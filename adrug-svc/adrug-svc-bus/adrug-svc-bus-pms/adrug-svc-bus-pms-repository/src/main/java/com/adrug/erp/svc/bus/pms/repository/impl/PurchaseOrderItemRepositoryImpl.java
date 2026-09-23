package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.svc.bus.pms.entity.PurchaseOrderItem;
import com.adrug.erp.svc.bus.pms.mapper.PurchaseOrderItemMapper;
import com.adrug.erp.svc.bus.pms.repository.PurchaseOrderItemRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购订单明细数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class PurchaseOrderItemRepositoryImpl implements PurchaseOrderItemRepository {

    private final PurchaseOrderItemMapper purchaseOrderItemMapper;

    public PurchaseOrderItemRepositoryImpl(PurchaseOrderItemMapper purchaseOrderItemMapper) {
        this.purchaseOrderItemMapper = purchaseOrderItemMapper;
    }

    @Override
    public List<PurchaseOrderItem> selectByOrderId(Long orderId) {
        return purchaseOrderItemMapper.selectList(new LambdaQueryWrapper<PurchaseOrderItem>()
                .eq(PurchaseOrderItem::getOrderId, orderId)
                .orderByAsc(PurchaseOrderItem::getId));
    }

    @Override
    public void insertBatch(List<PurchaseOrderItem> items) {
        if (items == null) {
            return;
        }
        items.forEach(purchaseOrderItemMapper::insert);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        purchaseOrderItemMapper.delete(new LambdaQueryWrapper<PurchaseOrderItem>()
                .eq(PurchaseOrderItem::getOrderId, orderId));
    }

    @Override
    public int deletePhysically(Long id) {
        return purchaseOrderItemMapper.deletePhysically(id);
    }
}
