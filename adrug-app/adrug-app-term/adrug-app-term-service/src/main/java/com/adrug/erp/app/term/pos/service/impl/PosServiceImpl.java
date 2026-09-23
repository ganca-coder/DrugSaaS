package com.adrug.erp.app.term.pos.service.impl;

import com.adrug.erp.app.term.pos.service.PosService;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.feign.PosRetailOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * POS收银台订单服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class PosServiceImpl implements PosService {

    @Autowired
    private PosRetailOrderFeign posRetailOrderFeign;

    @Override
    public Long createRetailOrder(PosRetailOrderSaveDTO dto) {
        return posRetailOrderFeign.create(dto).getData();
    }
}
