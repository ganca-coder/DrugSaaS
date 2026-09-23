package com.adrug.erp.svc.user.info.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.entity.Org;
import com.adrug.erp.svc.user.info.enums.OrgStatusEnum;
import com.adrug.erp.svc.user.info.enums.OrgTypeEnum;
import com.adrug.erp.svc.user.info.mapper.OrgMapper;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.repository.OrgRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机构数据访问实现。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class OrgRepositoryImpl implements OrgRepository {

    @Autowired
    private OrgMapper orgMapper;

    @Override
    public PageResult<Org> selectPage(OrgQuery query) {
        Page<Org> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Org> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Org::getOrgName, query.getKeyword())
                        .or()
                        .like(Org::getOrgCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Org::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Org::getTenantId, query.getTenantId());
        wrapper.eq(query.getParentId() != null, Org::getParentId, query.getParentId());
        wrapper.eq(StringUtils.hasText(query.getOrgCode()), Org::getOrgCode, query.getOrgCode());
        wrapper.eq(StringUtils.hasText(query.getOrgName()), Org::getOrgName, query.getOrgName());
        OrgTypeEnum orgType = BaseEnum.ofCode(OrgTypeEnum.class, query.getOrgType());
        wrapper.eq(orgType != null, Org::getOrgType, orgType);
        OrgStatusEnum status = BaseEnum.ofCode(OrgStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Org::getStatus, status);

        wrapper.orderByDesc(Org::getCreateTime);

        Page<Org> result = orgMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Org selectById(Long id) {
        return orgMapper.selectById(id);
    }

    @Override
    public List<Long> selectDescendantIds(Long orgId) {
        List<Long> result = new ArrayList<>();
        List<Long> current = new ArrayList<>();
        current.add(orgId);
        result.add(orgId);
        while (!current.isEmpty()) {
            LambdaQueryWrapper<Org> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Org::getParentId, current);
            wrapper.select(Org::getId);
            List<Long> children = orgMapper.selectList(wrapper).stream()
                    .map(Org::getId)
                    .collect(Collectors.toList());
            result.addAll(children);
            current = children;
        }
        return result;
    }

    @Override
    public void insert(Org org) {
        orgMapper.insert(org);
    }

    @Override
    public void updateById(Org org) {
        orgMapper.updateById(org);
    }

    @Override
    public void deleteById(Long id) {
        orgMapper.deleteById(id);
    }
}
