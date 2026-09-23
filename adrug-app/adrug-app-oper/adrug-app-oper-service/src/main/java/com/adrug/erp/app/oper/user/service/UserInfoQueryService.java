package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.vo.AccountVO;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import com.adrug.erp.svc.user.info.vo.OrgVO;
import com.adrug.erp.svc.user.info.vo.TenantVO;

/**
 * 用户信息查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface UserInfoQueryService {

    PageResult<EmployeeVO> getEmployeePage(EmployeeQuery query);

    EmployeeVO getEmployeeById(Long id);

    PageResult<TenantVO> getTenantPage(TenantQuery query);

    TenantVO getTenantById(Long id);

    PageResult<OrgVO> getOrgPage(OrgQuery query);

    OrgVO getOrgById(Long id);

    PageResult<AccountVO> getAccountPage(AccountQuery query);

    AccountVO getAccountById(Long id);

    AccountVO getAccountByEmployeeId(Long employeeId);
}
