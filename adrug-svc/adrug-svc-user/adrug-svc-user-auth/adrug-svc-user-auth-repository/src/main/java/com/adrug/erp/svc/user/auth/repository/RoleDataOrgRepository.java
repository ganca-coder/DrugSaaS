package com.adrug.erp.svc.user.auth.repository;

import com.adrug.erp.svc.user.auth.entity.RoleDataOrg;

import java.util.Collection;
import java.util.List;

/**
 * 角色-数据机构关联数据访问接口。
 */
public interface RoleDataOrgRepository {

    List<RoleDataOrg> selectByRoleId(Long roleId);

    List<RoleDataOrg> selectByRoleIds(Collection<Long> roleIds);

    void deleteByRoleId(Long roleId);

    void insertBatch(List<RoleDataOrg> list);
}
