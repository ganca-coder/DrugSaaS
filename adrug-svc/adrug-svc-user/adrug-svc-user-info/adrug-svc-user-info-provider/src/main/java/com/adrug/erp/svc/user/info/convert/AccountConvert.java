package com.adrug.erp.svc.user.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.entity.Account;
import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import com.adrug.erp.svc.user.info.vo.AccountCredentialVO;
import com.adrug.erp.svc.user.info.vo.AccountVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账号实体 &lt;-&gt; 传输对象 转换器。
 * <p>
 * 密码不进入 VO；密码加密在 {@code AccountProviderImpl} 处理。
 */
public final class AccountConvert {

    private AccountConvert() {
    }

    public static AccountVO toVO(Account account) {
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(account, vo);
        if (account.getAccountType() != null) {
            vo.setAccountType(account.getAccountType().getCode());
        }
        if (account.getStatus() != null) {
            vo.setStatus(account.getStatus().getCode());
        }
        return vo;
    }

    public static List<AccountVO> toVOList(List<Account> accounts) {
        return accounts.stream().map(AccountConvert::toVO).collect(Collectors.toList());
    }

    public static Account toEntity(AccountSaveDTO dto) {
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);
        account.setAccountType(BaseEnum.ofCode(AccountTypeEnum.class, dto.getAccountType()));
        account.setStatus(BaseEnum.ofCode(AccountStatusEnum.class, dto.getStatus()));
        return account;
    }

    public static AccountCredentialVO toCredentialVO(Account account) {
        AccountCredentialVO vo = new AccountCredentialVO();
        BeanUtils.copyProperties(account, vo);
        if (account.getAccountType() != null) {
            vo.setAccountType(account.getAccountType().getCode());
        }
        if (account.getStatus() != null) {
            vo.setStatus(account.getStatus().getCode());
        }
        return vo;
    }
}
