package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusOmsService;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.feign.PosRetailOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单管理服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusOmsServiceImpl implements BusOmsService {

    @Autowired
    private PosRetailOrderFeign posRetailOrderFeign;

    @Override
    public Long createPosRetailOrder(PosRetailOrderSaveDTO dto) {
        return posRetailOrderFeign.create(dto).getData();
    }

    @Override
    public void updatePosRetailOrder(Long id, PosRetailOrderSaveDTO dto) {
        posRetailOrderFeign.update(id, dto);
    }

    @Override
    public void deletePosRetailOrder(Long id) {
        posRetailOrderFeign.delete(id);
    }
}
