package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.svc.bus.pms.entity.ReceivingOrderItem;
import com.adrug.erp.svc.bus.pms.repository.impl.ReceivingOrderItemRepositoryImpl;

import java.util.List;

/**
 * 收货单明细数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link ReceivingOrderItemRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface ReceivingOrderItemRepository {

    /**
     * 按收货单 ID 查询明细。
     */
    List<ReceivingOrderItem> selectByOrderId(Long orderId);

    /**
     * 批量新增明细。
     */
    void insertBatch(List<ReceivingOrderItem> items);

    /**
     * 按收货单 ID 删除明细（逻辑删除）。
     */
    void deleteByOrderId(Long orderId);

    /**
     * 物理删除明细（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
