package com.adrug.erp.svc.bus.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.provider.DrugProvider;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
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
 * 药品信息 Controller。
 * <p>
 * URL 路径与 {@code DrugFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/info/drug")
public class DrugController {

    @Autowired
    private DrugProvider drugProvider;

    @PostMapping("/page")
    public Result<PageResult<DrugVO>> page(@RequestBody DrugQuery query) {
        return Result.success(drugProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<DrugVO> getById(@PathVariable("id") Long id) {
        return Result.success(drugProvider.getById(id));
    }

    @PostMapping("/listByIds")
    public Result<List<DrugVO>> listByIds(@RequestBody List<Long> ids) {
        return Result.success(drugProvider.getByIds(ids));
    }

    @PostMapping
    public Result<Long> create(@RequestBody DrugSaveDTO dto) {
        return Result.success(drugProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody DrugSaveDTO dto) {
        drugProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        drugProvider.delete(id);
        return Result.success();
    }
}
