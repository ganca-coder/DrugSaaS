package com.adrug.erp.app.oper.user.controller;

import com.adrug.erp.app.oper.user.service.AuthService;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.auth.dto.LoginDTO;
import com.adrug.erp.svc.user.auth.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权 Controller（应用层）。
 * <p>
 * 登录统一经应用层编排：Controller → Service → Rpc → Feign（adrug-svc-user-auth）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/user/auth")
public class AuthAppController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        return Result.success(authService.login(dto));
    }
}
