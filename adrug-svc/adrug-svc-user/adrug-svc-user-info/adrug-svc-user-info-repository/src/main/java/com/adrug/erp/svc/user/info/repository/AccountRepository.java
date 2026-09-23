package com.adrug.erp.svc.user.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.entity.Account;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.repository.impl.AccountRepositoryImpl;

/**
 * 账号数据访问接口（依赖倒置契约）。
 * <p>
 * 具体实现见 {@link AccountRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface AccountRepository {

    PageResult<Account> selectPage(AccountQuery query);

    Account selectById(Long id);

    Account selectByUsername(String username);

    Account selectByEmployeeId(Long employeeId);

    void insert(Account account);

    void updateById(Account account);

    void deleteById(Long id);
}
