package com.adrug.erp.svc.user.auth.mapper;

import com.adrug.erp.svc.user.auth.entity.AccountRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号-角色关联 Mapper。
 */
@Mapper
public interface AccountRoleMapper extends BaseMapper<AccountRole> {
}
