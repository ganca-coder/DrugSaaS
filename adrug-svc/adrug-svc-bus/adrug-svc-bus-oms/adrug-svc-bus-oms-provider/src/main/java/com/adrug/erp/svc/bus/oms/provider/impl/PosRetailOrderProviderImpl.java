package com.adrug.erp.svc.bus.oms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.oms.convert.PosRetailOrderConvert;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderItemDTO;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.entity.PosRetailOrder;
import com.adrug.erp.svc.bus.oms.entity.PosRetailOrderItem;
import com.adrug.erp.svc.bus.oms.provider.PosRetailOrderProvider;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.repository.PosRetailOrderItemRepository;
import com.adrug.erp.svc.bus.oms.repository.PosRetailOrderRepository;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * POS零售单服务实现。
 */
@Service
public class PosRetailOrderProviderImpl implements PosRetailOrderProvider {

    @Autowired
    private PosRetailOrderRepository posRetailOrderRepository;

    @Autowired
    private PosRetailOrderItemRepository posRetailOrderItemRepository;

    @Override
    public PageResult<PosRetailOrderVO> page(PosRetailOrderQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<PosRetailOrder> pageResult = posRetailOrderRepository.selectPage(query);
        List<PosRetailOrderVO> records = PosRetailOrderConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public PosRetailOrderVO getById(Long id) {
        PosRetailOrder order = posRetailOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        PosRetailOrderVO vo = PosRetailOrderConvert.toVO(order);
        vo.setItems(PosRetailOrderConvert.toItemVOList(posRetailOrderItemRepository.selectByOrderId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PosRetailOrderSaveDTO dto) {
        PosRetailOrder order = PosRetailOrderConvert.toEntity(dto);
        order.setId(null);
        List<PosRetailOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumQty(items));
        order.setDiscountedAmount(sumReceivableAmount(items));
        posRetailOrderRepository.insert(order);
        // 保存明细
        for (PosRetailOrderItem item : items) {
            item.setOrderId(order.getId());
        }
        posRetailOrderItemRepository.insertBatch(items);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, PosRetailOrderSaveDTO dto) {
        PosRetailOrder existing = posRetailOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        PosRetailOrder order = PosRetailOrderConvert.toEntity(dto);
        order.setId(id);
        List<PosRetailOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumQty(items));
        order.setDiscountedAmount(sumReceivableAmount(items));
        posRetailOrderRepository.updateById(order);
        // 重写明细：先删后插
        posRetailOrderItemRepository.deleteByOrderId(id);
        for (PosRetailOrderItem item : items) {
            item.setOrderId(id);
        }
        posRetailOrderItemRepository.insertBatch(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PosRetailOrder existing = posRetailOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        posRetailOrderRepository.deleteById(id);
        posRetailOrderItemRepository.deleteByOrderId(id);
    }

    /**
     * 明细入参列表转实体列表，序号缺省按明细顺序从 1 开始编号，
     * 金额缺省按「数量 × 单价」计算，应收金额缺省按「金额 - 优惠金额」计算。
     */
    private List<PosRetailOrderItem> buildItems(List<PosRetailOrderItemDTO> itemDTOs) {
        List<PosRetailOrderItem> items = new ArrayList<>();
        if (itemDTOs == null) {
            return items;
        }
        for (int i = 0; i < itemDTOs.size(); i++) {
            PosRetailOrderItemDTO itemDTO = itemDTOs.get(i);
            PosRetailOrderItem item = PosRetailOrderConvert.toItemEntity(itemDTO);
            if (item.getSeqNo() == null) {
                item.setSeqNo(i + 1);
            }
            if (item.getAmount() == null && item.getQty() != null && item.getPrice() != null) {
                item.setAmount(item.getQty().multiply(item.getPrice()));
            }
            if (item.getReceivableAmount() == null && item.getAmount() != null) {
                BigDecimal discount = item.getDiscountAmount() == null ? BigDecimal.ZERO : item.getDiscountAmount();
                item.setReceivableAmount(item.getAmount().subtract(discount));
            }
            items.add(item);
        }
        return items;
    }

    /**
     * 合计数量。
     */
    private BigDecimal sumQty(List<PosRetailOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (PosRetailOrderItem item : items) {
            if (item.getQty() != null) {
                total = total.add(item.getQty());
            }
        }
        return total;
    }

    /**
     * 折后金额合计（应收金额累加）。
     */
    private BigDecimal sumReceivableAmount(List<PosRetailOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (PosRetailOrderItem item : items) {
            if (item.getReceivableAmount() != null) {
                total = total.add(item.getReceivableAmount());
            }
        }
        return total;
    }
}
