package com.adrug.erp.svc.bus.pms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.pms.convert.PurchaseInboundOrderConvert;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrder;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrderItem;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.provider.PurchaseInboundOrderProvider;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.PurchaseInboundOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.PurchaseInboundOrderRepository;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购入库单服务实现。
 */
@Service
public class PurchaseInboundOrderProviderImpl implements PurchaseInboundOrderProvider {

    @Autowired
    private PurchaseInboundOrderRepository purchaseInboundOrderRepository;

    @Autowired
    private PurchaseInboundOrderItemRepository purchaseInboundOrderItemRepository;

    @Override
    public PageResult<PurchaseInboundOrderVO> page(PurchaseInboundOrderQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<PurchaseInboundOrder> pageResult = purchaseInboundOrderRepository.selectPage(query);
        List<PurchaseInboundOrderVO> records = PurchaseInboundOrderConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public PurchaseInboundOrderVO getById(Long id) {
        PurchaseInboundOrder order = purchaseInboundOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        PurchaseInboundOrderVO vo = PurchaseInboundOrderConvert.toVO(order);
        vo.setItems(PurchaseInboundOrderConvert.toItemVOList(purchaseInboundOrderItemRepository.selectByOrderId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PurchaseInboundOrderSaveDTO dto) {
        PurchaseInboundOrder order = PurchaseInboundOrderConvert.toEntity(dto);
        order.setId(null);
        order.setStatus(BillStatusEnum.DRAFT);
        List<PurchaseInboundOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumInboundQty(items));
        order.setTotalAmount(sumAmount(items));
        purchaseInboundOrderRepository.insert(order);
        for (PurchaseInboundOrderItem item : items) {
            item.setOrderId(order.getId());
        }
        purchaseInboundOrderItemRepository.insertBatch(items);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, PurchaseInboundOrderSaveDTO dto) {
        PurchaseInboundOrder existing = purchaseInboundOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        PurchaseInboundOrder order = PurchaseInboundOrderConvert.toEntity(dto);
        order.setId(id);
        // 保留原单据状态（状态由过账动作变更）
        order.setStatus(existing.getStatus());
        List<PurchaseInboundOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumInboundQty(items));
        order.setTotalAmount(sumAmount(items));
        purchaseInboundOrderRepository.updateById(order);
        purchaseInboundOrderItemRepository.deleteByOrderId(id);
        for (PurchaseInboundOrderItem item : items) {
            item.setOrderId(id);
        }
        purchaseInboundOrderItemRepository.insertBatch(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PurchaseInboundOrder existing = purchaseInboundOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        purchaseInboundOrderRepository.deleteById(id);
        purchaseInboundOrderItemRepository.deleteByOrderId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Long id) {
        PurchaseInboundOrder order = purchaseInboundOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        if (order.getStatus() == BillStatusEnum.COMPLETED) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "采购入库单已制单完成，不能重复过账");
        }
        // 只更新状态和过账时间，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
        PurchaseInboundOrder update = new PurchaseInboundOrder();
        update.setId(order.getId());
        update.setVersion(order.getVersion());
        update.setStatus(BillStatusEnum.COMPLETED);
        update.setPostTime(LocalDateTime.now());
        purchaseInboundOrderRepository.updateById(update);
        // 过账后增加系统库存、产生应付账由应用层（adrug-app-oper）编排调用库存/财务服务
    }

    /**
     * 明细入参列表转实体列表，金额缺省按「数量 × 单价」计算。
     */
    private List<PurchaseInboundOrderItem> buildItems(List<PurchaseInboundOrderItemDTO> itemDTOs) {
        List<PurchaseInboundOrderItem> items = new ArrayList<>();
        if (itemDTOs == null) {
            return items;
        }
        for (PurchaseInboundOrderItemDTO itemDTO : itemDTOs) {
            PurchaseInboundOrderItem item = PurchaseInboundOrderConvert.toItemEntity(itemDTO);
            if (item.getAmount() == null && item.getInboundQty() != null && item.getPrice() != null) {
                item.setAmount(item.getInboundQty().multiply(item.getPrice()));
            }
            items.add(item);
        }
        return items;
    }

    /**
     * 入库数量合计。
     */
    private BigDecimal sumInboundQty(List<PurchaseInboundOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseInboundOrderItem item : items) {
            if (item.getInboundQty() != null) {
                total = total.add(item.getInboundQty());
            }
        }
        return total;
    }

    /**
     * 入库金额合计。
     */
    private BigDecimal sumAmount(List<PurchaseInboundOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseInboundOrderItem item : items) {
            if (item.getAmount() != null) {
                total = total.add(item.getAmount());
            }
        }
        return total;
    }
}
