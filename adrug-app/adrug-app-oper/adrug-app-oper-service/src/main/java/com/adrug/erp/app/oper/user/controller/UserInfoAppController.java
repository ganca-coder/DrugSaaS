package com.adrug.erp.app.oper.user.controller;

import com.adrug.erp.app.oper.user.service.UserInfoQueryService;
import com.adrug.erp.app.oper.user.service.UserInfoService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.vo.AccountVO;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import com.adrug.erp.svc.user.info.vo.OrgVO;
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
 * 用户信息 Controller（应用层，读写分离）：员工 / 租户 / 机构 / 账号。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/user/info")
public class UserInfoAppController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoQueryService userInfoQueryService;

    // ===== 员工 =====

    @PostMapping("/getEmployeePage")
    public Result<PageResult<EmployeeVO>> getEmployeePage(@RequestBody EmployeeQuery query) {
        return Result.success(userInfoQueryService.getEmployeePage(query));
    }

    @GetMapping("/employee/{id}")
    public Result<EmployeeVO> getEmployeeById(@PathVariable("id") Long id) {
        return Result.success(userInfoQueryService.getEmployeeById(id));
    }

    @PostMapping("/employee")
    public Result<Long> createEmployee(@RequestBody EmployeeSaveDTO dto) {
        return Result.success(userInfoService.createEmployee(dto));
    }

    @PutMapping("/employee/{id}")
    public Result<Void> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeSaveDTO dto) {
        userInfoService.updateEmployee(id, dto);
        return Result.success();
    }

    @DeleteMapping("/employee/{id}")
    public Result<Void> deleteEmployee(@PathVariable("id") Long id) {
        userInfoService.deleteEmployee(id);
        return Result.success();
    }

    // ===== 租户 =====

    @PostMapping("/getTenantPage")
    public Result<PageResult<TenantVO>> getTenantPage(@RequestBody TenantQuery query) {
        return Result.success(userInfoQueryService.getTenantPage(query));
    }

    @GetMapping("/tenant/{id}")
    public Result<TenantVO> getTenantById(@PathVariable("id") Long id) {
        return Result.success(userInfoQueryService.getTenantById(id));
    }

    @PostMapping("/tenant")
    public Result<Long> createTenant(@RequestBody TenantSaveDTO dto) {
        return Result.success(userInfoService.createTenant(dto));
    }

    @PutMapping("/tenant/{id}")
    public Result<Void> updateTenant(@PathVariable("id") Long id, @RequestBody TenantSaveDTO dto) {
        userInfoService.updateTenant(id, dto);
        return Result.success();
    }

    @DeleteMapping("/tenant/{id}")
    public Result<Void> deleteTenant(@PathVariable("id") Long id) {
        userInfoService.deleteTenant(id);
        return Result.success();
    }

    // ===== 机构 =====

    @PostMapping("/getOrgPage")
    public Result<PageResult<OrgVO>> getOrgPage(@RequestBody OrgQuery query) {
        return Result.success(userInfoQueryService.getOrgPage(query));
    }

    @GetMapping("/org/{id}")
    public Result<OrgVO> getOrgById(@PathVariable("id") Long id) {
        return Result.success(userInfoQueryService.getOrgById(id));
    }

    @PostMapping("/org")
    public Result<Long> createOrg(@RequestBody OrgSaveDTO dto) {
        return Result.success(userInfoService.createOrg(dto));
    }

    @PutMapping("/org/{id}")
    public Result<Void> updateOrg(@PathVariable("id") Long id, @RequestBody OrgSaveDTO dto) {
        userInfoService.updateOrg(id, dto);
        return Result.success();
    }

    @DeleteMapping("/org/{id}")
    public Result<Void> deleteOrg(@PathVariable("id") Long id) {
        userInfoService.deleteOrg(id);
        return Result.success();
    }

    // ===== 账号 =====

    @PostMapping("/getAccountPage")
    public Result<PageResult<AccountVO>> getAccountPage(@RequestBody AccountQuery query) {
        return Result.success(userInfoQueryService.getAccountPage(query));
    }

    @GetMapping("/account/{id}")
    public Result<AccountVO> getAccountById(@PathVariable("id") Long id) {
        return Result.success(userInfoQueryService.getAccountById(id));
    }

    @GetMapping("/account/employee/{employeeId}")
    public Result<AccountVO> getAccountByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        return Result.success(userInfoQueryService.getAccountByEmployeeId(employeeId));
    }

    @PostMapping("/account")
    public Result<Long> createAccount(@RequestBody AccountSaveDTO dto) {
        return Result.success(userInfoService.createAccount(dto));
    }

    @PutMapping("/account/{id}")
    public Result<Void> updateAccount(@PathVariable("id") Long id, @RequestBody AccountSaveDTO dto) {
        userInfoService.updateAccount(id, dto);
        return Result.success();
    }

    @DeleteMapping("/account/{id}")
    public Result<Void> deleteAccount(@PathVariable("id") Long id) {
        userInfoService.deleteAccount(id);
        return Result.success();
    }
}
