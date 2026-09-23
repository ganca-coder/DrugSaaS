package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.svc.bus.pms.entity.RejectionOrderItem;
import com.adrug.erp.svc.bus.pms.mapper.RejectionOrderItemMapper;
import com.adrug.erp.svc.bus.pms.repository.RejectionOrderItemRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 拒收单明细数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class RejectionOrderItemRepositoryImpl implements RejectionOrderItemRepository {

    private final RejectionOrderItemMapper rejectionOrderItemMapper;

    public RejectionOrderItemRepositoryImpl(RejectionOrderItemMapper rejectionOrderItemMapper) {
        this.rejectionOrderItemMapper = rejectionOrderItemMapper;
    }

    @Override
    public List<RejectionOrderItem> selectByOrderId(Long orderId) {
        return rejectionOrderItemMapper.selectList(new LambdaQueryWrapper<RejectionOrderItem>()
                .eq(RejectionOrderItem::getOrderId, orderId)
                .orderByAsc(RejectionOrderItem::getId));
    }

    @Override
    public void insertBatch(List<RejectionOrderItem> items) {
        if (items == null) {
            return;
        }
        items.forEach(rejectionOrderItemMapper::insert);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        rejectionOrderItemMapper.delete(new LambdaQueryWrapper<RejectionOrderItem>()
                .eq(RejectionOrderItem::getOrderId, orderId));
    }

    @Override
    public int deletePhysically(Long id) {
        return rejectionOrderItemMapper.deletePhysically(id);
    }
}
