package com.adrug.erp.svc.user.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.AccountSaveDTO;
import com.adrug.erp.svc.user.info.query.AccountQuery;
import com.adrug.erp.svc.user.info.vo.AccountCredentialVO;
import com.adrug.erp.svc.user.info.vo.AccountVO;

/**
 * 账号服务接口（依赖倒置契约）。
 */
public interface AccountProvider {

    /**
     * 分页查询账号。
     */
    PageResult<AccountVO> page(AccountQuery query);

    /**
     * 按 ID 查询账号。
     */
    AccountVO getById(Long id);

    /**
     * 按登录账号查询账号凭证（含密码密文，仅供登录编排）。
     */
    AccountCredentialVO getByUsername(String username);

    /**
     * 按员工 ID 查询账号。
     */
    AccountVO getByEmployeeId(Long employeeId);

    /**
     * 新增账号。
     *
     * @return 新账号主键
     */
    Long create(AccountSaveDTO dto);

    /**
     * 变更账号。
     */
    void update(Long id, AccountSaveDTO dto);

    /**
     * 删除账号（逻辑删除）。
     */
    void delete(Long id);
}
