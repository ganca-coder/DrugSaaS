package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;

/**
 * 订单管理服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusOmsService {

    Long createPosRetailOrder(PosRetailOrderSaveDTO dto);

    void updatePosRetailOrder(Long id, PosRetailOrderSaveDTO dto);

    void deletePosRetailOrder(Long id);
}
