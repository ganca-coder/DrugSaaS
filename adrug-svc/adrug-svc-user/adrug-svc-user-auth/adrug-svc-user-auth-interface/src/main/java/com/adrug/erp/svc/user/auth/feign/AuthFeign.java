package com.adrug.erp.svc.user.auth.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.auth.dto.AuthLoginDTO;
import com.adrug.erp.svc.user.auth.provider.AuthProvider;
import com.adrug.erp.svc.user.auth.vo.LoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 授权 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-user-auth", path = "/svc/user/auth", contextId = "authFeign")
public interface AuthFeign {

    @ProviderService(provider = AuthProvider.class, method = "login")
    @PostMapping("/login")
    Result<LoginVO> login(@RequestBody AuthLoginDTO dto);
}
