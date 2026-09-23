package com.adrug.erp.app.oper.common.controller;

import com.adrug.erp.app.oper.common.service.CommonInfoQueryService;
import com.adrug.erp.app.oper.common.service.CommonInfoService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
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
 * 菜单信息 Controller（应用层，读写分离）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/common/info")
public class CommonInfoAppController {

    @Autowired
    private CommonInfoService commonInfoService;
    @Autowired
    private CommonInfoQueryService commonInfoQueryService;

    @GetMapping("/getMenuTree")
    public Result<List<MenuVO>> getMenuTree() {
        return Result.success(commonInfoQueryService.getMenuTree());
    }

    @PostMapping("/getMenuPage")
    public Result<PageResult<MenuVO>> getMenuPage(@RequestBody MenuQuery query) {
        return Result.success(commonInfoQueryService.getMenuPage(query));
    }

    @GetMapping("/menu/{id}")
    public Result<MenuVO> getMenuById(@PathVariable("id") Long id) {
        return Result.success(commonInfoQueryService.getMenuById(id));
    }

    @PostMapping("/menu")
    public Result<Long> createMenu(@RequestBody MenuSaveDTO dto) {
        return Result.success(commonInfoService.createMenu(dto));
    }

    @PutMapping("/menu/{id}")
    public Result<Void> updateMenu(@PathVariable("id") Long id, @RequestBody MenuSaveDTO dto) {
        commonInfoService.updateMenu(id, dto);
        return Result.success();
    }

    @DeleteMapping("/menu/{id}")
    public Result<Void> deleteMenu(@PathVariable("id") Long id) {
        commonInfoService.deleteMenu(id);
        return Result.success();
    }
}
