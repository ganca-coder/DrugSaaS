package com.adrug.erp.svc.user.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.provider.OrgProvider;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.vo.OrgVO;
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
 * 机构 Controller。
 * <p>
 * URL 路径与 {@code OrgFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/info/org")
public class OrgController {

    @Autowired
    private OrgProvider orgProvider;

    @PostMapping("/page")
    public Result<PageResult<OrgVO>> page(@RequestBody OrgQuery query) {
        return Result.success(orgProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<OrgVO> getById(@PathVariable("id") Long id) {
        return Result.success(orgProvider.getById(id));
    }

    @GetMapping("/{id}/descendants")
    public Result<List<Long>> getDescendantOrgIds(@PathVariable("id") Long id) {
        return Result.success(orgProvider.getDescendantOrgIds(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody OrgSaveDTO dto) {
        return Result.success(orgProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody OrgSaveDTO dto) {
        orgProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        orgProvider.delete(id);
        return Result.success();
    }
}
