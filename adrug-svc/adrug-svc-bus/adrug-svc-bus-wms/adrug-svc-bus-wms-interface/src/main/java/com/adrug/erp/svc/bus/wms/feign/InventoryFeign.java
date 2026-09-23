package com.adrug.erp.svc.bus.wms.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.wms.dto.InventoryIncreaseDTO;
import com.adrug.erp.svc.bus.wms.provider.InventoryProvider;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 库存 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-bus-wms", path = "/svc/bus/wms/inventory", contextId = "inventoryFeign")
public interface InventoryFeign {

    @ProviderService(provider = InventoryProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<InventoryVO>> page(@RequestBody InventoryQuery query);

    @ProviderService(provider = InventoryProvider.class, method = "increase")
    @PostMapping("/increase")
    Result<Void> increase(@RequestBody List<InventoryIncreaseDTO> items);
}
