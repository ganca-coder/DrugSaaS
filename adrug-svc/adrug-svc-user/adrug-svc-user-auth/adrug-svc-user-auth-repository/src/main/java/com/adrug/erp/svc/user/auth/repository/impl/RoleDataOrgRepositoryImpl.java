package com.adrug.erp.svc.user.auth.repository.impl;

import com.adrug.erp.svc.user.auth.entity.RoleDataOrg;
import com.adrug.erp.svc.user.auth.mapper.RoleDataOrgMapper;
import com.adrug.erp.svc.user.auth.repository.RoleDataOrgRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 角色-数据机构关联数据访问实现。
 */
@Repository
public class RoleDataOrgRepositoryImpl implements RoleDataOrgRepository {

    @Autowired
    private RoleDataOrgMapper roleDataOrgMapper;

    @Override
    public List<RoleDataOrg> selectByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleDataOrg> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDataOrg::getRoleId, roleId);
        return roleDataOrgMapper.selectList(wrapper);
    }

    @Override
    public List<RoleDataOrg> selectByRoleIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<RoleDataOrg> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RoleDataOrg::getRoleId, roleIds);
        return roleDataOrgMapper.selectList(wrapper);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleDataOrg> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDataOrg::getRoleId, roleId);
        roleDataOrgMapper.delete(wrapper);
    }

    @Override
    public void insertBatch(List<RoleDataOrg> list) {
        for (RoleDataOrg item : list) {
            roleDataOrgMapper.insert(item);
        }
    }
}
