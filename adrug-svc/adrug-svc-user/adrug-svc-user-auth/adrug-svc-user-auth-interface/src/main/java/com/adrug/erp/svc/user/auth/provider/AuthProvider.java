package com.adrug.erp.svc.user.auth.provider;

import com.adrug.erp.svc.user.auth.dto.AuthLoginDTO;
import com.adrug.erp.svc.user.auth.vo.LoginVO;

/**
 * 授权服务接口（依赖倒置契约）。
 */
public interface AuthProvider {

    /**
     * 登录：密码匹配 + 签发 JWT。
     * <p>
     * 账号查询、角色/权限查询由应用层编排完成，本方法仅做凭证校验与 token 生成。
     *
     * @param dto 登录入参（账号凭证 + 角色/权限）
     * @return 登录结果（token + 用户信息）
     */
    LoginVO login(AuthLoginDTO dto);
}
