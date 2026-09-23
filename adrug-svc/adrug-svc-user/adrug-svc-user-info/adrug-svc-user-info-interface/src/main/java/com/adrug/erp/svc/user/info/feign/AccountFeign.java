package com.adrug.erp.svc.user.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.provider.AccountProvider;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.vo.AccountCredentialVO;
import com.adrug.erp.svc.user.info.vo.AccountVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 账号 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-user-info", path = "/svc/user/info/account", contextId = "accountFeign")
public interface AccountFeign {

    @ProviderService(provider = AccountProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<AccountVO>> page(@RequestBody AccountQuery query);

    @ProviderService(provider = AccountProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<AccountVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = AccountProvider.class, method = "getByUsername")
    @GetMapping("/username/{username}")
    Result<AccountCredentialVO> getByUsername(@PathVariable("username") String username);

    @ProviderService(provider = AccountProvider.class, method = "getByEmployeeId")
    @GetMapping("/employee/{employeeId}")
    Result<AccountVO> getByEmployeeId(@PathVariable("employeeId") Long employeeId);

    @ProviderService(provider = AccountProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody AccountSaveDTO dto);

    @ProviderService(provider = AccountProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody AccountSaveDTO dto);

    @ProviderService(provider = AccountProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
