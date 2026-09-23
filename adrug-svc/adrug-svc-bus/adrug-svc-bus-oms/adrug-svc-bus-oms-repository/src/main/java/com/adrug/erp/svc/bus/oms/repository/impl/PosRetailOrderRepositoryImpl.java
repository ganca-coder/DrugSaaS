package com.adrug.erp.svc.bus.oms.repository.impl;

import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.oms.entity.PosRetailOrder;
import com.adrug.erp.svc.bus.oms.mapper.PosRetailOrderMapper;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.repository.PosRetailOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * POS零售单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class PosRetailOrderRepositoryImpl implements PosRetailOrderRepository {

    private final PosRetailOrderMapper posRetailOrderMapper;

    public PosRetailOrderRepositoryImpl(PosRetailOrderMapper posRetailOrderMapper) {
        this.posRetailOrderMapper = posRetailOrderMapper;
    }

    @Override
    public PageResult<PosRetailOrder> selectPage(PosRetailOrderQuery query) {
        Page<PosRetailOrder> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<PosRetailOrder> wrapper = new LambdaQueryWrapper<>();
        // 关键字：单据编号 / 会员姓名 / 会员手机号 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(PosRetailOrder::getOrderNo, query.getKeyword())
                        .or()
                        .like(PosRetailOrder::getMemberName, query.getKeyword())
                        .or()
                        .like(PosRetailOrder::getMemberPhone, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, PosRetailOrder::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, PosRetailOrder::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, PosRetailOrder::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, PosRetailOrder::getOrgId, PosRetailOrder::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getOrderNo()), PosRetailOrder::getOrderNo, query.getOrderNo());
        wrapper.eq(StringUtils.hasText(query.getBillType()), PosRetailOrder::getBillType, query.getBillType());
        wrapper.eq(StringUtils.hasText(query.getSaleType()), PosRetailOrder::getSaleType, query.getSaleType());
        wrapper.eq(StringUtils.hasText(query.getPosSerialNo()), PosRetailOrder::getPosSerialNo, query.getPosSerialNo());
        wrapper.eq(query.getWarehouseId() != null, PosRetailOrder::getWarehouseId, query.getWarehouseId());
        wrapper.eq(StringUtils.hasText(query.getPosNo()), PosRetailOrder::getPosNo, query.getPosNo());
        wrapper.eq(StringUtils.hasText(query.getShift()), PosRetailOrder::getShift, query.getShift());
        wrapper.eq(query.getPointsPrinted() != null, PosRetailOrder::getPointsPrinted, query.getPointsPrinted());
        wrapper.eq(StringUtils.hasText(query.getCashier()), PosRetailOrder::getCashier, query.getCashier());
        wrapper.eq(StringUtils.hasText(query.getSalesman()), PosRetailOrder::getSalesman, query.getSalesman());
        wrapper.eq(query.getMemberId() != null, PosRetailOrder::getMemberId, query.getMemberId());
        wrapper.eq(StringUtils.hasText(query.getMemberPhone()), PosRetailOrder::getMemberPhone, query.getMemberPhone());
        wrapper.eq(StringUtils.hasText(query.getBusinessPlatform()), PosRetailOrder::getBusinessPlatform, query.getBusinessPlatform());
        wrapper.eq(StringUtils.hasText(query.getRetailPriceType()), PosRetailOrder::getRetailPriceType, query.getRetailPriceType());
        wrapper.eq(query.getCreateUserId() != null, PosRetailOrder::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, PosRetailOrder::getUpdateUserId, query.getUpdateUserId());

        // 日期/时间范围查询
        wrapper.ge(query.getBillDateStart() != null, PosRetailOrder::getBillDate, query.getBillDateStart());
        wrapper.le(query.getBillDateEnd() != null, PosRetailOrder::getBillDate, query.getBillDateEnd());
        wrapper.ge(query.getBusinessTimeStart() != null, PosRetailOrder::getBusinessTime, query.getBusinessTimeStart());
        wrapper.le(query.getBusinessTimeEnd() != null, PosRetailOrder::getBusinessTime, query.getBusinessTimeEnd());
        wrapper.ge(query.getCreateTimeStart() != null, PosRetailOrder::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, PosRetailOrder::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, PosRetailOrder::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, PosRetailOrder::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(PosRetailOrder::getCreateTime);

        Page<PosRetailOrder> result = posRetailOrderMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public PosRetailOrder selectById(Long id) {
        return posRetailOrderMapper.selectById(id);
    }

    @Override
    public void insert(PosRetailOrder order) {
        posRetailOrderMapper.insert(order);
    }

    @Override
    public void updateById(PosRetailOrder order) {
        posRetailOrderMapper.updateById(order);
    }

    @Override
    public void deleteById(Long id) {
        posRetailOrderMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return posRetailOrderMapper.deletePhysically(id);
    }
}
