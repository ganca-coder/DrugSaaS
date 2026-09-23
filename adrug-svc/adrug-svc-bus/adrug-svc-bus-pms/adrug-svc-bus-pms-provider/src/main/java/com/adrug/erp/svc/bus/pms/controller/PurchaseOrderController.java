package com.adrug.erp.svc.bus.pms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.PurchaseOrderProvider;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采购订单 Controller。
 * <p>
 * URL 路径与 {@code PurchaseOrderFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/pms/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderProvider purchaseOrderProvider;

    @PostMapping("/page")
    public Result<PageResult<PurchaseOrderVO>> page(@RequestBody PurchaseOrderQuery query) {
        return Result.success(purchaseOrderProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<PurchaseOrderVO> getById(@PathVariable("id") Long id) {
        return Result.success(purchaseOrderProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody PurchaseOrderSaveDTO dto) {
        return Result.success(purchaseOrderProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody PurchaseOrderSaveDTO dto) {
        purchaseOrderProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        purchaseOrderProvider.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/post")
    public Result<Void> post(@PathVariable("id") Long id) {
        purchaseOrderProvider.post(id);
        return Result.success();
    }
}
