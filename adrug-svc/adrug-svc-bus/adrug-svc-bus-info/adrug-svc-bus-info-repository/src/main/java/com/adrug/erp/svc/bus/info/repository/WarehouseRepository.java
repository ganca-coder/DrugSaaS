package com.adrug.erp.svc.bus.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.entity.Warehouse;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.repository.impl.WarehouseRepositoryImpl;

/**
 * 仓库信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link WarehouseRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface WarehouseRepository {

    /**
     * 分页查询仓库。
     */
    PageResult<Warehouse> selectPage(WarehouseQuery query);

    /**
     * 按 ID 查询仓库。
     */
    Warehouse selectById(Long id);

    /**
     * 新增仓库（雪花主键回填到 {@code warehouse.id}）。
     */
    void insert(Warehouse warehouse);

    /**
     * 变更仓库。
     */
    void updateById(Warehouse warehouse);

    /**
     * 删除仓库（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除仓库（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
