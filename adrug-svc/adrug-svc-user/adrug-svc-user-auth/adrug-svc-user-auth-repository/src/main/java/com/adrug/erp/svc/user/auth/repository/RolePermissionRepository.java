package com.adrug.erp.svc.user.auth.repository;

import com.adrug.erp.svc.user.auth.entity.RolePermission;

import java.util.Collection;
import java.util.List;

/**
 * 角色-权限关联数据访问接口。
 */
public interface RolePermissionRepository {

    List<RolePermission> selectByRoleId(Long roleId);

    List<RolePermission> selectByRoleIds(Collection<Long> roleIds);

    void deleteByRoleId(Long roleId);

    void insertBatch(List<RolePermission> list);
}
