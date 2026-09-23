package com.adrug.erp.svc.bus.pms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.PurchaseInboundOrderProvider;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;
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
 * 采购入库单 Controller。
 * <p>
 * URL 路径与 {@code PurchaseInboundOrderFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/pms/purchase-inbound-order")
public class PurchaseInboundOrderController {

    @Autowired
    private PurchaseInboundOrderProvider purchaseInboundOrderProvider;

    @PostMapping("/page")
    public Result<PageResult<PurchaseInboundOrderVO>> page(@RequestBody PurchaseInboundOrderQuery query) {
        return Result.success(purchaseInboundOrderProvider.page(query));
    }

    @GetMapping("/{id}")
    public Result<PurchaseInboundOrderVO> getById(@PathVariable("id") Long id) {
        return Result.success(purchaseInboundOrderProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody PurchaseInboundOrderSaveDTO dto) {
        return Result.success(purchaseInboundOrderProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody PurchaseInboundOrderSaveDTO dto) {
        purchaseInboundOrderProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        purchaseInboundOrderProvider.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/post")
    public Result<Void> post(@PathVariable("id") Long id) {
        purchaseInboundOrderProvider.post(id);
        return Result.success();
    }
}
