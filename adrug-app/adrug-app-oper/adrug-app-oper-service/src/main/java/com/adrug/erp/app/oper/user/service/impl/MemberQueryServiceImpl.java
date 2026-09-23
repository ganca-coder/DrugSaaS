package com.adrug.erp.app.oper.user.service.impl;

import com.adrug.erp.app.oper.user.service.MemberQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.feign.MemberFeign;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员管理查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    @Autowired
    private MemberFeign memberFeign;

    @Override
    public PageResult<MemberVO> getMemberPage(MemberQuery query) {
        return memberFeign.page(query).getData();
    }

    @Override
    public MemberVO getMemberById(Long id) {
        return memberFeign.getById(id).getData();
    }
}
