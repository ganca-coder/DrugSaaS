package com.adrug.erp.svc.user.auth.mapper;

import com.adrug.erp.svc.user.auth.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色-权限关联 Mapper。
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
