package com.adrug.erp.svc.user.auth.controller;

import com.adrug.erp.common.core.anno.RequirePermission;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.provider.PermissionProvider;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.vo.AccountAuthVO;
import com.adrug.erp.svc.user.auth.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限 Controller。
 * <p>
 * URL 路径与 {@code PermissionFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/auth/permission")
public class PermissionController {

    @Autowired
    private PermissionProvider permissionProvider;

    // ===== 角色 =====

    @PostMapping("/role/page")
    public Result<PageResult<RoleVO>> page(@RequestBody RoleQuery query) {
        return Result.success(permissionProvider.page(query));
    }

    @GetMapping("/role/{id}")
    public Result<RoleVO> getById(@PathVariable("id") Long id) {
        return Result.success(permissionProvider.getById(id));
    }

    @PostMapping("/role")
    public Result<Long> create(@RequestBody RoleSaveDTO dto) {
        return Result.success(permissionProvider.create(dto));
    }

    @PutMapping("/role/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody RoleSaveDTO dto) {
        permissionProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/role/{id}")
    @RequirePermission("system:role:delete")
    public Result<Void> delete(@PathVariable("id") Long id) {
        permissionProvider.delete(id);
        return Result.success();
    }

    // ===== 角色-权限 =====

    @PutMapping("/role/{roleId}/permissions")
    public Result<Void> saveRolePermissions(@PathVariable("roleId") Long roleId, @RequestBody List<String> permissions) {
        permissionProvider.saveRolePermissions(roleId, permissions);
        return Result.success();
    }

    @GetMapping("/role/{roleId}/permissions")
    public Result<List<String>> getRolePermissions(@PathVariable("roleId") Long roleId) {
        return Result.success(permissionProvider.getRolePermissions(roleId));
    }

    // ===== 账号-角色 =====

    @PutMapping("/account/{accountId}/roles")
    public Result<Void> saveAccountRoles(@PathVariable("accountId") Long accountId, @RequestBody List<Long> roleIds) {
        permissionProvider.saveAccountRoles(accountId, roleIds);
        return Result.success();
    }

    @GetMapping("/account/{accountId}/roles")
    public Result<List<Long>> getAccountRoleIds(@PathVariable("accountId") Long accountId) {
        return Result.success(permissionProvider.getAccountRoleIds(accountId));
    }

    @GetMapping("/account/{accountId}/auth")
    public Result<AccountAuthVO> getAccountAuth(@PathVariable("accountId") Long accountId) {
        return Result.success(permissionProvider.getAccountAuth(accountId));
    }

    @PutMapping("/role/{roleId}/dataOrgs")
    public Result<Void> saveRoleDataOrgs(@PathVariable("roleId") Long roleId, @RequestBody List<Long> orgIds) {
        permissionProvider.saveRoleDataOrgs(roleId, orgIds);
        return Result.success();
    }

    @GetMapping("/role/{roleId}/dataOrgs")
    public Result<List<Long>> getRoleDataOrgs(@PathVariable("roleId") Long roleId) {
        return Result.success(permissionProvider.getRoleDataOrgs(roleId));
    }
}
