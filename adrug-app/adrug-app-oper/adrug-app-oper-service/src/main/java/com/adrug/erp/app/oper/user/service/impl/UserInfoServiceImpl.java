package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.UserInfoService;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.feign.AccountFeign;
import com.adrug.erp.svc.user.info.feign.EmployeeFeign;
import com.adrug.erp.svc.user.info.feign.OrgFeign;
import com.adrug.erp.svc.user.info.feign.TenantFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private EmployeeFeign employeeFeign;
    @Autowired
    private TenantFeign tenantFeign;
    @Autowired
    private OrgFeign orgFeign;
    @Autowired
    private AccountFeign accountFeign;

    @Override
    public Long createEmployee(EmployeeSaveDTO dto) {
        return employeeFeign.create(dto).getData();
    }

    @Override
    public void updateEmployee(Long id, EmployeeSaveDTO dto) {
        employeeFeign.update(id, dto);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeFeign.delete(id);
    }

    @Override
    public Long createTenant(TenantSaveDTO dto) {
        return tenantFeign.create(dto).getData();
    }

    @Override
    public void updateTenant(Long id, TenantSaveDTO dto) {
        tenantFeign.update(id, dto);
    }

    @Override
    public void deleteTenant(Long id) {
        tenantFeign.delete(id);
    }

    @Override
    public Long createOrg(OrgSaveDTO dto) {
        return orgFeign.create(dto).getData();
    }

    @Override
    public void updateOrg(Long id, OrgSaveDTO dto) {
        orgFeign.update(id, dto);
    }

    @Override
    public void deleteOrg(Long id) {
        orgFeign.delete(id);
    }

    @Override
    public Long createAccount(AccountSaveDTO dto) {
        return accountFeign.create(dto).getData();
    }

    @Override
    public void updateAccount(Long id, AccountSaveDTO dto) {
        accountFeign.update(id, dto);
    }

    @Override
    public void deleteAccount(Long id) {
        accountFeign.delete(id);
    }
}
