package com.adrug.erp.svc.bus.pms.mapper;

import com.adrug.erp.svc.bus.pms.entity.RejectionOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 拒收单 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL 预留 XML 方式实现，见 {@code resources/mapper/RejectionOrderMapper.xml}。
 */
@Mapper
public interface RejectionOrderMapper extends BaseMapper<RejectionOrder> {

    /**
     * 物理删除拒收单（预留）。
     *
     * @param id 拒收单主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
