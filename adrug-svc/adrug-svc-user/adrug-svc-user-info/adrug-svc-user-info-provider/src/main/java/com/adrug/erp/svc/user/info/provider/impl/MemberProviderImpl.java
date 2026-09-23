package com.adrug.erp.svc.user.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.info.convert.MemberConvert;
import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
import com.adrug.erp.svc.user.info.entity.Member;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.repository.MemberRepository;
import com.adrug.erp.svc.user.info.provider.MemberProvider;
import com.adrug.erp.svc.user.info.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员信息服务实现。
 */
@Service
public class MemberProviderImpl implements MemberProvider {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public PageResult<MemberVO> page(MemberQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Member> pageResult = memberRepository.selectPage(query);
        List<MemberVO> records = MemberConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public MemberVO getById(Long id) {
        Member member = memberRepository.selectById(id);
        if (member == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return MemberConvert.toVO(member);
    }

    @Override
    public Long create(MemberSaveDTO dto) {
        Member member = MemberConvert.toEntity(dto);
        member.setId(null);
        memberRepository.insert(member);
        return member.getId();
    }

    @Override
    public void update(Long id, MemberSaveDTO dto) {
        Member existing = memberRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Member member = MemberConvert.toEntity(dto);
        member.setId(id);
        memberRepository.updateById(member);
    }

    @Override
    public void delete(Long id) {
        Member existing = memberRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // @TableLogic 逻辑删除
        memberRepository.deleteById(id);
    }

    @Override
    public void deletePhysically(Long id) {
        Member existing = memberRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        memberRepository.deletePhysically(id);
    }
}
