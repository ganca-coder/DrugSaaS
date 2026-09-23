package com.adrug.erp.svc.bus.wms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.wms.dto.InventoryIncreaseDTO;
import com.adrug.erp.svc.bus.wms.provider.InventoryProvider;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 库存 Controller。
 * <p>
 * URL 路径与 {@code InventoryFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/wms/inventory")
public class InventoryController {

    @Autowired
    private InventoryProvider inventoryProvider;

    @PostMapping("/page")
    public Result<PageResult<InventoryVO>> page(@RequestBody InventoryQuery query) {
        return Result.success(inventoryProvider.page(query));
    }

    @PostMapping("/increase")
    public Result<Void> increase(@RequestBody List<InventoryIncreaseDTO> items) {
        inventoryProvider.increase(items);
        return Result.success();
    }
}
