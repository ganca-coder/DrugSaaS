package com.adrug.erp.svc.user.info.feign;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.provider.EmployeeProvider;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 员工信息 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * service 模块的 {@code EmployeeController} 保持一致。
 */
@FeignClient(name = "adrug-svc-user-info", path = "/svc/user/info/employee", contextId = "employeeFeign")
public interface EmployeeFeign {

    @ProviderService(provider = EmployeeProvider.class, method = "page")
    @PostMapping("/page")
    Result<PageResult<EmployeeVO>> page(@RequestBody EmployeeQuery query);

    @ProviderService(provider = EmployeeProvider.class, method = "getById")
    @GetMapping("/{id}")
    Result<EmployeeVO> getById(@PathVariable("id") Long id);

    @ProviderService(provider = EmployeeProvider.class, method = "getByIds")
    @PostMapping("/listByIds")
    Result<List<EmployeeVO>> listByIds(@RequestBody List<Long> ids);

    @ProviderService(provider = EmployeeProvider.class, method = "create")
    @PostMapping
    Result<Long> create(@RequestBody EmployeeSaveDTO dto);

    @ProviderService(provider = EmployeeProvider.class, method = "update")
    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody EmployeeSaveDTO dto);

    @ProviderService(provider = EmployeeProvider.class, method = "delete")
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
