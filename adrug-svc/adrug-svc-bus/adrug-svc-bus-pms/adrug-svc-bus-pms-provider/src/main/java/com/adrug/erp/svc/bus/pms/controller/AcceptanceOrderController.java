package com.adrug.erp.svc.bus.pms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.AcceptanceOrderProvider;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;
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
 * 验收单 Controller。
 * <p>
 * URL 路径与 {@code AcceptanceOrderFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/pms/acceptance-order")
public class AcceptanceOrderController {

    @Autowired
    private AcceptanceOrderProvider acceptanceOrderProvider;

    @PostMapping("/page")
    public Result<PageResult<AcceptanceOrderVO>> page(@RequestBody AcceptanceOrderQuery query) {
        return Result.success(acceptanceOrderProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<AcceptanceOrderVO> getById(@PathVariable("id") Long id) {
        return Result.success(acceptanceOrderProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody AcceptanceOrderSaveDTO dto) {
        return Result.success(acceptanceOrderProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody AcceptanceOrderSaveDTO dto) {
        acceptanceOrderProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        acceptanceOrderProvider.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/accept")
    public Result<Void> accept(@PathVariable("id") Long id) {
        acceptanceOrderProvider.accept(id);
        return Result.success();
    }

    @PostMapping("/{id}/post")
    public Result<Void> post(@PathVariable("id") Long id) {
        acceptanceOrderProvider.post(id);
        return Result.success();
    }
}
