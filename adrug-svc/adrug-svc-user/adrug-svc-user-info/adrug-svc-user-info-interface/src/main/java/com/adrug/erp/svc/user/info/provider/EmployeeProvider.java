package com.adrug.erp.svc.user.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.EmployeeSaveDTO;
import com.adrug.erp.svc.user.info.query.EmployeeQuery;
import com.adrug.erp.svc.user.info.vo.EmployeeVO;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 service 模块 {@code EmployeeServiceImpl}。
 */
public interface EmployeeProvider {

    /**
     * 分页查询员工。
     */
    PageResult<EmployeeVO> page(EmployeeQuery query);

    /**
     * 按 ID 查询员工。
     */
    EmployeeVO getById(Long id);

    /**
     * 按 ID 集合批量查询员工。
     */
    List<EmployeeVO> getByIds(Collection<Long> ids);

    /**
     * 新增员工。
     *
     * @return 新员工主键
     */
    Long create(EmployeeSaveDTO dto);

    /**
     * 变更员工。
     */
    void update(Long id, EmployeeSaveDTO dto);

    /**
     * 删除员工（逻辑删除）。
     */
    void delete(Long id);

    /**
     * 物理删除员工（预留）。
     * <p>
     * 真正从数据库移除记录，仅用于特殊清理场景；业务常规删除请使用逻辑删除。
     */
    void deletePhysically(Long id);
}
