package com.adrug.erp.svc.bus.pms.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.PurchaseOrderProvider;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 采购订单 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code PurchaseOrderController} 保持一致。
 */
@FeignClient(name = "adrug-svc-bus-pms", path = "/svc/bus/pms/purchase-order", contextId = "purchaseOrderFeign")
public interface PurchaseOrderFeign {

    @ProviderService(provider = PurchaseOrderProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<PurchaseOrderVO>> page(@RequestBody PurchaseOrderQuery query);

    @ProviderService(provider = PurchaseOrderProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<PurchaseOrderVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = PurchaseOrderProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody PurchaseOrderSaveDTO dto);

    @ProviderService(provider = PurchaseOrderProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody PurchaseOrderSaveDTO dto);

    @ProviderService(provider = PurchaseOrderProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);

    @ProviderService(provider = PurchaseOrderProvider.class, method = "post")
    @PostMapping("/{id}/post")
    Result<Void> post(@PathVariable("id") Long id);
}
