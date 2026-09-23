package com.adrug.erp.svc.bus.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.provider.WarehouseProvider;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;
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
 * 仓库信息 Controller。
 * <p>
 * URL 路径与 {@code WarehouseFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/info/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseProvider warehouseProvider;

    @PostMapping("/page")
    public Result<PageResult<WarehouseVO>> page(@RequestBody WarehouseQuery query) {
        return Result.success(warehouseProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<WarehouseVO> getById(@PathVariable("id") Long id) {
        return Result.success(warehouseProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody WarehouseSaveDTO dto) {
        return Result.success(warehouseProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody WarehouseSaveDTO dto) {
        warehouseProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        warehouseProvider.delete(id);
        return Result.success();
    }
}
