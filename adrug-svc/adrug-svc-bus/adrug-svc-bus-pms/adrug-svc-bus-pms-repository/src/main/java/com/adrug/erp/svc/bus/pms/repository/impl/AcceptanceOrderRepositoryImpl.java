package com.adrug.erp.svc.bus.pms.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrder;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.mapper.AcceptanceOrderMapper;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.AcceptanceOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 验收单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class AcceptanceOrderRepositoryImpl implements AcceptanceOrderRepository {

    private final AcceptanceOrderMapper acceptanceOrderMapper;

    public AcceptanceOrderRepositoryImpl(AcceptanceOrderMapper acceptanceOrderMapper) {
        this.acceptanceOrderMapper = acceptanceOrderMapper;
    }

    @Override
    public PageResult<AcceptanceOrder> selectPage(AcceptanceOrderQuery query) {
        Page<AcceptanceOrder> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<AcceptanceOrder> wrapper = new LambdaQueryWrapper<>();
        // 关键字：验收单号 或 供应商名称 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(AcceptanceOrder::getOrderNo, query.getKeyword())
                        .or()
                        .like(AcceptanceOrder::getSupplierName, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, AcceptanceOrder::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, AcceptanceOrder::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, AcceptanceOrder::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, AcceptanceOrder::getOrgId, AcceptanceOrder::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getOrderNo()), AcceptanceOrder::getOrderNo, query.getOrderNo());
        wrapper.eq(query.getSupplierId() != null, AcceptanceOrder::getSupplierId, query.getSupplierId());
        wrapper.eq(StringUtils.hasText(query.getSupplierName()), AcceptanceOrder::getSupplierName, query.getSupplierName());
        wrapper.eq(query.getWarehouseId() != null, AcceptanceOrder::getWarehouseId, query.getWarehouseId());
        wrapper.eq(query.getSourceOrderId() != null, AcceptanceOrder::getSourceOrderId, query.getSourceOrderId());
        BillStatusEnum status = BaseEnum.ofCode(BillStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, AcceptanceOrder::getStatus, status);
        wrapper.eq(query.getCreateUserId() != null, AcceptanceOrder::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, AcceptanceOrder::getUpdateUserId, query.getUpdateUserId());

        // 日期/时间范围查询
        wrapper.ge(query.getAcceptDateStart() != null, AcceptanceOrder::getAcceptDate, query.getAcceptDateStart());
        wrapper.le(query.getAcceptDateEnd() != null, AcceptanceOrder::getAcceptDate, query.getAcceptDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, AcceptanceOrder::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, AcceptanceOrder::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, AcceptanceOrder::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, AcceptanceOrder::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(AcceptanceOrder::getCreateTime);

        Page<AcceptanceOrder> result = acceptanceOrderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public AcceptanceOrder selectById(Long id) {
        return acceptanceOrderMapper.selectById(id);
    }

    @Override
    public void insert(AcceptanceOrder acceptanceOrder) {
        acceptanceOrderMapper.insert(acceptanceOrder);
    }

    @Override
    public void updateById(AcceptanceOrder acceptanceOrder) {
        acceptanceOrderMapper.updateById(acceptanceOrder);
    }

    @Override
    public void deleteById(Long id) {
        acceptanceOrderMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return acceptanceOrderMapper.deletePhysically(id);
    }
}
