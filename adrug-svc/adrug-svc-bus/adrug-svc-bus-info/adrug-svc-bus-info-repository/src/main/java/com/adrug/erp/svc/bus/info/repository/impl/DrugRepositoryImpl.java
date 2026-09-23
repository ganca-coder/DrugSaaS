package com.adrug.erp.svc.bus.info.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.entity.Drug;
import com.adrug.erp.svc.bus.info.enums.DrugStatusEnum;
import com.adrug.erp.svc.bus.info.enums.DrugTypeEnum;
import com.adrug.erp.common.db.helper.DataScopeHelper;
import com.adrug.erp.svc.bus.info.mapper.DrugMapper;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.repository.DrugRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 药品信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class DrugRepositoryImpl implements DrugRepository {

    private final DrugMapper drugMapper;

    public DrugRepositoryImpl(DrugMapper drugMapper) {
        this.drugMapper = drugMapper;
    }

    @Override
    public PageResult<Drug> selectPage(DrugQuery query) {
        Page<Drug> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Drug> wrapper = new LambdaQueryWrapper<>();
        // 关键字：药品名称 或 药品编码 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Drug::getDrugName, query.getKeyword())
                        .or()
                        .like(Drug::getDrugCode, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, Drug::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Drug::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Drug::getOrgId, query.getOrgId());
        // 数据权限：按当前用户数据范围过滤
        DataScopeHelper.apply(wrapper, Drug::getOrgId, Drug::getCreateUserId);
        wrapper.eq(StringUtils.hasText(query.getDrugCode()), Drug::getDrugCode, query.getDrugCode());
        wrapper.eq(StringUtils.hasText(query.getDrugName()), Drug::getDrugName, query.getDrugName());
        wrapper.eq(StringUtils.hasText(query.getGenericName()), Drug::getGenericName, query.getGenericName());
        wrapper.eq(StringUtils.hasText(query.getSpec()), Drug::getSpec, query.getSpec());
        wrapper.eq(StringUtils.hasText(query.getDosageForm()), Drug::getDosageForm, query.getDosageForm());
        wrapper.eq(StringUtils.hasText(query.getUnit()), Drug::getUnit, query.getUnit());
        wrapper.eq(StringUtils.hasText(query.getManufacturer()), Drug::getManufacturer, query.getManufacturer());
        wrapper.eq(StringUtils.hasText(query.getApprovalNo()), Drug::getApprovalNo, query.getApprovalNo());
        DrugTypeEnum drugType = BaseEnum.ofCode(DrugTypeEnum.class, query.getDrugType());
        wrapper.eq(drugType != null, Drug::getDrugType, drugType);
        DrugStatusEnum status = BaseEnum.ofCode(DrugStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Drug::getStatus, status);
        wrapper.eq(StringUtils.hasText(query.getRemark()), Drug::getRemark, query.getRemark());
        wrapper.eq(query.getCreateUserId() != null, Drug::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Drug::getUpdateUserId, query.getUpdateUserId());

        // 日期/时间范围查询
        wrapper.ge(query.getCreateTimeStart() != null, Drug::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Drug::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Drug::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Drug::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Drug::getCreateTime);

        Page<Drug> result = drugMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Drug selectById(Long id) {
        return drugMapper.selectById(id);
    }

    @Override
    public List<Drug> selectByIds(Collection<Long> ids) {
        return drugMapper.selectBatchIds(ids);
    }

    @Override
    public void insert(Drug drug) {
        drugMapper.insert(drug);
    }

    @Override
    public void updateById(Drug drug) {
        drugMapper.updateById(drug);
    }

    @Override
    public void deleteById(Long id) {
        drugMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return drugMapper.deletePhysically(id);
    }
}
