package com.adrug.erp.app.oper.user.controller;

import com.adrug.erp.app.oper.user.service.MemberQueryService;
import com.adrug.erp.app.oper.user.service.MemberService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
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
 * 会员管理 Controller（应用层，读写分离）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/user/member")
public class MemberAppController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberQueryService memberQueryService;

    @PostMapping("/getMemberPage")
    public Result<PageResult<MemberVO>> getMemberPage(@RequestBody MemberQuery query) {
        return Result.success(memberQueryService.getMemberPage(query));
    }

    @GetMapping("/{id}")
    public Result<MemberVO> getMemberById(@PathVariable("id") Long id) {
        return Result.success(memberQueryService.getMemberById(id));
    }

    @PostMapping
    public Result<Long> createMember(@RequestBody MemberSaveDTO dto) {
        return Result.success(memberService.createMember(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> updateMember(@PathVariable("id") Long id, @RequestBody MemberSaveDTO dto) {
        memberService.updateMember(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return Result.success();
    }
}
