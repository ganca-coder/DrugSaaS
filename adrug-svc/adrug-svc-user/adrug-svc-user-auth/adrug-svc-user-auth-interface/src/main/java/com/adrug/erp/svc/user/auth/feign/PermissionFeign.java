package com.adrug.erp.svc.user.auth.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.provider.PermissionProvider;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.vo.AccountAuthVO;
import com.adrug.erp.svc.user.auth.vo.RoleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 权限 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-user-auth", path = "/svc/user/auth/permission", contextId = "permissionFeign")
public interface PermissionFeign {

    @ProviderService(provider = PermissionProvider.class, method = "page")
    @PostMapping("/role/page")
    Result<PageResult<RoleVO>> page(@RequestBody RoleQuery query);

    @ProviderService(provider = PermissionProvider.class, method = "getById")
    @GetMapping("/role/{id}")
    Result<RoleVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = PermissionProvider.class, method = "create")
    @PostMapping("/role")
    Result<Long> create(@RequestBody RoleSaveDTO dto);

    @ProviderService(provider = PermissionProvider.class, method = "update")
    @PutMapping("/role/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody RoleSaveDTO dto);

    @ProviderService(provider = PermissionProvider.class, method = "delete")
    @DeleteMapping("/role/{id}")
    Result<Void> delete(@PathVariable("id") Long id);

    @ProviderService(provider = PermissionProvider.class, method = "saveRolePermissions")
    @PutMapping("/role/{roleId}/permissions")
    Result<Void> saveRolePermissions(@PathVariable("roleId") Long roleId, @RequestBody List<String> permissions);

    @ProviderService(provider = PermissionProvider.class, method = "getRolePermissions")
    @GetMapping("/role/{roleId}/permissions")
    Result<List<String>> getRolePermissions(@PathVariable("roleId") Long roleId);

    @ProviderService(provider = PermissionProvider.class, method = "saveAccountRoles")
    @PutMapping("/account/{accountId}/roles")
    Result<Void> saveAccountRoles(@PathVariable("accountId") Long accountId, @RequestBody List<Long> roleIds);

    @ProviderService(provider = PermissionProvider.class, method = "getAccountRoleIds")
    @GetMapping("/account/{accountId}/roles")
    Result<List<Long>> getAccountRoleIds(@PathVariable("accountId") Long accountId);

    @ProviderService(provider = PermissionProvider.class, method = "getAccountAuth")
    @GetMapping("/account/{accountId}/auth")
    Result<AccountAuthVO> getAccountAuth(@PathVariable("accountId") Long accountId);

    @ProviderService(provider = PermissionProvider.class, method = "saveRoleDataOrgs")
    @PutMapping("/role/{roleId}/dataOrgs")
    Result<Void> saveRoleDataOrgs(@PathVariable("roleId") Long roleId, @RequestBody List<Long> orgIds);

    @ProviderService(provider = PermissionProvider.class, method = "getRoleDataOrgs")
    @GetMapping("/role/{roleId}/dataOrgs")
    Result<List<Long>> getRoleDataOrgs(@PathVariable("roleId") Long roleId);
}
