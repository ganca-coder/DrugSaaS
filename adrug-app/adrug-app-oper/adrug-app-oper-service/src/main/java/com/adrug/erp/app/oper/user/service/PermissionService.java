package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;

import java.util.List;

/**
 * 权限（角色）服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface PermissionService {

    Long createRole(RoleSaveDTO dto);

    void updateRole(Long id, RoleSaveDTO dto);

    void deleteRole(Long id);

    void saveRolePermissions(Long roleId, List<String> permissions);

    void saveAccountRoles(Long accountId, List<Long> roleIds);
}
