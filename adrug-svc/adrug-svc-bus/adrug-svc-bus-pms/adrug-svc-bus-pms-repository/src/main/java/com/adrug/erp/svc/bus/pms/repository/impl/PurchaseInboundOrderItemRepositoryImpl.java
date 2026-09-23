package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrderItem;
import com.adrug.erp.svc.bus.pms.mapper.PurchaseInboundOrderItemMapper;
import com.adrug.erp.svc.bus.pms.repository.PurchaseInboundOrderItemRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购入库单明细数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class PurchaseInboundOrderItemRepositoryImpl implements PurchaseInboundOrderItemRepository {

    private final PurchaseInboundOrderItemMapper purchaseInboundOrderItemMapper;

    public PurchaseInboundOrderItemRepositoryImpl(PurchaseInboundOrderItemMapper purchaseInboundOrderItemMapper) {
        this.purchaseInboundOrderItemMapper = purchaseInboundOrderItemMapper;
    }

    @Override
    public List<PurchaseInboundOrderItem> selectByOrderId(Long orderId) {
        return purchaseInboundOrderItemMapper.selectList(new LambdaQueryWrapper<PurchaseInboundOrderItem>()
                .eq(PurchaseInboundOrderItem::getOrderId, orderId)
                .orderByAsc(PurchaseInboundOrderItem::getId));
    }

    @Override
    public void insertBatch(List<PurchaseInboundOrderItem> items) {
        if (items == null) {
            return;
        }
        items.forEach(purchaseInboundOrderItemMapper::insert);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        purchaseInboundOrderItemMapper.delete(new LambdaQueryWrapper<PurchaseInboundOrderItem>()
                .eq(PurchaseInboundOrderItem::getOrderId, orderId));
    }

    @Override
    public int deletePhysically(Long id) {
        return purchaseInboundOrderItemMapper.deletePhysically(id);
    }
}
