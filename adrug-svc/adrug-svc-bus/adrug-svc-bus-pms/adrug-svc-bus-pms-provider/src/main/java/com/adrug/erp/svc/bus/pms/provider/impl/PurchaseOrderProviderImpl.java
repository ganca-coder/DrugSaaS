package com.adrug.erp.svc.bus.pms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.pms.convert.PurchaseOrderConvert;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrder;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrderItem;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.provider.PurchaseOrderProvider;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.PurchaseOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.PurchaseOrderRepository;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购订单服务实现。
 */
@Service
public class PurchaseOrderProviderImpl implements PurchaseOrderProvider {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Override
    public PageResult<PurchaseOrderVO> page(PurchaseOrderQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<PurchaseOrder> pageResult = purchaseOrderRepository.selectPage(query);
        List<PurchaseOrderVO> records = PurchaseOrderConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public PurchaseOrderVO getById(Long id) {
        PurchaseOrder order = purchaseOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        PurchaseOrderVO vo = PurchaseOrderConvert.toVO(order);
        vo.setItems(PurchaseOrderConvert.toItemVOList(purchaseOrderItemRepository.selectByOrderId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PurchaseOrderSaveDTO dto) {
        PurchaseOrder order = PurchaseOrderConvert.toEntity(dto);
        order.setId(null);
        order.setStatus(BillStatusEnum.DRAFT);
        // 计算数量/金额合计
        List<PurchaseOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumQty(items));
        order.setTotalAmount(sumAmount(items));
        purchaseOrderRepository.insert(order);
        // 保存明细
        for (PurchaseOrderItem item : items) {
            item.setOrderId(order.getId());
        }
        purchaseOrderItemRepository.insertBatch(items);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, PurchaseOrderSaveDTO dto) {
        PurchaseOrder existing = purchaseOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        PurchaseOrder order = PurchaseOrderConvert.toEntity(dto);
        order.setId(id);
        // 保留原单据状态（状态由过账动作变更）
        order.setStatus(existing.getStatus());
        List<PurchaseOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumQty(items));
        order.setTotalAmount(sumAmount(items));
        purchaseOrderRepository.updateById(order);
        // 重写明细：先删后插
        purchaseOrderItemRepository.deleteByOrderId(id);
        for (PurchaseOrderItem item : items) {
            item.setOrderId(id);
        }
        purchaseOrderItemRepository.insertBatch(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PurchaseOrder existing = purchaseOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        purchaseOrderRepository.deleteById(id);
        purchaseOrderItemRepository.deleteByOrderId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Long id) {
        PurchaseOrder order = purchaseOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        if (order.getStatus() == BillStatusEnum.COMPLETED) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "采购订单已制单完成，不能重复过账");
        }
        // 只更新状态和过账时间，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
        PurchaseOrder update = new PurchaseOrder();
        update.setId(order.getId());
        update.setVersion(order.getVersion());
        update.setStatus(BillStatusEnum.COMPLETED);
        update.setPostTime(LocalDateTime.now());
        purchaseOrderRepository.updateById(update);
    }

    /**
     * 明细入参列表转实体列表，序号缺省按明细顺序从 1 开始编号，金额缺省按「数量 × 单价」计算。
     */
    private List<PurchaseOrderItem> buildItems(List<PurchaseOrderItemDTO> itemDTOs) {
        List<PurchaseOrderItem> items = new ArrayList<>();
        if (itemDTOs == null) {
            return items;
        }
        for (int i = 0; i < itemDTOs.size(); i++) {
            PurchaseOrderItemDTO itemDTO = itemDTOs.get(i);
            PurchaseOrderItem item = PurchaseOrderConvert.toItemEntity(itemDTO);
            if (item.getSeqNo() == null) {
                item.setSeqNo(i + 1);
            }
            if (item.getAmount() == null && item.getQty() != null && item.getPrice() != null) {
                item.setAmount(item.getQty().multiply(item.getPrice()));
            }
            items.add(item);
        }
        return items;
    }

    /**
     * 采购数量合计。
     */
    private BigDecimal sumQty(List<PurchaseOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseOrderItem item : items) {
            if (item.getQty() != null) {
                total = total.add(item.getQty());
            }
        }
        return total;
    }

    /**
     * 采购金额合计。
     */
    private BigDecimal sumAmount(List<PurchaseOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseOrderItem item : items) {
            if (item.getAmount() != null) {
                total = total.add(item.getAmount());
            }
        }
        return total;
    }
}
