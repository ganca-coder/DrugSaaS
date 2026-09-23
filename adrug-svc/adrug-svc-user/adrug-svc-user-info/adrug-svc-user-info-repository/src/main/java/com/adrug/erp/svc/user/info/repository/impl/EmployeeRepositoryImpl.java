package com.adrug.erp.svc.user.info.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.entity.Employee;
import com.adrug.erp.svc.user.info.enums.EmployeeStatusEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.PharmacistFlagEnum;
import com.adrug.erp.svc.user.info.mapper.EmployeeMapper;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.repository.EmployeeRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeRepositoryImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public PageResult<Employee> selectPage(EmployeeQuery query) {
        Page<Employee> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        // 关键字：姓名 或 工号 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Employee::getName, query.getKeyword())
                        .or()
                        .like(Employee::getEmpNo, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, Employee::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Employee::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Employee::getOrgId, query.getOrgId());

        wrapper.eq(StringUtils.hasText(query.getEmpNo()), Employee::getEmpNo, query.getEmpNo());
        wrapper.eq(StringUtils.hasText(query.getName()), Employee::getName, query.getName());
        GenderEnum gender = BaseEnum.ofCode(GenderEnum.class, query.getGender());
        wrapper.eq(gender != null, Employee::getGender, gender);
        wrapper.eq(query.getBirthDate() != null, Employee::getBirthDate, query.getBirthDate());
        wrapper.eq(StringUtils.hasText(query.getIdCardNo()), Employee::getIdCardNo, query.getIdCardNo());
        wrapper.eq(StringUtils.hasText(query.getPhone()), Employee::getPhone, query.getPhone());
        wrapper.eq(StringUtils.hasText(query.getEmail()), Employee::getEmail, query.getEmail());
        wrapper.eq(query.getPositionId() != null, Employee::getPositionId, query.getPositionId());
        wrapper.eq(query.getEntryDate() != null, Employee::getEntryDate, query.getEntryDate());
        wrapper.eq(query.getResignDate() != null, Employee::getResignDate, query.getResignDate());
        EmployeeStatusEnum status = BaseEnum.ofCode(EmployeeStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Employee::getStatus, status);
        PharmacistFlagEnum pharmacist = BaseEnum.ofCode(PharmacistFlagEnum.class, query.getPharmacist());
        wrapper.eq(pharmacist != null, Employee::getPharmacist, pharmacist);
        wrapper.eq(StringUtils.hasText(query.getRemark()), Employee::getRemark, query.getRemark());
        wrapper.eq(query.getCreateUserId() != null, Employee::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Employee::getUpdateUserId, query.getUpdateUserId());
        // 日期/时间范围查询
        wrapper.ge(query.getBirthDateStart() != null, Employee::getBirthDate, query.getBirthDateStart());
        wrapper.le(query.getBirthDateEnd() != null, Employee::getBirthDate, query.getBirthDateEnd());
        wrapper.ge(query.getEntryDateStart() != null, Employee::getEntryDate, query.getEntryDateStart());
        wrapper.le(query.getEntryDateEnd() != null, Employee::getEntryDate, query.getEntryDateEnd());
        wrapper.ge(query.getResignDateStart() != null, Employee::getResignDate, query.getResignDateStart());
        wrapper.le(query.getResignDateEnd() != null, Employee::getResignDate, query.getResignDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, Employee::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Employee::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Employee::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Employee::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Employee::getCreateTime);

        Page<Employee> result = employeeMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public List<Employee> selectByIds(Collection<Long> ids) {
        return employeeMapper.selectBatchIds(ids);
    }

    @Override
    public void insert(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void updateById(Employee employee) {
        employeeMapper.updateById(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return employeeMapper.deletePhysically(id);
    }
}
