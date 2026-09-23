package com.adrug.erp.svc.bus.pms.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.AcceptanceOrderProvider;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 验收单 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code AcceptanceOrderController} 保持一致。
 */
@FeignClient(name = "adrug-svc-bus-pms", path = "/svc/bus/pms/acceptance-order", contextId = "acceptanceOrderFeign")
public interface AcceptanceOrderFeign {

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<AcceptanceOrderVO>> page(@RequestBody AcceptanceOrderQuery query);

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<AcceptanceOrderVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody AcceptanceOrderSaveDTO dto);

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody AcceptanceOrderSaveDTO dto);

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "accept")
    @PostMapping("/{id}/accept")
    Result<Void> accept(@PathVariable("id") Long id);

    @ProviderService(provider = AcceptanceOrderProvider.class, method = "post")
    @PostMapping("/{id}/post")
    Result<Void> post(@PathVariable("id") Long id);
}
