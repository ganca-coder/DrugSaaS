package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusOmsQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.oms.feign.PosRetailOrderFeign;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单管理查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusOmsQueryServiceImpl implements BusOmsQueryService {

    @Autowired
    private PosRetailOrderFeign posRetailOrderFeign;

    @Override
    public PageResult<PosRetailOrderVO> getPosRetailOrderPage(PosRetailOrderQuery query) {
        return posRetailOrderFeign.page(query).getData();
    }

    @Override
    public PosRetailOrderVO getPosRetailOrderById(Long id) {
        return posRetailOrderFeign.getById(id).getData();
    }
}
