package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrder;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.mapper.PurchaseInboundOrderMapper;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.PurchaseInboundOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 采购入库单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class PurchaseInboundOrderRepositoryImpl implements PurchaseInboundOrderRepository {

    private final PurchaseInboundOrderMapper purchaseInboundOrderMapper;

    public PurchaseInboundOrderRepositoryImpl(PurchaseInboundOrderMapper purchaseInboundOrderMapper) {
        this.purchaseInboundOrderMapper = purchaseInboundOrderMapper;
    }

    @Override
    public PageResult<PurchaseInboundOrder> selectPage(PurchaseInboundOrderQuery query) {
        Page<PurchaseInboundOrder> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<PurchaseInboundOrder> wrapper = new LambdaQueryWrapper<>();
        // 关键字：入库单号 或 供应商名称 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(PurchaseInboundOrder::getOrderNo, query.getKeyword())
                        .or()
                        .like(PurchaseInboundOrder::getSupplierName, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, PurchaseInboundOrder::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, PurchaseInboundOrder::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, PurchaseInboundOrder::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, PurchaseInboundOrder::getOrgId, PurchaseInboundOrder::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getOrderNo()), PurchaseInboundOrder::getOrderNo, query.getOrderNo());
        wrapper.eq(query.getSupplierId() != null, PurchaseInboundOrder::getSupplierId, query.getSupplierId());
        wrapper.eq(StringUtils.hasText(query.getSupplierName()), PurchaseInboundOrder::getSupplierName, query.getSupplierName());
        wrapper.eq(query.getWarehouseId() != null, PurchaseInboundOrder::getWarehouseId, query.getWarehouseId());
        wrapper.eq(query.getSourceOrderId() != null, PurchaseInboundOrder::getSourceOrderId, query.getSourceOrderId());
        BillStatusEnum status = BaseEnum.ofCode(BillStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, PurchaseInboundOrder::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, PurchaseInboundOrder::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, PurchaseInboundOrder::getUpdateUserId, query.getUpdateUserId());

        // 日期/时间范围查询
        wrapper.ge(query.getInboundDateStart() != null, PurchaseInboundOrder::getInboundDate, query.getInboundDateStart());
        wrapper.le(query.getInboundDateEnd() != null, PurchaseInboundOrder::getInboundDate, query.getInboundDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, PurchaseInboundOrder::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, PurchaseInboundOrder::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, PurchaseInboundOrder::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, PurchaseInboundOrder::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(PurchaseInboundOrder::getCreateTime);

        Page<PurchaseInboundOrder> result = purchaseInboundOrderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public PurchaseInboundOrder selectById(Long id) {
        return purchaseInboundOrderMapper.selectById(id);
    }

    @Override
    public void insert(PurchaseInboundOrder purchaseInboundOrder) {
        purchaseInboundOrderMapper.insert(purchaseInboundOrder);
    }

    @Override
    public void updateById(PurchaseInboundOrder purchaseInboundOrder) {
        purchaseInboundOrderMapper.updateById(purchaseInboundOrder);
    }

    @Override
    public void deleteById(Long id) {
        purchaseInboundOrderMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return purchaseInboundOrderMapper.deletePhysically(id);
    }
}
