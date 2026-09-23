package com.adrug.erp.svc.user.info.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.entity.Account;
import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import com.adrug.erp.svc.user.info.mapper.AccountMapper;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.repository.AccountRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 账号数据访问实现。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public PageResult<Account> selectPage(AccountQuery query) {
        Page<Account> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Account::getUsername, query.getKeyword()));

        wrapper.eq(query.getId() != null, Account::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Account::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Account::getOrgId, query.getOrgId());
        wrapper.eq(StringUtils.hasText(query.getUsername()), Account::getUsername, query.getUsername());
        wrapper.eq(query.getEmployeeId() != null, Account::getEmployeeId, query.getEmployeeId());
        AccountTypeEnum accountType = BaseEnum.ofCode(AccountTypeEnum.class, query.getAccountType());
        wrapper.eq(accountType != null, Account::getAccountType, accountType);
        AccountStatusEnum status = BaseEnum.ofCode(AccountStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Account::getStatus, status);

        wrapper.orderByDesc(Account::getCreateTime);

        Page<Account> result = accountMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Account selectById(Long id) {
        return accountMapper.selectById(id);
    }

    @Override
    public Account selectByUsername(String username) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUsername, username);
        return accountMapper.selectOne(wrapper);
    }

    @Override
    public Account selectByEmployeeId(Long employeeId) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getEmployeeId, employeeId);
        return accountMapper.selectOne(wrapper);
    }

    @Override
    public void insert(Account account) {
        accountMapper.insert(account);
    }

    @Override
    public void updateById(Account account) {
        accountMapper.updateById(account);
    }

    @Override
    public void deleteById(Long id) {
        accountMapper.deleteById(id);
    }
}
