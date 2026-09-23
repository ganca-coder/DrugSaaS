package com.adrug.erp.svc.bus.oms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.provider.PosRetailOrderProvider;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;
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
 * POS零售单 Controller。
 * <p>
 * URL 路径与 {@code PosRetailOrderFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/oms/pos-retail-order")
public class PosRetailOrderController {

    @Autowired
    private PosRetailOrderProvider posRetailOrderProvider;

    @PostMapping("/page")
    public Result<PageResult<PosRetailOrderVO>> page(@RequestBody PosRetailOrderQuery query) {
        return Result.success(posRetailOrderProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<PosRetailOrderVO> getById(@PathVariable("id") Long id) {
        return Result.success(posRetailOrderProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody PosRetailOrderSaveDTO dto) {
        return Result.success(posRetailOrderProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody PosRetailOrderSaveDTO dto) {
        posRetailOrderProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        posRetailOrderProvider.delete(id);
        return Result.success();
    }
}
