package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.svc.user.auth.dto.LoginDTO;
import com.adrug.erp.svc.user.auth.vo.LoginVO;

/**
 * 授权服务接口（应用层编排）。
 */
public interface AuthService {

    LoginVO login(LoginDTO dto);
}
