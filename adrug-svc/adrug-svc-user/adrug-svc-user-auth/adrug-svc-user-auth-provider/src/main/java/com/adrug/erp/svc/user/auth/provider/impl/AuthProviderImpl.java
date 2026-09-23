package com.adrug.erp.svc.user.auth.provider.impl;

import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.core.util.JwtUtil;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.auth.dto.AuthLoginDTO;
import com.adrug.erp.svc.user.auth.provider.AuthProvider;
import com.adrug.erp.svc.user.auth.vo.LoginVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 授权服务实现。
 * <p>
 * 仅做密码匹配与 JWT 签发；账号查询、角色/权限查询由应用层编排完成。
 */
@Service
public class AuthProviderImpl implements AuthProvider {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginVO login(AuthLoginDTO dto) {
        if (dto.getStatus() == null || dto.getStatus() != 1) {
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "账号已停用");
        }
        if (!passwordEncoder.matches(dto.getPassword(), dto.getPasswordHash())) {
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "账号或密码错误");
        }

        String token = JwtUtil.generate(dto.getAccountId(), dto.getTenantId(), dto.getOrgId(), dto.getUsername(),
                dto.getRoles() == null ? Collections.emptyList() : dto.getRoles(),
                dto.getPermissions() == null ? Collections.emptyList() : dto.getPermissions(),
                dto.getDataScope(),
                dto.getDataScopeOrgIds() == null ? Collections.emptyList() : dto.getDataScopeOrgIds());

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setAccountId(dto.getAccountId());
        vo.setUsername(dto.getUsername());
        vo.setTenantId(dto.getTenantId());
        vo.setOrgId(dto.getOrgId());
        vo.setEmployeeId(dto.getEmployeeId());
        vo.setAccountType(dto.getAccountType());
        vo.setRoles(dto.getRoles());
        vo.setPermissions(dto.getPermissions());
        return vo;
    }
}
