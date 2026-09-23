package com.adrug.erp.app.oper.user.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;

/**
 * 会员管理查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface MemberQueryService {

    PageResult<MemberVO> getMemberPage(MemberQuery query);

    MemberVO getMemberById(Long id);
}
