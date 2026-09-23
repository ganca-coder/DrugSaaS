package com.adrug.erp.svc.bus.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.entity.Supplier;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.repository.impl.SupplierRepositoryImpl;

/**
 * 供应商信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link SupplierRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface SupplierRepository {

    /**
     * 分页查询供应商。
     */
    PageResult<Supplier> selectPage(SupplierQuery query);

    /**
     * 按 ID 查询供应商。
     */
    Supplier selectById(Long id);

    /**
     * 新增供应商（雪花主键回填到 {@code supplier.id}）。
     */
    void insert(Supplier supplier);

    /**
     * 变更供应商。
     */
    void updateById(Supplier supplier);

    /**
     * 删除供应商（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除供应商（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
