package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.AuthService;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.auth.dto.AuthLoginDTO;
import com.adrug.erp.svc.user.auth.dto.LoginDTO;
import com.adrug.erp.svc.user.auth.feign.AuthFeign;
import com.adrug.erp.svc.user.auth.feign.PermissionFeign;
import com.adrug.erp.svc.user.auth.vo.AccountAuthVO;
import com.adrug.erp.svc.user.auth.vo.LoginVO;
import com.adrug.erp.svc.user.info.feign.AccountFeign;
import com.adrug.erp.svc.user.info.feign.OrgFeign;
import com.adrug.erp.svc.user.info.vo.AccountCredentialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 授权服务实现（应用层编排，直接调用 Feign）。
 * <p>
 * 编排登录流程：查账号（user-info）→ 查角色/权限/数据范围（auth）→ 解析数据范围机构 → 交授权中心做密码匹配与签发。
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountFeign accountFeign;
    @Autowired
    private OrgFeign orgFeign;
    @Autowired
    private PermissionFeign permissionFeign;
    @Autowired
    private AuthFeign authFeign;

    @Override
    public LoginVO login(LoginDTO dto) {
        AccountCredentialVO account = accountFeign.getByUsername(dto.getUsername()).getData();
        if (account == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "账号或密码错误");
        }
        AccountAuthVO auth = permissionFeign.getAccountAuth(account.getId()).getData();

        Integer dataScope = (auth == null || auth.getDataScope() == null)
                ? DataScopeEnum.SELF.getCode() : auth.getDataScope();
        List<Long> dataScopeOrgIds = resolveDataScopeOrgIds(dataScope, account.getOrgId(),
                auth == null ? null : auth.getDataScopeOrgIds());

        AuthLoginDTO authLogin = new AuthLoginDTO();
        authLogin.setUsername(account.getUsername());
        authLogin.setPassword(dto.getPassword());
        authLogin.setPasswordHash(account.getPassword());
        authLogin.setAccountId(account.getId());
        authLogin.setTenantId(account.getTenantId());
        authLogin.setOrgId(account.getOrgId());
        authLogin.setEmployeeId(account.getEmployeeId());
        authLogin.setAccountType(account.getAccountType());
        authLogin.setStatus(account.getStatus());
        authLogin.setRoles(auth == null ? null : auth.getRoles());
        authLogin.setPermissions(auth == null ? null : auth.getPermissions());
        authLogin.setDataScope(dataScope);
        authLogin.setDataScopeOrgIds(dataScopeOrgIds);

        return authFeign.login(authLogin).getData();
    }

    private List<Long> resolveDataScopeOrgIds(Integer dataScope, Long orgId, List<Long> customOrgIds) {
        if (dataScope == null) {
            return Collections.emptyList();
        }
        int scope = dataScope;
        if (scope == DataScopeEnum.ALL.getCode() || scope == DataScopeEnum.SELF.getCode()) {
            return Collections.emptyList();
        }
        if (scope == DataScopeEnum.CUSTOM.getCode()) {
            // 多机构及下级：每个绑定机构取「本机构及下级」，再取并集
            if (customOrgIds == null || customOrgIds.isEmpty()) {
                return Collections.emptyList();
            }
            java.util.LinkedHashSet<Long> set = new java.util.LinkedHashSet<>();
            for (Long oid : customOrgIds) {
                if (oid != null) {
                    set.addAll(orgFeign.getDescendantOrgIds(oid).getData());
                }
            }
            return new java.util.ArrayList<>(set);
        }
        if (scope == DataScopeEnum.ORG.getCode()) {
            return orgId == null ? Collections.emptyList() : List.of(orgId);
        }
        if (scope == DataScopeEnum.ORG_AND_CHILD.getCode()) {
            return orgId == null ? Collections.emptyList() : orgFeign.getDescendantOrgIds(orgId).getData();
        }
        return Collections.emptyList();
    }
}
