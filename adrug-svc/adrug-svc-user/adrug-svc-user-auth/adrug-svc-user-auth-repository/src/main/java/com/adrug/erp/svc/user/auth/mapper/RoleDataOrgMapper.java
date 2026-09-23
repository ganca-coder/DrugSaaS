package com.adrug.erp.svc.user.auth.mapper;

import com.adrug.erp.svc.user.auth.entity.RoleDataOrg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色-数据机构关联 Mapper。
 */
@Mapper
public interface RoleDataOrgMapper extends BaseMapper<RoleDataOrg> {
}
