package com.adrug.erp.svc.bus.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.provider.DrugProvider;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 药品信息 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * provider 模块的 {@code DrugController} 保持一致。
 */
@FeignClient(name = "adrug-svc-bus-info", path = "/svc/bus/info/drug", contextId = "drugFeign")
public interface DrugFeign {

    @ProviderService(provider = DrugProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<DrugVO>> page(@RequestBody DrugQuery query);

    @ProviderService(provider = DrugProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<DrugVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = DrugProvider.class, method = "getByIds")
    @PostMapping("/listByIds")
    Result<List<DrugVO>> listByIds(@RequestBody List<Long> ids);

    @ProviderService(provider = DrugProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody DrugSaveDTO dto);

    @ProviderService(provider = DrugProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody DrugSaveDTO dto);

    @ProviderService(provider = DrugProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
