package com.adrug.erp.svc.bus.pms.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.RejectionOrderProvider;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 拒收单 OpenFeign 客户端，与 Controller 接口联动。
 */
@FeignClient(name = "adrug-svc-bus-pms", path = "/svc/bus/pms/rejection-order", contextId = "rejectionOrderFeign")
public interface RejectionOrderFeign {

    @ProviderService(provider = RejectionOrderProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<RejectionOrderVO>> page(@RequestBody RejectionOrderQuery query);

    @ProviderService(provider = RejectionOrderProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<RejectionOrderVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = RejectionOrderProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody RejectionOrderSaveDTO dto);

    @ProviderService(provider = RejectionOrderProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody RejectionOrderSaveDTO dto);

    @ProviderService(provider = RejectionOrderProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);

    @ProviderService(provider = RejectionOrderProvider.class, method = "post")
    @PostMapping("/{id}/post")
    Result<Void> post(@PathVariable("id") Long id);
}
