package com.adrug.erp.svc.bus.info.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.entity.Supplier;
import com.adrug.erp.svc.bus.info.enums.SupplierStatusEnum;
import com.adrug.erp.svc.bus.info.mapper.SupplierMapper;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.repository.SupplierRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 供应商信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private final SupplierMapper supplierMapper;

    public SupplierRepositoryImpl(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    @Override
    public PageResult<Supplier> selectPage(SupplierQuery query) {
        Page<Supplier> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();
        // 关键字：供应商名称 或 供应商编码 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Supplier::getSupplierName, query.getKeyword())
                        .or()
                        .like(Supplier::getSupplierCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Supplier::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Supplier::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Supplier::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, Supplier::getOrgId, Supplier::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getSupplierCode()), Supplier::getSupplierCode, query.getSupplierCode());
        wrapper.eq(StringUtils.hasText(query.getSupplierName()), Supplier::getSupplierName, query.getSupplierName());
        SupplierStatusEnum status = BaseEnum.ofCode(SupplierStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Supplier::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, Supplier::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Supplier::getUpdateUserId, query.getUpdateUserId());

        wrapper.ge(query.getCreateTimeStart() != null, Supplier::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Supplier::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Supplier::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Supplier::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Supplier::getCreateTime);

        Page<Supplier> result = supplierMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Supplier selectById(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public void insert(Supplier supplier) {
        supplierMapper.insert(supplier);
    }

    @Override
    public void updateById(Supplier supplier) {
        supplierMapper.updateById(supplier);
    }

    @Override
    public void deleteById(Long id) {
        supplierMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return supplierMapper.deletePhysically(id);
    }
}
