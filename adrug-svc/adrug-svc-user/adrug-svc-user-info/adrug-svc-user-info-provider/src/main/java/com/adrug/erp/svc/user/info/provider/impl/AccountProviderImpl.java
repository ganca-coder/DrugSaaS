package com.adrug.erp.svc.user.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.info.convert.AccountConvert;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.entity.Account;
import com.adrug.erp.svc.user.info.provider.AccountProvider;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.repository.AccountRepository;
import com.adrug.erp.svc.user.info.vo.AccountCredentialVO;
import com.adrug.erp.svc.user.info.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 账号服务实现。
 */
@Service
public class AccountProviderImpl implements AccountProvider {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public PageResult<AccountVO> page(AccountQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Account> pageResult = accountRepository.selectPage(query);
        List<AccountVO> records = AccountConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public AccountVO getById(Long id) {
        Account account = accountRepository.selectById(id);
        if (account == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return AccountConvert.toVO(account);
    }

    @Override
    public AccountCredentialVO getByUsername(String username) {
        Account account = accountRepository.selectByUsername(username);
        return account == null ? null : AccountConvert.toCredentialVO(account);
    }

    @Override
    public AccountVO getByEmployeeId(Long employeeId) {
        Account account = accountRepository.selectByEmployeeId(employeeId);
        return account == null ? null : AccountConvert.toVO(account);
    }

    @Override
    public Long create(AccountSaveDTO dto) {
        Account account = AccountConvert.toEntity(dto);
        account.setId(null);
        if (!StringUtils.hasText(dto.getPassword())) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "密码不能为空");
        }
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        accountRepository.insert(account);
        return account.getId();
    }

    @Override
    public void update(Long id, AccountSaveDTO dto) {
        Account existing = accountRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Account account = AccountConvert.toEntity(dto);
        account.setId(id);
        // 密码为空则不修改
        if (StringUtils.hasText(dto.getPassword())) {
            account.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            account.setPassword(null);
        }
        accountRepository.updateById(account);
    }

    @Override
    public void delete(Long id) {
        Account existing = accountRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        accountRepository.deleteById(id);
    }
}
