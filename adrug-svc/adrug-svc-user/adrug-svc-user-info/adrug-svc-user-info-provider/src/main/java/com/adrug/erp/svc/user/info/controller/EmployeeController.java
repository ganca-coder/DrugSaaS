package com.adrug.erp.svc.user.info.controller;

import com.adrug.erp.common.result.Result;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.provider.EmployeeProvider;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 员工信息 Controller。
 * <p>
 * URL 路径与 {@code EmployeeFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/info/employee")
public class EmployeeController {
    @Autowired
    private EmployeeProvider employeeService;

    @PostMapping("/page")
    public Result<PageResult<EmployeeVO>> page(@RequestBody EmployeeQuery query) {
        return Result.success(employeeService.page(query));
    }

    @GetMapping("/{id}")
    public Result<EmployeeVO> getById(@PathVariable("id") Long id) {
        return Result.success(employeeService.getById(id));
    }

    @PostMapping("/listByIds")
    public Result<List<EmployeeVO>> listByIds(@RequestBody List<Long> ids) {
        return Result.success(employeeService.getByIds(ids));
    }

    @PostMapping
    public Result<Long> create(@RequestBody EmployeeSaveDTO dto) {
        return Result.success(employeeService.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody EmployeeSaveDTO dto) {
        employeeService.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return Result.success();
    }
}
