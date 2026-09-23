package com.adrug.erp.app.term.pos.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;

/**
 * POS收银台查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface PosQueryService {

    /**
     * 药品搜索。
     */
    PageResult<DrugVO> searchDrug(DrugQuery query);

    /**
     * 会员搜索。
     */
    PageResult<MemberVO> searchMember(MemberQuery query);
}
