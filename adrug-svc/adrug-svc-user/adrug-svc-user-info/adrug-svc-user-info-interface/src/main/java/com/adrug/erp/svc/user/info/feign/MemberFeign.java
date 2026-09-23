package com.adrug.erp.svc.user.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
import com.adrug.erp.svc.user.info.provider.MemberProvider;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 会员信息 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code MemberController} 保持一致。
 */
@FeignClient(name = "adrug-svc-user-info", path = "/svc/user/info/member", contextId = "memberFeign")
public interface MemberFeign {

    @ProviderService(provider = MemberProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<MemberVO>> page(@RequestBody MemberQuery query);

    @ProviderService(provider = MemberProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<MemberVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = MemberProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody MemberSaveDTO dto);

    @ProviderService(provider = MemberProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody MemberSaveDTO dto);

    @ProviderService(provider = MemberProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
