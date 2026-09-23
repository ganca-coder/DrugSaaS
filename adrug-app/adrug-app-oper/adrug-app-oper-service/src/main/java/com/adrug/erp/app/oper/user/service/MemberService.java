package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;

/**
 * 会员管理服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface MemberService {

    Long createMember(MemberSaveDTO dto);

    void updateMember(Long id, MemberSaveDTO dto);

    void deleteMember(Long id);
}
