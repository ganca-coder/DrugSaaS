package com.adrug.erp.app.term.pos.service;

import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;

/**
 * POS收银台订单服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface PosService {

    /**
     * 创建 POS 零售单。
     *
     * @return 新 POS 零售单主键
     */
    Long createRetailOrder(PosRetailOrderSaveDTO dto);
}
