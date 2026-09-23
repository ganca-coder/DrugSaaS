package com.adrug.erp.svc.bus.pms.mapper;

import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 验收单 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL（多表关联、动态条件等）预留 XML 方式实现，见
 * {@code resources/mapper/AcceptanceOrderMapper.xml}。
 */
@Mapper
public interface AcceptanceOrderMapper extends BaseMapper<AcceptanceOrder> {

    /**
     * 物理删除验收单（预留）。
     *
     * @param id 验收单主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
