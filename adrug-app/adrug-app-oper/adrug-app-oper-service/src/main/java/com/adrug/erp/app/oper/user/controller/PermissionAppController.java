package com.adrug.erp.app.oper.user.controller;

import com.adrug.erp.app.oper.user.service.PermissionQueryService;
import com.adrug.erp.app.oper.user.service.PermissionService;
import com.adrug.erp.common.core.anno.RequirePermission;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限（角色） Controller（应用层，读写分离）。
 * <p>
 * RBAC 鉴权点挂在本层：Controller → Service → Feign（adrug-svc-user-auth）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/user/permission")
public class PermissionAppController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionQueryService permissionQueryService;

    @PostMapping("/getRolePage")
    public Result<PageResult<RoleVO>> getRolePage(@RequestBody RoleQuery query) {
        return Result.success(permissionQueryService.getRolePage(query));
    }

    @GetMapping("/role/{id}")
    public Result<RoleVO> getRoleById(@PathVariable("id") Long id) {
        return Result.success(permissionQueryService.getRoleById(id));
    }

    @PostMapping("/role")
    @RequirePermission("system:role:create")
    public Result<Long> createRole(@RequestBody RoleSaveDTO dto) {
        return Result.success(permissionService.createRole(dto));
    }

    @PutMapping("/role/{id}")
    @RequirePermission("system:role:update")
    public Result<Void> updateRole(@PathVariable("id") Long id, @RequestBody RoleSaveDTO dto) {
        permissionService.updateRole(id, dto);
        return Result.success();
    }

    @DeleteMapping("/role/{id}")
    @RequirePermission("system:role:delete")
    public Result<Void> deleteRole(@PathVariable("id") Long id) {
        permissionService.deleteRole(id);
        return Result.success();
    }

    @PutMapping("/role/{roleId}/permissions")
    @RequirePermission("system:role:permission")
    public Result<Void> saveRolePermissions(@PathVariable("roleId") Long roleId, @RequestBody List<String> permissions) {
        permissionService.saveRolePermissions(roleId, permissions);
        return Result.success();
    }

    @GetMapping("/role/{roleId}/permissions")
    public Result<List<String>> getRolePermissions(@PathVariable("roleId") Long roleId) {
        return Result.success(permissionQueryService.getRolePermissions(roleId));
    }

    @PutMapping("/account/{accountId}/roles")
    @RequirePermission("system:role:assign")
    public Result<Void> saveAccountRoles(@PathVariable("accountId") Long accountId, @RequestBody List<Long> roleIds) {
        permissionService.saveAccountRoles(accountId, roleIds);
        return Result.success();
    }

    @GetMapping("/account/{accountId}/roles")
    public Result<List<Long>> getAccountRoleIds(@PathVariable("accountId") Long accountId) {
        return Result.success(permissionQueryService.getAccountRoleIds(accountId));
    }
}
