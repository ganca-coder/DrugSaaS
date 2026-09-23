package com.adrug.erp.svc.bus.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.provider.WarehouseProvider;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 仓库信息 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-bus-info", path = "/svc/bus/info/warehouse", contextId = "warehouseFeign")
public interface WarehouseFeign {

    @ProviderService(provider = WarehouseProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<WarehouseVO>> page(@RequestBody WarehouseQuery query);

    @ProviderService(provider = WarehouseProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<WarehouseVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = WarehouseProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody WarehouseSaveDTO dto);

    @ProviderService(provider = WarehouseProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody WarehouseSaveDTO dto);

    @ProviderService(provider = WarehouseProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
