package com.adrug.erp.svc.user.info.mapper;

import com.adrug.erp.svc.user.info.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 员工信息 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL（多表关联、动态条件等）预留 XML 方式实现，见
 * {@code resources/mapper/EmployeeMapper.xml}；未来在此接口声明自定义方法，
 * 并在 XML 中补充对应 SQL 节点。
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 物理删除员工（预留）。
     * <p>
     * 通过手写 DELETE 语句绕过 {@code @TableLogic} 逻辑删除，真正从数据库移除记录。
     * 仅用于特殊清理场景，业务常规删除请继续使用 {@link BaseMapper#deleteById}（逻辑删除）。
     *
     * @param id 员工主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
