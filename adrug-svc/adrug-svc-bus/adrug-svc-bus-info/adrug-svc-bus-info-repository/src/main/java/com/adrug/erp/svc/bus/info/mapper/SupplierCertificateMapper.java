package com.adrug.erp.svc.bus.info.mapper;

import com.adrug.erp.svc.bus.info.entity.SupplierCertificate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 供应商证件信息 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL 预留 XML 方式实现，见 {@code resources/mapper/SupplierCertificateMapper.xml}。
 */
@Mapper
public interface SupplierCertificateMapper extends BaseMapper<SupplierCertificate> {

    /**
     * 物理删除证件信息（预留）。
     *
     * @param id 证件信息主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
