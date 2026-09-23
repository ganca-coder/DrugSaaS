package com.adrug.erp.svc.bus.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.provider.SupplierProvider;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商信息 Controller。
 * <p>
 * URL 路径与 {@code SupplierFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/info/supplier")
public class SupplierController {

    @Autowired
    private SupplierProvider supplierProvider;

    @PostMapping("/page")
    public Result<PageResult<SupplierVO>> page(@RequestBody SupplierQuery query) {
        return Result.success(supplierProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<SupplierVO> getById(@PathVariable("id") Long id) {
        return Result.success(supplierProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody SupplierSaveDTO dto) {
        return Result.success(supplierProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody SupplierSaveDTO dto) {
        supplierProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        supplierProvider.delete(id);
        return Result.success();
    }
}
