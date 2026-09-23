package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrder;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.mapper.PurchaseOrderMapper;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.PurchaseOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 采购订单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final PurchaseOrderMapper purchaseOrderMapper;

    public PurchaseOrderRepositoryImpl(PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    @Override
    public PageResult<PurchaseOrder> selectPage(PurchaseOrderQuery query) {
        Page<PurchaseOrder> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        // 关键字：采购订单号 或 供应商名称 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(PurchaseOrder::getOrderNo, query.getKeyword())
                        .or()
                        .like(PurchaseOrder::getSupplierName, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, PurchaseOrder::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, PurchaseOrder::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, PurchaseOrder::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, PurchaseOrder::getOrgId, PurchaseOrder::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getOrderNo()), PurchaseOrder::getOrderNo, query.getOrderNo());
        wrapper.eq(query.getSupplierId() != null, PurchaseOrder::getSupplierId, query.getSupplierId());
        wrapper.eq(StringUtils.hasText(query.getSupplierName()), PurchaseOrder::getSupplierName, query.getSupplierName());
        BillStatusEnum status = BaseEnum.ofCode(BillStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, PurchaseOrder::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, PurchaseOrder::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, PurchaseOrder::getUpdateUserId, query.getUpdateUserId());

        // 日期/时间范围查询
        wrapper.ge(query.getOrderDateStart() != null, PurchaseOrder::getOrderDate, query.getOrderDateStart());
        wrapper.le(query.getOrderDateEnd() != null, PurchaseOrder::getOrderDate, query.getOrderDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, PurchaseOrder::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, PurchaseOrder::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, PurchaseOrder::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, PurchaseOrder::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(PurchaseOrder::getCreateTime);

        Page<PurchaseOrder> result = purchaseOrderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public PurchaseOrder selectById(Long id) {
        return purchaseOrderMapper.selectById(id);
    }

    @Override
    public void insert(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.insert(purchaseOrder);
    }

    @Override
    public void updateById(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.updateById(purchaseOrder);
    }

    @Override
    public void deleteById(Long id) {
        purchaseOrderMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return purchaseOrderMapper.deletePhysically(id);
    }
}
