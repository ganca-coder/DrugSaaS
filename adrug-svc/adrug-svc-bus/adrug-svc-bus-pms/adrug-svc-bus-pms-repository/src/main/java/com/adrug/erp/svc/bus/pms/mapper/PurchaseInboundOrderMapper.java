package com.adrug.erp.svc.bus.pms.mapper;

import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 采购入库单 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL（多表关联、动态条件等）预留 XML 方式实现，见
 * {@code resources/mapper/PurchaseInboundOrderMapper.xml}。
 */
@Mapper
public interface PurchaseInboundOrderMapper extends BaseMapper<PurchaseInboundOrder> {

    /**
     * 物理删除采购入库单（预留）。
     *
     * @param id 采购入库单主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
