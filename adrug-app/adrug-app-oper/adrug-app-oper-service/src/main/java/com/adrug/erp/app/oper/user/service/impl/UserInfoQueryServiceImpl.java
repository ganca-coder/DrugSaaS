package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.UserInfoQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.feign.AccountFeign;
import com.adrug.erp.svc.user.info.feign.EmployeeFeign;
import com.adrug.erp.svc.user.info.feign.OrgFeign;
import com.adrug.erp.svc.user.info.feign.TenantFeign;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.vo.AccountVO;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import com.adrug.erp.svc.user.info.vo.OrgVO;
import com.adrug.erp.svc.user.info.vo.TenantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class UserInfoQueryServiceImpl implements UserInfoQueryService {

    @Autowired
    private EmployeeFeign employeeFeign;
    @Autowired
    private TenantFeign tenantFeign;
    @Autowired
    private OrgFeign orgFeign;
    @Autowired
    private AccountFeign accountFeign;

    @Override
    public PageResult<EmployeeVO> getEmployeePage(EmployeeQuery query) {
        return employeeFeign.page(query).getData();
    }

    @Override
    public EmployeeVO getEmployeeById(Long id) {
        return employeeFeign.getById(id).getData();
    }

    @Override
    public PageResult<TenantVO> getTenantPage(TenantQuery query) {
        return tenantFeign.page(query).getData();
    }

    @Override
    public TenantVO getTenantById(Long id) {
        return tenantFeign.getById(id).getData();
    }

    @Override
    public PageResult<OrgVO> getOrgPage(OrgQuery query) {
        return orgFeign.page(query).getData();
    }

    @Override
    public OrgVO getOrgById(Long id) {
        return orgFeign.getById(id).getData();
    }

    @Override
    public PageResult<AccountVO> getAccountPage(AccountQuery query) {
        return accountFeign.page(query).getData();
    }

    @Override
    public AccountVO getAccountById(Long id) {
        return accountFeign.getById(id).getData();
    }

    @Override
    public AccountVO getAccountByEmployeeId(Long employeeId) {
        return accountFeign.getByEmployeeId(employeeId).getData();
    }
}
