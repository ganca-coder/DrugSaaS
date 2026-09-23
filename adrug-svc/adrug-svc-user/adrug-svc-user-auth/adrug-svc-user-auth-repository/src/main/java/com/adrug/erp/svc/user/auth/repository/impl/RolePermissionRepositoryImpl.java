package com.adrug.erp.svc.user.auth.repository.impl;

import com.adrug.erp.svc.user.auth.entity.RolePermission;
import com.adrug.erp.svc.user.auth.mapper.RolePermissionMapper;
import com.adrug.erp.svc.user.auth.repository.RolePermissionRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 角色-权限关联数据访问实现。
 */
@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> selectByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        return rolePermissionMapper.selectList(wrapper);
    }

    @Override
    public List<RolePermission> selectByRoleIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePermission::getRoleId, roleIds);
        return rolePermissionMapper.selectList(wrapper);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(wrapper);
    }

    @Override
    public void insertBatch(List<RolePermission> list) {
        for (RolePermission rp : list) {
            rolePermissionMapper.insert(rp);
        }
    }
}
