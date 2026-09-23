package com.adrug.erp.svc.user.auth.mapper;

import com.adrug.erp.svc.user.auth.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色 Mapper。
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
