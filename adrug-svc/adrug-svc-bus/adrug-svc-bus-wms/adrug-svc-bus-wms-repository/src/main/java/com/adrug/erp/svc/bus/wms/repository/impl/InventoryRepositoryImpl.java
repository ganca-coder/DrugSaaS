package com.adrug.erp.svc.bus.wms.repository.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.wms.entity.Inventory;
import com.adrug.erp.svc.bus.wms.mapper.InventoryMapper;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.repository.InventoryRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 库存数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryMapper inventoryMapper;

    public InventoryRepositoryImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public PageResult<Inventory> selectPage(InventoryQuery query) {
        Page<Inventory> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        // 关键字：药品名称 或 药品编码 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Inventory::getDrugName, query.getKeyword())
                        .or()
                        .like(Inventory::getDrugCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Inventory::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Inventory::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Inventory::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, Inventory::getOrgId, Inventory::getCreateUserId);
        wrapper.eq(query.getDrugId() != null, Inventory::getDrugId, query.getDrugId());
        wrapper.eq(StringUtils.hasText(query.getDrugCode()), Inventory::getDrugCode, query.getDrugCode());
        wrapper.eq(query.getWarehouseId() != null, Inventory::getWarehouseId, query.getWarehouseId());
        wrapper.eq(StringUtils.hasText(query.getBatchNo()), Inventory::getBatchNo, query.getBatchNo());
        wrapper.eq(query.getCreateUserId() != null, Inventory::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Inventory::getUpdateUserId, query.getUpdateUserId());

        wrapper.ge(query.getCreateTimeStart() != null, Inventory::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Inventory::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Inventory::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Inventory::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Inventory::getCreateTime);

        Page<Inventory> result = inventoryMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Inventory selectByKey(Long drugId, Long warehouseId, String batchNo) {
        return inventoryMapper.selectOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getTenantId, TenantContextHolder.get())
                .eq(Inventory::getDrugId, drugId)
                .eq(Inventory::getWarehouseId, warehouseId)
                .eq(Inventory::getBatchNo, batchNo == null ? "" : batchNo)
                .last("limit 1"));
    }

    @Override
    public void insert(Inventory inventory) {
        inventoryMapper.insert(inventory);
    }

    @Override
    public void updateById(Inventory inventory) {
        inventoryMapper.updateById(inventory);
    }

    @Override
    public int deletePhysically(Long id) {
        return inventoryMapper.deletePhysically(id);
    }
}
