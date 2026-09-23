package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.entity.ReceivingOrder;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.enums.SourceTypeEnum;
import com.adrug.erp.svc.bus.pms.mapper.ReceivingOrderMapper;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.ReceivingOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 收货单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class ReceivingOrderRepositoryImpl implements ReceivingOrderRepository {

    private final ReceivingOrderMapper receivingOrderMapper;

    public ReceivingOrderRepositoryImpl(ReceivingOrderMapper receivingOrderMapper) {
        this.receivingOrderMapper = receivingOrderMapper;
    }

    @Override
    public PageResult<ReceivingOrder> selectPage(ReceivingOrderQuery query) {
        Page<ReceivingOrder> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<ReceivingOrder> wrapper = new LambdaQueryWrapper<>();
        // 关键字：收货单号 或 往来单位名称 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(ReceivingOrder::getOrderNo, query.getKeyword())
                        .or()
                        .like(ReceivingOrder::getSupplierName, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, ReceivingOrder::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, ReceivingOrder::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, ReceivingOrder::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, ReceivingOrder::getOrgId, ReceivingOrder::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getOrderNo()), ReceivingOrder::getOrderNo, query.getOrderNo());
        SourceTypeEnum sourceType = BaseEnum.ofCode(SourceTypeEnum.class, query.getSourceType());
        wrapper.eq(sourceType != null, ReceivingOrder::getSourceType, sourceType);
        wrapper.eq(query.getSourceOrderId() != null, ReceivingOrder::getSourceOrderId, query.getSourceOrderId());
        wrapper.eq(query.getSupplierId() != null, ReceivingOrder::getSupplierId, query.getSupplierId());
        wrapper.eq(StringUtils.hasText(query.getSupplierName()), ReceivingOrder::getSupplierName, query.getSupplierName());
        wrapper.eq(query.getWarehouseId() != null, ReceivingOrder::getWarehouseId, query.getWarehouseId());
        wrapper.eq(StringUtils.hasText(query.getArrivalNo()), ReceivingOrder::getArrivalNo, query.getArrivalNo());
        BillStatusEnum status = BaseEnum.ofCode(BillStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, ReceivingOrder::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, ReceivingOrder::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, ReceivingOrder::getUpdateUserId, query.getUpdateUserId());

        // 日期/时间范围查询
        wrapper.ge(query.getReceiveDateStart() != null, ReceivingOrder::getReceiveDate, query.getReceiveDateStart());
        wrapper.le(query.getReceiveDateEnd() != null, ReceivingOrder::getReceiveDate, query.getReceiveDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, ReceivingOrder::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, ReceivingOrder::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, ReceivingOrder::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, ReceivingOrder::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(ReceivingOrder::getCreateTime);

        Page<ReceivingOrder> result = receivingOrderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public ReceivingOrder selectById(Long id) {
        return receivingOrderMapper.selectById(id);
    }

    @Override
    public void insert(ReceivingOrder receivingOrder) {
        receivingOrderMapper.insert(receivingOrder);
    }

    @Override
    public void updateById(ReceivingOrder receivingOrder) {
        receivingOrderMapper.updateById(receivingOrder);
    }

    @Override
    public void deleteById(Long id) {
        receivingOrderMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return receivingOrderMapper.deletePhysically(id);
    }
}
