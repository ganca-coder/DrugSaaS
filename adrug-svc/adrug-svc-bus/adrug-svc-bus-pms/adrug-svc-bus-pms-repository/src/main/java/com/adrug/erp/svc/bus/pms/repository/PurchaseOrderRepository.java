package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrder;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.impl.PurchaseOrderRepositoryImpl;

/**
 * 采购订单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link PurchaseOrderRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface PurchaseOrderRepository {

    /**
     * 分页查询采购订单。
     */
    PageResult<PurchaseOrder> selectPage(PurchaseOrderQuery query);

    /**
     * 按 ID 查询采购订单。
     */
    PurchaseOrder selectById(Long id);

    /**
     * 新增采购订单（雪花主键回填到 {@code purchaseOrder.id}）。
     */
    void insert(PurchaseOrder purchaseOrder);

    /**
     * 变更采购订单。
     */
    void updateById(PurchaseOrder purchaseOrder);

    /**
     * 删除采购订单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除采购订单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
