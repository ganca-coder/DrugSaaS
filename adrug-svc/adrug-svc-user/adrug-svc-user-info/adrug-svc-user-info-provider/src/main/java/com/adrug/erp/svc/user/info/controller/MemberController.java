package com.adrug.erp.svc.user.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
import com.adrug.erp.svc.user.info.provider.MemberProvider;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;
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
 * 会员信息 Controller。
 * <p>
 * URL 路径与 {@code MemberFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/info/member")
public class MemberController {

    @Autowired
    private MemberProvider memberProvider;

    @PostMapping("/page")
    public Result<PageResult<MemberVO>> page(@RequestBody MemberQuery query) {
        return Result.success(memberProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<MemberVO> getById(@PathVariable("id") Long id) {
        return Result.success(memberProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody MemberSaveDTO dto) {
        return Result.success(memberProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody MemberSaveDTO dto) {
        memberProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        memberProvider.delete(id);
        return Result.success();
    }
}
