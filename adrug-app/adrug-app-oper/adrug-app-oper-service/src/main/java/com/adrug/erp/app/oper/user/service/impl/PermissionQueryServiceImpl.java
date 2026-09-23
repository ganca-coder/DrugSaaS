package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.PermissionQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.auth.feign.PermissionFeign;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限（角色）查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class PermissionQueryServiceImpl implements PermissionQueryService {

    @Autowired
    private PermissionFeign permissionFeign;

    @Override
    public PageResult<RoleVO> getRolePage(RoleQuery query) {
        return permissionFeign.page(query).getData();
    }

    @Override
    public RoleVO getRoleById(Long id) {
        return permissionFeign.getById(id).getData();
    }

    @Override
    public List<String> getRolePermissions(Long roleId) {
        return permissionFeign.getRolePermissions(roleId).getData();
    }

    @Override
    public List<Long> getAccountRoleIds(Long accountId) {
        return permissionFeign.getAccountRoleIds(accountId).getData();
    }
}
