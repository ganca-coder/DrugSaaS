package com.adrug.erp.svc.bus.oms.repository.impl;

import com.adrug.erp.svc.bus.oms.entity.PosRetailOrderItem;
import com.adrug.erp.svc.bus.oms.mapper.PosRetailOrderItemMapper;
import com.adrug.erp.svc.bus.oms.repository.PosRetailOrderItemRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * POS零售单明细数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class PosRetailOrderItemRepositoryImpl implements PosRetailOrderItemRepository {

    private final PosRetailOrderItemMapper posRetailOrderItemMapper;

    public PosRetailOrderItemRepositoryImpl(PosRetailOrderItemMapper posRetailOrderItemMapper) {
        this.posRetailOrderItemMapper = posRetailOrderItemMapper;
    }

    @Override
    public List<PosRetailOrderItem> selectByOrderId(Long orderId) {
        return posRetailOrderItemMapper.selectList(new LambdaQueryWrapper<PosRetailOrderItem>()
                .eq(PosRetailOrderItem::getOrderId, orderId)
                .orderByAsc(PosRetailOrderItem::getId));
    }

    @Override
    public void insertBatch(List<PosRetailOrderItem> items) {
        if (items == null) {
            return;
        }
        items.forEach(posRetailOrderItemMapper::insert);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        posRetailOrderItemMapper.delete(new LambdaQueryWrapper<PosRetailOrderItem>()
                .eq(PosRetailOrderItem::getOrderId, orderId));
    }

    @Override
    public int deletePhysically(Long id) {
        return posRetailOrderItemMapper.deletePhysically(id);
    }
}
