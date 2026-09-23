package com.adrug.erp.app.term.pos.service.impl;

import com.adrug.erp.app.term.pos.service.PosQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.feign.DrugFeign;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import com.adrug.erp.svc.user.info.feign.MemberFeign;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * POS收银台查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class PosQueryServiceImpl implements PosQueryService {

    @Autowired
    private DrugFeign drugFeign;
    @Autowired
    private MemberFeign memberFeign;

    @Override
    public PageResult<DrugVO> searchDrug(DrugQuery query) {
        return drugFeign.page(query).getData();
    }

    @Override
    public PageResult<MemberVO> searchMember(MemberQuery query) {
        return memberFeign.page(query).getData();
    }
}
