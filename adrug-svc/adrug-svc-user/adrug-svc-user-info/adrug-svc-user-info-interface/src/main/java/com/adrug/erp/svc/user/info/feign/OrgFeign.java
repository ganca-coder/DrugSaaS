package com.adrug.erp.svc.user.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.provider.OrgProvider;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.vo.OrgVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 机构 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-user-info", path = "/svc/user/info/org", contextId = "orgFeign")
public interface OrgFeign {

    @ProviderService(provider = OrgProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<OrgVO>> page(@RequestBody OrgQuery query);

    @ProviderService(provider = OrgProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<OrgVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = OrgProvider.class, method = "getDescendantOrgIds")
    @GetMapping("/{id}/descendants")
    Result<List<Long>> getDescendantOrgIds(@PathVariable("id") Long id);

    @ProviderService(provider = OrgProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody OrgSaveDTO dto);

    @ProviderService(provider = OrgProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody OrgSaveDTO dto);

    @ProviderService(provider = OrgProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
