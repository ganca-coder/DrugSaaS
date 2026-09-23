package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.PermissionService;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.feign.PermissionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限（角色）服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionFeign permissionFeign;

    @Override
    public Long createRole(RoleSaveDTO dto) {
        return permissionFeign.create(dto).getData();
    }

    @Override
    public void updateRole(Long id, RoleSaveDTO dto) {
        permissionFeign.update(id, dto);
    }

    @Override
    public void deleteRole(Long id) {
        permissionFeign.delete(id);
    }

    @Override
    public void saveRolePermissions(Long roleId, List<String> permissions) {
        permissionFeign.saveRolePermissions(roleId, permissions);
    }

    @Override
    public void saveAccountRoles(Long accountId, List<Long> roleIds) {
        permissionFeign.saveAccountRoles(accountId, roleIds);
    }
}
