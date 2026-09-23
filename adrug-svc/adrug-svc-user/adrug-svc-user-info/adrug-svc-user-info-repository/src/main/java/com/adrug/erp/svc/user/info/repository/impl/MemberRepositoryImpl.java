package com.adrug.erp.svc.user.info.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.entity.Member;
import com.adrug.erp.svc.user.info.mapper.MemberMapper;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.repository.MemberRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * 会员信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;

    public MemberRepositoryImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public PageResult<Member> selectPage(MemberQuery query) {
        Page<Member> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        // 关键字：姓名 / 手机号 / 会员卡号 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Member::getName, query.getKeyword())
                        .or()
                        .like(Member::getPhone, query.getKeyword())
                        .or()
                        .like(Member::getMemberNo, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, Member::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Member::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Member::getOrgId, query.getOrgId());
        wrapper.eq(StringUtils.hasText(query.getMemberNo()), Member::getMemberNo, query.getMemberNo());
        wrapper.eq(StringUtils.hasText(query.getName()), Member::getName, query.getName());
        wrapper.eq(StringUtils.hasText(query.getPhone()), Member::getPhone, query.getPhone());
        wrapper.eq(query.getGender() != null, Member::getGender, query.getGender());
        wrapper.eq(StringUtils.hasText(query.getIdCardNo()), Member::getIdCardNo, query.getIdCardNo());
        wrapper.eq(StringUtils.hasText(query.getLevel()), Member::getLevel, query.getLevel());
        wrapper.eq(query.getStatus() != null, Member::getStatus, query.getStatus());
        wrapper.eq(query.getCreateUserId() != null, Member::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Member::getUpdateUserId, query.getUpdateUserId());
        // 日期/时间范围查询
        wrapper.ge(query.getBirthDateStart() != null, Member::getBirthDate, query.getBirthDateStart());
        wrapper.le(query.getBirthDateEnd() != null, Member::getBirthDate, query.getBirthDateEnd());
        wrapper.ge(query.getRegisterTimeStart() != null, Member::getRegisterTime, query.getRegisterTimeStart());
        wrapper.le(query.getRegisterTimeEnd() != null, Member::getRegisterTime, query.getRegisterTimeEnd());
        wrapper.ge(query.getCreateTimeStart() != null, Member::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Member::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Member::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Member::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Member::getCreateTime);

        Page<Member> result = memberMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Member selectById(Long id) {
        return memberMapper.selectById(id);
    }

    @Override
    public void insert(Member member) {
        memberMapper.insert(member);
    }

    @Override
    public void updateById(Member member) {
        memberMapper.updateById(member);
    }

    @Override
    public void deleteById(Long id) {
        memberMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return memberMapper.deletePhysically(id);
    }
}
