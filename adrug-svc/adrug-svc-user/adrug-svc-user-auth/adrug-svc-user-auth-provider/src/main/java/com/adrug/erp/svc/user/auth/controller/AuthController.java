package com.adrug.erp.svc.user.auth.controller;

import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.auth.dto.AuthLoginDTO;
import com.adrug.erp.svc.user.auth.provider.AuthProvider;
import com.adrug.erp.svc.user.auth.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权 Controller。
 * <p>
 * URL 路径与 {@code AuthFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/auth")
public class AuthController {

    @Autowired
    private AuthProvider authProvider;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody AuthLoginDTO dto) {
        return Result.success(authProvider.login(dto));
    }
}
