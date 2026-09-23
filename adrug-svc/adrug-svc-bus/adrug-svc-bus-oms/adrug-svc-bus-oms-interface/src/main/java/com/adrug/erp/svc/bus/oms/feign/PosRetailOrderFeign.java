package com.adrug.erp.svc.bus.oms.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.provider.PosRetailOrderProvider;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * POS零售单 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code PosRetailOrderController} 保持一致。
 */
@FeignClient(name = "adrug-svc-bus-oms", path = "/svc/bus/oms/pos-retail-order", contextId = "posRetailOrderFeign")
public interface PosRetailOrderFeign {

    @ProviderService(provider = PosRetailOrderProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<PosRetailOrderVO>> page(@RequestBody PosRetailOrderQuery query);

    @ProviderService(provider = PosRetailOrderProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<PosRetailOrderVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = PosRetailOrderProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody PosRetailOrderSaveDTO dto);

    @ProviderService(provider = PosRetailOrderProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody PosRetailOrderSaveDTO dto);

    @ProviderService(provider = PosRetailOrderProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
