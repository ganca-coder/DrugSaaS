package com.adrug.erp.svc.user.info.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.provider.AccountProvider;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.vo.AccountCredentialVO;
import com.adrug.erp.svc.user.info.vo.AccountVO;
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
 * 账号 Controller。
 * <p>
 * URL 路径与 {@code AccountFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/user/info/account")
public class AccountController {

    @Autowired
    private AccountProvider accountProvider;

    @PostMapping("/page")
    public Result<PageResult<AccountVO>> page(@RequestBody AccountQuery query) {
        return Result.success(accountProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<AccountVO> getById(@PathVariable("id") Long id) {
        return Result.success(accountProvider.getById(id));
    }

    @GetMapping("/username/{username}")
    public Result<AccountCredentialVO> getByUsername(@PathVariable("username") String username) {
        return Result.success(accountProvider.getByUsername(username));
    }

    @GetMapping("/employee/{employeeId}")
    public Result<AccountVO> getByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        return Result.success(accountProvider.getByEmployeeId(employeeId));
    }

    @PostMapping
    public Result<Long> create(@RequestBody AccountSaveDTO dto) {
        return Result.success(accountProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody AccountSaveDTO dto) {
        accountProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        accountProvider.delete(id);
        return Result.success();
    }
}
