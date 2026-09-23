package com.adrug.erp.svc.bus.pms.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.ReceivingOrderProvider;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 收货单 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code ReceivingOrderController} 保持一致。
 */
@FeignClient(name = "adrug-svc-bus-pms", path = "/svc/bus/pms/receiving-order", contextId = "receivingOrderFeign")
public interface ReceivingOrderFeign {

    @ProviderService(provider = ReceivingOrderProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<ReceivingOrderVO>> page(@RequestBody ReceivingOrderQuery query);

    @ProviderService(provider = ReceivingOrderProvider.class, method = "loadItemsFromPurchaseOrder")
    @GetMapping("/transfer/{purchaseOrderId}")
    Result<List<ReceivingOrderItemVO>> loadItemsFromPurchaseOrder(@PathVariable("purchaseOrderId") Long purchaseOrderId);

    @ProviderService(provider = ReceivingOrderProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<ReceivingOrderVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = ReceivingOrderProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody ReceivingOrderSaveDTO dto);

    @ProviderService(provider = ReceivingOrderProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody ReceivingOrderSaveDTO dto);

    @ProviderService(provider = ReceivingOrderProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);

    @ProviderService(provider = ReceivingOrderProvider.class, method = "post")
    @PostMapping("/{id}/post")
    Result<Void> post(@PathVariable("id") Long id);
}
