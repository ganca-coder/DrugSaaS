package com.adrug.erp.svc.user.auth.repository.impl;

import com.adrug.erp.svc.user.auth.entity.AccountRole;
import com.adrug.erp.svc.user.auth.mapper.AccountRoleMapper;
import com.adrug.erp.svc.user.auth.repository.AccountRoleRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账号-角色关联数据访问实现。
 */
@Repository
public class AccountRoleRepositoryImpl implements AccountRoleRepository {

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Override
    public List<AccountRole> selectByAccountId(Long accountId) {
        LambdaQueryWrapper<AccountRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountRole::getAccountId, accountId);
        return accountRoleMapper.selectList(wrapper);
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        LambdaQueryWrapper<AccountRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountRole::getAccountId, accountId);
        accountRoleMapper.delete(wrapper);
    }

    @Override
    public void insertBatch(List<AccountRole> list) {
        for (AccountRole ar : list) {
            accountRoleMapper.insert(ar);
        }
    }
}
