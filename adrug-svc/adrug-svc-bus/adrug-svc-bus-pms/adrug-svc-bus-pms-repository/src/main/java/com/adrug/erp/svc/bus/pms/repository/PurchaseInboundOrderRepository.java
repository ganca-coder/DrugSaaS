package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrder;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.impl.PurchaseInboundOrderRepositoryImpl;

/**
 * 采购入库单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link PurchaseInboundOrderRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface PurchaseInboundOrderRepository {

    /**
     * 分页查询采购入库单。
     */
    PageResult<PurchaseInboundOrder> selectPage(PurchaseInboundOrderQuery query);

    /**
     * 按 ID 查询采购入库单。
     */
    PurchaseInboundOrder selectById(Long id);

    /**
     * 新增采购入库单（雪花主键回填到 {@code purchaseInboundOrder.id}）。
     */
    void insert(PurchaseInboundOrder purchaseInboundOrder);

    /**
     * 变更采购入库单。
     */
    void updateById(PurchaseInboundOrder purchaseInboundOrder);

    /**
     * 删除采购入库单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除采购入库单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
