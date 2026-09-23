package com.adrug.erp.svc.user.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.entity.Employee;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.repository.impl.EmployeeRepositoryImpl;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link EmployeeRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface EmployeeRepository {

    /**
     * 分页查询员工。
     */
    PageResult<Employee> selectPage(EmployeeQuery query);

    /**
     * 按 ID 查询员工。
     */
    Employee selectById(Long id);

    /**
     * 按 ID 集合批量查询员工。
     */
    List<Employee> selectByIds(Collection<Long> ids);

    /**
     * 新增员工（雪花主键回填到 {@code employee.id}）。
     */
    void insert(Employee employee);

    /**
     * 变更员工。
     */
    void updateById(Employee employee);

    /**
     * 删除员工（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除员工（预留）。
     * <p>
     * 真正从数据库移除记录，仅用于特殊清理场景；业务常规删除请使用逻辑删除。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
