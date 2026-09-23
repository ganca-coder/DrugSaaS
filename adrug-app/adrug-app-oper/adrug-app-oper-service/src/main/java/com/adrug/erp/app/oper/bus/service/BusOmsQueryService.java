package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;

/**
 * 订单管理查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusOmsQueryService {

    PageResult<PosRetailOrderVO> getPosRetailOrderPage(PosRetailOrderQuery query);

    PosRetailOrderVO getPosRetailOrderById(Long id);
}
