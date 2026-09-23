package com.adrug.erp.svc.bus.info.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.entity.Warehouse;
import com.adrug.erp.svc.bus.info.enums.WarehouseStatusEnum;
import com.adrug.erp.svc.bus.info.enums.WarehouseTypeEnum;
import com.adrug.erp.svc.bus.info.mapper.WarehouseMapper;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.repository.WarehouseRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 仓库信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class WarehouseRepositoryImpl implements WarehouseRepository {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public PageResult<Warehouse> selectPage(WarehouseQuery query) {
        Page<Warehouse> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        // 关键字：仓库名称 或 仓库编码 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Warehouse::getWarehouseName, query.getKeyword())
                        .or()
                        .like(Warehouse::getWarehouseCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Warehouse::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Warehouse::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Warehouse::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, Warehouse::getOrgId, Warehouse::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getWarehouseCode()), Warehouse::getWarehouseCode, query.getWarehouseCode());
        wrapper.eq(StringUtils.hasText(query.getWarehouseName()), Warehouse::getWarehouseName, query.getWarehouseName());
        WarehouseTypeEnum warehouseType = BaseEnum.ofCode(WarehouseTypeEnum.class, query.getWarehouseType());
        wrapper.eq(warehouseType != null, Warehouse::getWarehouseType, warehouseType);
        WarehouseStatusEnum status = BaseEnum.ofCode(WarehouseStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Warehouse::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, Warehouse::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Warehouse::getUpdateUserId, query.getUpdateUserId());

        wrapper.ge(query.getCreateTimeStart() != null, Warehouse::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Warehouse::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Warehouse::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Warehouse::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Warehouse::getCreateTime);

        Page<Warehouse> result = warehouseMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Warehouse selectById(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public void insert(Warehouse warehouse) {
        warehouseMapper.insert(warehouse);
    }

    @Override
    public void updateById(Warehouse warehouse) {
        warehouseMapper.updateById(warehouse);
    }

    @Override
    public void deleteById(Long id) {
        warehouseMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return warehouseMapper.deletePhysically(id);
    }
}
