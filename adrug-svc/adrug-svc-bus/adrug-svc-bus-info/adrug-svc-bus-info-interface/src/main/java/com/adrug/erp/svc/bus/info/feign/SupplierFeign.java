package com.adrug.erp.svc.bus.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.provider.SupplierProvider;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 供应商信息 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-bus-info", path = "/svc/bus/info/supplier", contextId = "supplierFeign")
public interface SupplierFeign {

    @ProviderService(provider = SupplierProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<SupplierVO>> page(@RequestBody SupplierQuery query);

    @ProviderService(provider = SupplierProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<SupplierVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = SupplierProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody SupplierSaveDTO dto);

    @ProviderService(provider = SupplierProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody SupplierSaveDTO dto);

    @ProviderService(provider = SupplierProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
