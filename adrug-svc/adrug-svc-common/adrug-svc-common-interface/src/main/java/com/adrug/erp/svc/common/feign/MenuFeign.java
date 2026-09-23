package com.adrug.erp.svc.common.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
import com.adrug.erp.svc.common.provider.MenuProvider;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.vo.MenuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 菜单 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code MenuController} 保持一致。
 */
@FeignClient(name = "adrug-svc-common", path = "/svc/common/menu", contextId = "menuFeign")
public interface MenuFeign {

    @ProviderService(provider = MenuProvider.class, method = "tree")
    @GetMapping("/tree")
    Result<List<MenuVO>> tree();

    @ProviderService(provider = MenuProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<MenuVO>> page(@RequestBody MenuQuery query);

    @ProviderService(provider = MenuProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<MenuVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = MenuProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody MenuSaveDTO dto);

    @ProviderService(provider = MenuProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody MenuSaveDTO dto);

    @ProviderService(provider = MenuProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
