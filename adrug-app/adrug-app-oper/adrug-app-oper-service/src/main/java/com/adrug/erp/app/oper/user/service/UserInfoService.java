package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;

/**
 * 用户信息服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface UserInfoService {

    Long createEmployee(EmployeeSaveDTO dto);

    void updateEmployee(Long id, EmployeeSaveDTO dto);

    void deleteEmployee(Long id);

    Long createTenant(TenantSaveDTO dto);

    void updateTenant(Long id, TenantSaveDTO dto);

    void deleteTenant(Long id);

    Long createOrg(OrgSaveDTO dto);

    void updateOrg(Long id, OrgSaveDTO dto);

    void deleteOrg(Long id);

    Long createAccount(AccountSaveDTO dto);

    void updateAccount(Long id, AccountSaveDTO dto);

    void deleteAccount(Long id);
}
