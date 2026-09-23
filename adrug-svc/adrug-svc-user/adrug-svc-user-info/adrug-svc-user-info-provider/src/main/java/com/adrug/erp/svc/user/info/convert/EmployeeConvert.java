package com.adrug.erp.svc.user.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.entity.Employee;
import com.adrug.erp.svc.user.info.enums.EmployeeStatusEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.PharmacistFlagEnum;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工信息实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link BaseEnum#ofCode} 在 code 与枚举之间切换。
 */
public final class EmployeeConvert {

    private EmployeeConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static EmployeeVO toVO(Employee employee) {
        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);
        if (employee.getGender() != null) {
            vo.setGender(employee.getGender().getCode());
        }
        if (employee.getStatus() != null) {
            vo.setStatus(employee.getStatus().getCode());
        }
        if (employee.getPharmacist() != null) {
            vo.setPharmacist(employee.getPharmacist().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<EmployeeVO> toVOList(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static Employee toEntity(EmployeeSaveDTO dto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        employee.setGender(BaseEnum.ofCode(GenderEnum.class, dto.getGender()));
        employee.setStatus(BaseEnum.ofCode(EmployeeStatusEnum.class, dto.getStatus()));
        employee.setPharmacist(BaseEnum.ofCode(PharmacistFlagEnum.class, dto.getPharmacist()));
        return employee;
    }
}
