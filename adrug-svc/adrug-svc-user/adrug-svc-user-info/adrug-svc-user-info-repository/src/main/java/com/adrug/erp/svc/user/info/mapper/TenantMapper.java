package com.adrug.erp.svc.user.info.mapper;

import com.adrug.erp.svc.user.info.entity.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力；
 * 复杂 SQL 预留 XML 方式实现（见 {@code resources/mapper/TenantMapper.xml}）。
 */
@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {
}
