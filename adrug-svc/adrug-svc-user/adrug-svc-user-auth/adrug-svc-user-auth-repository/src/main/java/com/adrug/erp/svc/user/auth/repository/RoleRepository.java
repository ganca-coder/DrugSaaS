package com.adrug.erp.svc.user.auth.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.auth.entity.Role;
import com.adrug.erp.svc.user.auth.query.RoleQuery;

import java.util.Collection;
import java.util.List;

/**
 * 角色数据访问接口。
 */
public interface RoleRepository {

    PageResult<Role> selectPage(RoleQuery query);

    Role selectById(Long id);

    List<Role> selectByIds(Collection<Long> ids);

    void insert(Role role);

    void updateById(Role role);

    void deleteById(Long id);
}
