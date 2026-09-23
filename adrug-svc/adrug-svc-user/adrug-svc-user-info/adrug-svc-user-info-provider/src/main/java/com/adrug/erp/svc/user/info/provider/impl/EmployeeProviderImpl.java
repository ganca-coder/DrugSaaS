package com.adrug.erp.svc.user.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.convert.EmployeeConvert;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.entity.Account;
import com.adrug.erp.svc.user.info.entity.Employee;
import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.repository.AccountRepository;
import com.adrug.erp.svc.user.info.repository.EmployeeRepository;
import com.adrug.erp.svc.user.info.provider.EmployeeProvider;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息服务实现。
 * <p>
 * 新建员工时自动创建对应账号：账号 = 员工手机号，默认密码见 {@link #DEFAULT_PASSWORD}。
 */
@Service
public class EmployeeProviderImpl implements EmployeeProvider {

    /** 自动创建账号的默认密码 */
    private static final String DEFAULT_PASSWORD = "123456";

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public PageResult<EmployeeVO> page(EmployeeQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Employee> pageResult = employeeRepository.selectPage(query);
        List<EmployeeVO> records = EmployeeConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public EmployeeVO getById(Long id) {
        Employee employee = employeeRepository.selectById(id);
        if (employee == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return EmployeeConvert.toVO(employee);
    }

    @Override
    public List<EmployeeVO> getByIds(Collection<Long> ids) {
        List<Employee> employees = employeeRepository.selectByIds(ids);
        return EmployeeConvert.toVOList(employees);
    }

    @Override
    public Long create(EmployeeSaveDTO dto) {
        Employee employee = EmployeeConvert.toEntity(dto);
        employee.setId(null);
        employeeRepository.insert(employee);

        // 自动创建账号：账号 = 员工手机号
        if (StringUtils.hasText(dto.getPhone())) {
            Account account = new Account();
            account.setTenantId(employee.getTenantId());
            account.setOrgId(employee.getOrgId());
            account.setUsername(dto.getPhone());
            account.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            account.setAccountType(AccountTypeEnum.EMPLOYEE);
            account.setEmployeeId(employee.getId());
            account.setStatus(AccountStatusEnum.ENABLED);
            accountRepository.insert(account);
        }
        return employee.getId();
    }

    @Override
    public void update(Long id, EmployeeSaveDTO dto) {
        Employee existing = employeeRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Employee employee = EmployeeConvert.toEntity(dto);
        employee.setId(id);
        employeeRepository.updateById(employee);
    }

    @Override
    public void delete(Long id) {
        Employee existing = employeeRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // @TableLogic 逻辑删除
        employeeRepository.deleteById(id);
        // 级联删除关联账号（逻辑删除）
        Account account = accountRepository.selectByEmployeeId(id);
        if (account != null) {
            accountRepository.deleteById(account.getId());
        }
    }

    @Override
    public void deletePhysically(Long id) {
        Employee existing = employeeRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        employeeRepository.deletePhysically(id);
    }
}
