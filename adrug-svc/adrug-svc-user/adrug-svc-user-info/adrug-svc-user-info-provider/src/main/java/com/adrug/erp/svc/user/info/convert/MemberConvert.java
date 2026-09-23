package com.adrug.erp.svc.user.info.convert;

import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
import com.adrug.erp.svc.user.info.entity.Member;
import com.adrug.erp.svc.user.info.vo.MemberVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员信息实体 <-> 传输对象 转换器。
 * <p>
 * 枚举字段（性别/状态）在实体与传输对象中统一使用 code（Integer），无需额外转换。
 */
public final class MemberConvert {

    private MemberConvert() {
    }

    /**
     * 实体转视图对象。
     */
    public static MemberVO toVO(Member member) {
        MemberVO vo = new MemberVO();
        BeanUtils.copyProperties(member, vo);
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<MemberVO> toVOList(List<Member> members) {
        return members.stream()
                .map(MemberConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体。
     */
    public static Member toEntity(MemberSaveDTO dto) {
        Member member = new Member();
        BeanUtils.copyProperties(dto, member);
        return member;
    }
}
