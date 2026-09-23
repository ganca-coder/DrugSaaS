package com.adrug.erp.svc.bus.oms.mapper;

import com.adrug.erp.svc.bus.oms.entity.PosRetailOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * POS零售单 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL 预留 XML 方式实现，见 {@code resources/mapper/PosRetailOrderMapper.xml}。
 */
@Mapper
public interface PosRetailOrderMapper extends BaseMapper<PosRetailOrder> {

    /**
     * 物理删除 POS 零售单（预留）。
     *
     * @param id 主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
