package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.MemberService;
import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
import com.adrug.erp.svc.user.info.feign.MemberFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员管理服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberFeign memberFeign;

    @Override
    public Long createMember(MemberSaveDTO dto) {
        return memberFeign.create(dto).getData();
    }

    @Override
    public void updateMember(Long id, MemberSaveDTO dto) {
        memberFeign.update(id, dto);
    }

    @Override
    public void deleteMember(Long id) {
        memberFeign.delete(id);
    }
}
