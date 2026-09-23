package com.adrug.erp.svc.bus.pms.controller;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.provider.ReceivingOrderProvider;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收货单 Controller。
 * <p>
 * URL 路径与 {@code ReceivingOrderFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/svc/bus/pms/receiving-order")
public class ReceivingOrderController {

    @Autowired
    private ReceivingOrderProvider receivingOrderProvider;

    @PostMapping("/page")
    public Result<PageResult<ReceivingOrderVO>> page(@RequestBody ReceivingOrderQuery query) {
        return Result.success(receivingOrderProvider.page(query));
    }

    @GetMapping("/transfer/{purchaseOrderId}")
    public Result<List<ReceivingOrderItemVO>> loadItemsFromPurchaseOrder(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
        return Result.success(receivingOrderProvider.loadItemsFromPurchaseOrder(purchaseOrderId));
    }

    @GetMapping("/{id}")
    public Result<ReceivingOrderVO> getById(@PathVariable("id") Long id) {
        return Result.success(receivingOrderProvider.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody ReceivingOrderSaveDTO dto) {
        return Result.success(receivingOrderProvider.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody ReceivingOrderSaveDTO dto) {
        receivingOrderProvider.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        receivingOrderProvider.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/post")
    public Result<Void> post(@PathVariable("id") Long id) {
        receivingOrderProvider.post(id);
        return Result.success();
    }
}
