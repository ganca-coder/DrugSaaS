package com.adrug.erp.svc.user.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.provider.TenantProvider;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.vo.TenantVO;
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
 * 租户 Controller。
 * <p>
 * URL 路径与 {@code TenantFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/info/tenant")
public class TenantController {

    @Autowired
    private TenantProvider tenantProvider;

    @PostMapping("/page")
    public Result<PageResult<TenantVO>> page(@RequestBody TenantQuery query) {
        return Result.success(tenantProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<TenantVO> getById(@PathVariable("id") Long id) {
        return Result.success(tenantProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody TenantSaveDTO dto) {
        return Result.success(tenantProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody TenantSaveDTO dto) {
        tenantProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        tenantProvider.delete(id);
        return Result.success();
    }
}
