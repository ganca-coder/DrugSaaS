package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;

/**
 * 仓库/库存查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusWmsQueryService {

    /**
     * 分页查询库存。
     */
    PageResult<InventoryVO> getInventoryPage(InventoryQuery query);
}
