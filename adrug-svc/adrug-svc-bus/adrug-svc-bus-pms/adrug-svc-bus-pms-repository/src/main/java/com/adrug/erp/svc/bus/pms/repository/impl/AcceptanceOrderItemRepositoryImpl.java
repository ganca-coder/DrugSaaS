package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrderItem;
import com.adrug.erp.svc.bus.pms.mapper.AcceptanceOrderItemMapper;
import com.adrug.erp.svc.bus.pms.repository.AcceptanceOrderItemRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 验收单明细数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class AcceptanceOrderItemRepositoryImpl implements AcceptanceOrderItemRepository {

    private final AcceptanceOrderItemMapper acceptanceOrderItemMapper;

    public AcceptanceOrderItemRepositoryImpl(AcceptanceOrderItemMapper acceptanceOrderItemMapper) {
        this.acceptanceOrderItemMapper = acceptanceOrderItemMapper;
    }

    @Override
    public List<AcceptanceOrderItem> selectByOrderId(Long orderId) {
        return acceptanceOrderItemMapper.selectList(new LambdaQueryWrapper<AcceptanceOrderItem>()
                .eq(AcceptanceOrderItem::getOrderId, orderId)
                .orderByAsc(AcceptanceOrderItem::getId));
    }

    @Override
    public void insertBatch(List<AcceptanceOrderItem> items) {
        if (items == null) {
            return;
        }
        items.forEach(acceptanceOrderItemMapper::insert);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        acceptanceOrderItemMapper.delete(new LambdaQueryWrapper<AcceptanceOrderItem>()
                .eq(AcceptanceOrderItem::getOrderId, orderId));
    }

    @Override
    public int deletePhysically(Long id) {
        return acceptanceOrderItemMapper.deletePhysically(id);
    }
}
