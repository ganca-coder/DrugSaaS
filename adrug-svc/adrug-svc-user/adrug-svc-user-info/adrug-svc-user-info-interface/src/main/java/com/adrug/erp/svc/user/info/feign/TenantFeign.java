package com.adrug.erp.svc.user.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.provider.TenantProvider;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.vo.TenantVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 租户 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-user-info", path = "/svc/user/info/tenant", contextId = "tenantFeign")
public interface TenantFeign {

    @ProviderService(provider = TenantProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<TenantVO>> page(@RequestBody TenantQuery query);

    @ProviderService(provider = TenantProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<TenantVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = TenantProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody TenantSaveDTO dto);

    @ProviderService(provider = TenantProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody TenantSaveDTO dto);

    @ProviderService(provider = TenantProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
