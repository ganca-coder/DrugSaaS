package com.adrug.erp.app.oper.bus.controller;

import com.adrug.erp.app.oper.bus.service.BusWmsQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存管理 Controller（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/bus/wms")
public class BusWmsAppController {

    @Autowired
    private BusWmsQueryService busWmsQueryService;

    @PostMapping("/getInventoryPage")
    public Result<PageResult<InventoryVO>> getInventoryPage(@RequestBody InventoryQuery query) {
        return Result.success(busWmsQueryService.getInventoryPage(query));
    }
}
