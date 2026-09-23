package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.svc.bus.pms.entity.ReceivingOrderItem;
import com.adrug.erp.svc.bus.pms.mapper.ReceivingOrderItemMapper;
import com.adrug.erp.svc.bus.pms.repository.ReceivingOrderItemRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收货单明细数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class ReceivingOrderItemRepositoryImpl implements ReceivingOrderItemRepository {

    private final ReceivingOrderItemMapper receivingOrderItemMapper;

    public ReceivingOrderItemRepositoryImpl(ReceivingOrderItemMapper receivingOrderItemMapper) {
        this.receivingOrderItemMapper = receivingOrderItemMapper;
    }

    @Override
    public List<ReceivingOrderItem> selectByOrderId(Long orderId) {
        return receivingOrderItemMapper.selectList(new LambdaQueryWrapper<ReceivingOrderItem>()
                .eq(ReceivingOrderItem::getOrderId, orderId)
                .orderByAsc(ReceivingOrderItem::getId));
    }

    @Override
    public void insertBatch(List<ReceivingOrderItem> items) {
        if (items == null) {
            return;
        }
        items.forEach(receivingOrderItemMapper::insert);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        receivingOrderItemMapper.delete(new LambdaQueryWrapper<ReceivingOrderItem>()
                .eq(ReceivingOrderItem::getOrderId, orderId));
    }

    @Override
    public int deletePhysically(Long id) {
        return receivingOrderItemMapper.deletePhysically(id);
    }
}
