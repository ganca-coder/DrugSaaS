package com.adrug.erp.svc.user.auth.repository;

import com.adrug.erp.svc.user.auth.entity.AccountRole;

import java.util.List;

/**
 * 账号-角色关联数据访问接口。
 */
public interface AccountRoleRepository {

    List<AccountRole> selectByAccountId(Long accountId);

    void deleteByAccountId(Long accountId);

    void insertBatch(List<AccountRole> list);
}
