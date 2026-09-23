package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.vo.RoleVO;

import java.util.List;

/**
 * 权限（角色）查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface PermissionQueryService {

    PageResult<RoleVO> getRolePage(RoleQuery query);

    RoleVO getRoleById(Long id);

    List<String> getRolePermissions(Long roleId);

    List<Long> getAccountRoleIds(Long accountId);
}
