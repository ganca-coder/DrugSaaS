package com.adrug.erp.svc.bus.wms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.wms.entity.Inventory;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.repository.impl.InventoryRepositoryImpl;

/**
 * 库存数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link InventoryRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface InventoryRepository {

    /**
     * 分页查询库存。
     */
    PageResult<Inventory> selectPage(InventoryQuery query);

    /**
     * 按 商品 + 仓库 + 批号 查询库存。
     */
    Inventory selectByKey(Long drugId, Long warehouseId, String batchNo);

    /**
     * 新增库存（雪花主键回填到 {@code inventory.id}）。
     */
    void insert(Inventory inventory);

    /**
     * 变更库存。
     */
    void updateById(Inventory inventory);

    /**
     * 物理删除库存（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
