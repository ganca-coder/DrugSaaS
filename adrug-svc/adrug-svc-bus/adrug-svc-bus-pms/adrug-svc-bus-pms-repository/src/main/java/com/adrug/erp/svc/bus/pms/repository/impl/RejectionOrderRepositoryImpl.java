package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.entity.RejectionOrder;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.mapper.RejectionOrderMapper;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.RejectionOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 拒收单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class RejectionOrderRepositoryImpl implements RejectionOrderRepository {

    private final RejectionOrderMapper rejectionOrderMapper;

    public RejectionOrderRepositoryImpl(RejectionOrderMapper rejectionOrderMapper) {
        this.rejectionOrderMapper = rejectionOrderMapper;
    }

    @Override
    public PageResult<RejectionOrder> selectPage(RejectionOrderQuery query) {
        Page<RejectionOrder> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<RejectionOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(RejectionOrder::getOrderNo, query.getKeyword())
                        .or()
                        .like(RejectionOrder::getSupplierName, query.getKeyword()));

        wrapper.eq(query.getId() != null, RejectionOrder::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, RejectionOrder::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, RejectionOrder::getOrgId, query.getOrgId());
        DataScopeHelper.apply(wrapper, RejectionOrder::getOrgId, RejectionOrder::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getOrderNo()), RejectionOrder::getOrderNo, query.getOrderNo());
        wrapper.eq(query.getSupplierId() != null, RejectionOrder::getSupplierId, query.getSupplierId());
        wrapper.eq(StringUtils.hasText(query.getSupplierName()), RejectionOrder::getSupplierName, query.getSupplierName());
        wrapper.eq(query.getWarehouseId() != null, RejectionOrder::getWarehouseId, query.getWarehouseId());
        wrapper.eq(StringUtils.hasText(query.getArrivalNo()), RejectionOrder::getArrivalNo, query.getArrivalNo());
        BillStatusEnum status = BaseEnum.ofCode(BillStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, RejectionOrder::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, RejectionOrder::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, RejectionOrder::getUpdateUserId, query.getUpdateUserId());

        wrapper.ge(query.getRejectDateStart() != null, RejectionOrder::getRejectDate, query.getRejectDateStart());
        wrapper.le(query.getRejectDateEnd() != null, RejectionOrder::getRejectDate, query.getRejectDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, RejectionOrder::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, RejectionOrder::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, RejectionOrder::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, RejectionOrder::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(RejectionOrder::getCreateTime);

        Page<RejectionOrder> result = rejectionOrderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public RejectionOrder selectById(Long id) {
        return rejectionOrderMapper.selectById(id);
    }

    @Override
    public void insert(RejectionOrder rejectionOrder) {
        rejectionOrderMapper.insert(rejectionOrder);
    }

    @Override
    public void updateById(RejectionOrder rejectionOrder) {
        rejectionOrderMapper.updateById(rejectionOrder);
    }

    @Override
    public void deleteById(Long id) {
        rejectionOrderMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return rejectionOrderMapper.deletePhysically(id);
    }
}
