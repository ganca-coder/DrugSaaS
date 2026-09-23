package com.adrug.erp.svc.user.auth.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.query.RoleQuery;
import com.adrug.erp.svc.user.auth.vo.AccountAuthVO;
import com.adrug.erp.svc.user.auth.vo.RoleVO;

import java.util.List;

/**
 * 权限服务接口（依赖倒置契约）。
 */
public interface PermissionProvider {

    // ===== 角色 CRUD =====

    PageResult<RoleVO> page(RoleQuery query);

    RoleVO getById(Long id);

    Long create(RoleSaveDTO dto);

    void update(Long id, RoleSaveDTO dto);

    void delete(Long id);

    // ===== 角色-权限 =====

    /**
     * 保存角色权限（全量覆盖）。
     */
    void saveRolePermissions(Long roleId, List<String> permissions);

    List<String> getRolePermissions(Long roleId);

    // ===== 账号-角色 =====

    /**
     * 保存账号角色（全量覆盖）。
     */
    void saveAccountRoles(Long accountId, List<Long> roleIds);

    List<Long> getAccountRoleIds(Long accountId);

    // ===== 角色-数据机构 =====

    /**
     * 保存角色数据机构（全量覆盖，数据范围为「自定义」时使用）。
     */
    void saveRoleDataOrgs(Long roleId, List<Long> orgIds);

    List<Long> getRoleDataOrgs(Long roleId);

    // ===== 登录用 =====

    /**
     * 查询账号的角色码与权限标识（登录签发 JWT 用）。
     */
    AccountAuthVO getAccountAuth(Long accountId);
}
