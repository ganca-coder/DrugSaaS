package com.adrug.erp.svc.user.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.entity.Tenant;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.repository.impl.TenantRepositoryImpl;

/**
 * 租户数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link TenantRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface TenantRepository {

    /**
     * 分页查询租户。
     */
    PageResult<Tenant> selectPage(TenantQuery query);

    /**
     * 按 ID 查询租户。
     */
    Tenant selectById(Long id);

    /**
     * 新增租户（雪花主键回填到 {@code tenant.id}）。
     */
    void insert(Tenant tenant);

    /**
     * 变更租户。
     */
    void updateById(Tenant tenant);

    /**
     * 删除租户（逻辑删除）。
     */
    void deleteById(Long id);
}
