package com.adrug.erp.svc.user.auth.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.auth.convert.RoleConvert;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.entity.AccountRole;
import com.adrug.erp.svc.user.auth.entity.Role;
import com.adrug.erp.svc.user.auth.entity.RoleDataOrg;
import com.adrug.erp.svc.user.auth.entity.RolePermission;
import com.adrug.erp.svc.user.auth.provider.PermissionProvider;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.repository.AccountRoleRepository;
import com.adrug.erp.svc.user.auth.repository.RoleDataOrgRepository;
import com.adrug.erp.svc.user.auth.repository.RolePermissionRepository;
import com.adrug.erp.svc.user.auth.repository.RoleRepository;
import com.adrug.erp.svc.user.auth.vo.AccountAuthVO;
import com.adrug.erp.svc.user.auth.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权限服务实现。
 */
@Service
public class PermissionProviderImpl implements PermissionProvider {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Autowired
    private RoleDataOrgRepository roleDataOrgRepository;

    // ===== 角色 CRUD =====

    @Override
    public PageResult<RoleVO> page(RoleQuery query) {
        query.setTenantId(TenantContextHolder.get());
        PageResult<Role> pageResult = roleRepository.selectPage(query);
        List<RoleVO> records = RoleConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public RoleVO getById(Long id) {
        Role role = roleRepository.selectById(id);
        if (role == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return RoleConvert.toVO(role);
    }

    @Override
    public Long create(RoleSaveDTO dto) {
        Role role = RoleConvert.toEntity(dto);
        role.setId(null);
        roleRepository.insert(role);
        return role.getId();
    }

    @Override
    public void update(Long id, RoleSaveDTO dto) {
        Role existing = roleRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Role role = RoleConvert.toEntity(dto);
        role.setId(id);
        roleRepository.updateById(role);
    }

    @Override
    public void delete(Long id) {
        Role existing = roleRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        roleRepository.deleteById(id);
    }

    // ===== 角色-权限 =====

    @Override
    public void saveRolePermissions(Long roleId, List<String> permissions) {
        rolePermissionRepository.deleteByRoleId(roleId);
        if (permissions == null || permissions.isEmpty()) {
            return;
        }
        List<RolePermission> list = permissions.stream().map(p -> {
            RolePermission rp = new RolePermission();
            rp.setRoleId(roleId);
            rp.setPermission(p);
            return rp;
        }).collect(Collectors.toList());
        rolePermissionRepository.insertBatch(list);
    }

    @Override
    public List<String> getRolePermissions(Long roleId) {
        return rolePermissionRepository.selectByRoleId(roleId).stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());
    }

    // ===== 账号-角色 =====

    @Override
    public void saveAccountRoles(Long accountId, List<Long> roleIds) {
        accountRoleRepository.deleteByAccountId(accountId);
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        List<AccountRole> list = roleIds.stream().map(roleId -> {
            AccountRole ar = new AccountRole();
            ar.setAccountId(accountId);
            ar.setRoleId(roleId);
            return ar;
        }).collect(Collectors.toList());
        accountRoleRepository.insertBatch(list);
    }

    @Override
    public List<Long> getAccountRoleIds(Long accountId) {
        return accountRoleRepository.selectByAccountId(accountId).stream()
                .map(AccountRole::getRoleId)
                .collect(Collectors.toList());
    }

    // ===== 角色-数据机构 =====

    @Override
    public void saveRoleDataOrgs(Long roleId, List<Long> orgIds) {
        roleDataOrgRepository.deleteByRoleId(roleId);
        if (orgIds == null || orgIds.isEmpty()) {
            return;
        }
        List<RoleDataOrg> list = orgIds.stream().map(orgId -> {
            RoleDataOrg rdo = new RoleDataOrg();
            rdo.setRoleId(roleId);
            rdo.setOrgId(orgId);
            return rdo;
        }).collect(Collectors.toList());
        roleDataOrgRepository.insertBatch(list);
    }

    @Override
    public List<Long> getRoleDataOrgs(Long roleId) {
        return roleDataOrgRepository.selectByRoleId(roleId).stream()
                .map(RoleDataOrg::getOrgId)
                .collect(Collectors.toList());
    }

    // ===== 登录用 =====

    @Override
    public AccountAuthVO getAccountAuth(Long accountId) {
        List<AccountRole> accountRoles = accountRoleRepository.selectByAccountId(accountId);
        if (accountRoles.isEmpty()) {
            AccountAuthVO empty = new AccountAuthVO();
            empty.setRoles(Collections.emptyList());
            empty.setPermissions(Collections.emptyList());
            empty.setDataScope(DataScopeEnum.SELF.getCode());
            empty.setDataScopeOrgIds(Collections.emptyList());
            return empty;
        }
        List<Long> roleIds = accountRoles.stream().map(AccountRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleRepository.selectByIds(roleIds);
        List<String> roleCodes = roles.stream().map(Role::getRoleCode).collect(Collectors.toList());
        List<String> permissions = rolePermissionRepository.selectByRoleIds(roleIds).stream()
                .map(RolePermission::getPermission)
                .distinct()
                .collect(Collectors.toList());

        // 数据范围：取最宽松（code 最小）
        int dataScope = roles.stream()
                .map(Role::getDataScope)
                .filter(Objects::nonNull)
                .map(DataScopeEnum::getCode)
                .min(Integer::compareTo)
                .orElse(DataScopeEnum.SELF.getCode());

        List<Long> dataScopeOrgIds = Collections.emptyList();
        if (dataScope == DataScopeEnum.CUSTOM.getCode()) {
            dataScopeOrgIds = roleDataOrgRepository.selectByRoleIds(roleIds).stream()
                    .map(RoleDataOrg::getOrgId)
                    .distinct()
                    .collect(Collectors.toList());
        }

        AccountAuthVO vo = new AccountAuthVO();
        vo.setRoles(roleCodes);
        vo.setPermissions(permissions);
        vo.setDataScope(dataScope);
        vo.setDataScopeOrgIds(dataScopeOrgIds);
        return vo;
    }
}
