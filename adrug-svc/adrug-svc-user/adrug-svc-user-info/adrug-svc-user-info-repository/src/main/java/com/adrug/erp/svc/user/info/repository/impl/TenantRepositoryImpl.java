package com.adrug.erp.svc.user.info.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.entity.Tenant;
import com.adrug.erp.svc.user.info.enums.TenantStatusEnum;
import com.adrug.erp.svc.user.info.mapper.TenantMapper;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.repository.TenantRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 租户数据访问实现。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class TenantRepositoryImpl implements TenantRepository {

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public PageResult<Tenant> selectPage(TenantQuery query) {
        Page<Tenant> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Tenant::getTenantName, query.getKeyword())
                        .or()
                        .like(Tenant::getTenantCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Tenant::getId, query.getId());
        wrapper.eq(StringUtils.hasText(query.getTenantCode()), Tenant::getTenantCode, query.getTenantCode());
        wrapper.eq(StringUtils.hasText(query.getTenantName()), Tenant::getTenantName, query.getTenantName());
        TenantStatusEnum status = BaseEnum.ofCode(TenantStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Tenant::getStatus, status);

        wrapper.orderByDesc(Tenant::getCreateTime);

        Page<Tenant> result = tenantMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Tenant selectById(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public void insert(Tenant tenant) {
        tenantMapper.insert(tenant);
    }

    @Override
    public void updateById(Tenant tenant) {
        tenantMapper.updateById(tenant);
    }

    @Override
    public void deleteById(Long id) {
        tenantMapper.deleteById(id);
    }
}
