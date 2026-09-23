package com.adrug.erp.svc.user.auth.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.auth.entity.Role;
import com.adrug.erp.svc.user.auth.enums.RoleStatusEnum;
import com.adrug.erp.svc.user.auth.mapper.RoleMapper;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.repository.RoleRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 角色数据访问实现。
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult<Role> selectPage(RoleQuery query) {
        Page<Role> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Role::getRoleName, query.getKeyword())
                        .or()
                        .like(Role::getRoleCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Role::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Role::getTenantId, query.getTenantId());
        wrapper.eq(StringUtils.hasText(query.getRoleCode()), Role::getRoleCode, query.getRoleCode());
        wrapper.eq(StringUtils.hasText(query.getRoleName()), Role::getRoleName, query.getRoleName());
        RoleStatusEnum status = BaseEnum.ofCode(RoleStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Role::getStatus, status);

        wrapper.orderByDesc(Role::getCreateTime);

        Page<Role> result = roleMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Role selectById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> selectByIds(Collection<Long> ids) {
        return roleMapper.selectBatchIds(ids);
    }

    @Override
    public void insert(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateById(Role role) {
        roleMapper.updateById(role);
    }

    @Override
    public void deleteById(Long id) {
        roleMapper.deleteById(id);
    }
}
