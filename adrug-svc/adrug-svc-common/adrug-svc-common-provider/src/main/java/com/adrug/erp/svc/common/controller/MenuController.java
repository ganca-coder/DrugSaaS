package com.adrug.erp.svc.common.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
import com.adrug.erp.svc.common.provider.MenuProvider;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.vo.MenuVO;
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
 * 菜单 Controller。
 * <p>
 * URL 路径与 {@code MenuFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/common/menu")
public class MenuController {
    @Autowired
    private MenuProvider menuProvider;

    @GetMapping("/tree")
    public Result<List<MenuVO>> tree() {
        return Result.success(menuProvider.tree());
    }

    @PostMapping("/page")
    public Result<PageResult<MenuVO>> page(@RequestBody MenuQuery query) {
        return Result.success(menuProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<MenuVO> getById(@PathVariable("id") Long id) {
        return Result.success(menuProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody MenuSaveDTO dto) {
        return Result.success(menuProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody MenuSaveDTO dto) {
        menuProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        menuProvider.delete(id);
        return Result.success();
    }
}
