package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.svc.bus.pms.entity.RejectionOrderItem;
import com.adrug.erp.svc.bus.pms.repository.impl.RejectionOrderItemRepositoryImpl;

import java.util.List;

/**
 * 拒收单明细数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link RejectionOrderItemRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface RejectionOrderItemRepository {

    /**
     * 按拒收单 ID 查询明细。
     */
    List<RejectionOrderItem> selectByOrderId(Long orderId);

    /**
     * 批量新增明细。
     */
    void insertBatch(List<RejectionOrderItem> items);

    /**
     * 按拒收单 ID 删除明细（逻辑删除）。
     */
    void deleteByOrderId(Long orderId);

    /**
     * 物理删除明细（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
