package com.adrug.erp.svc.bus.oms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.oms.entity.PosRetailOrder;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.repository.impl.PosRetailOrderRepositoryImpl;

/**
 * POS零售单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link PosRetailOrderRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface PosRetailOrderRepository {

    /**
     * 分页查询 POS 零售单。
     */
    PageResult<PosRetailOrder> selectPage(PosRetailOrderQuery query);

    /**
     * 按 ID 查询 POS 零售单。
     */
    PosRetailOrder selectById(Long id);

    /**
     * 新增 POS 零售单（雪花主键回填到 {@code order.id}）。
     */
    void insert(PosRetailOrder order);

    /**
     * 变更 POS 零售单。
     */
    void updateById(PosRetailOrder order);

    /**
     * 删除 POS 零售单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除 POS 零售单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
