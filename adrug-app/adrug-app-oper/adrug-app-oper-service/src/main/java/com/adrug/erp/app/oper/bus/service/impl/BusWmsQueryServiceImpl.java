package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusWmsQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.wms.feign.InventoryFeign;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仓库/库存查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusWmsQueryServiceImpl implements BusWmsQueryService {

    @Autowired
    private InventoryFeign inventoryFeign;

    @Override
    public PageResult<InventoryVO> getInventoryPage(InventoryQuery query) {
        return inventoryFeign.page(query).getData();
    }
}
