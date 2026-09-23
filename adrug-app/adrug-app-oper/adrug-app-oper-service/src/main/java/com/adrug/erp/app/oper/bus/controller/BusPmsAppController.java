package com.adrug.erp.app.oper.bus.controller;

import com.adrug.erp.app.oper.bus.service.BusPmsQueryService;
import com.adrug.erp.app.oper.bus.service.BusPmsService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;
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
 * 采购管理 Controller（应用层，读写分离）：采购订单 / 收货单 / 验收单 / 采购入库单。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/bus/pms")
public class BusPmsAppController {

    @Autowired
    private BusPmsService busPmsService;
    @Autowired
    private BusPmsQueryService busPmsQueryService;

    // ===== 采购订单 =====

    @PostMapping("/getPurchaseOrderPage")
    public Result<PageResult<PurchaseOrderVO>> getPurchaseOrderPage(@RequestBody PurchaseOrderQuery query) {
        return Result.success(busPmsQueryService.getPurchaseOrderPage(query));
    }

    @GetMapping("/purchase-order/{id}")
    public Result<PurchaseOrderVO> getPurchaseOrderById(@PathVariable("id") Long id) {
        return Result.success(busPmsQueryService.getPurchaseOrderById(id));
    }

    @PostMapping("/purchase-order")
    public Result<Long> createPurchaseOrder(@RequestBody PurchaseOrderSaveDTO dto) {
        return Result.success(busPmsService.createPurchaseOrder(dto));
    }

    @PutMapping("/purchase-order/{id}")
    public Result<Void> updatePurchaseOrder(@PathVariable("id") Long id, @RequestBody PurchaseOrderSaveDTO dto) {
        busPmsService.updatePurchaseOrder(id, dto);
        return Result.success();
    }

    @DeleteMapping("/purchase-order/{id}")
    public Result<Void> deletePurchaseOrder(@PathVariable("id") Long id) {
        busPmsService.deletePurchaseOrder(id);
        return Result.success();
    }

    @PostMapping("/purchase-order/{id}/post")
    public Result<Void> postPurchaseOrder(@PathVariable("id") Long id) {
        busPmsService.postPurchaseOrder(id);
        return Result.success();
    }

    // ===== 收货单 =====

    @PostMapping("/getReceivingOrderPage")
    public Result<PageResult<ReceivingOrderVO>> getReceivingOrderPage(@RequestBody ReceivingOrderQuery query) {
        return Result.success(busPmsQueryService.getReceivingOrderPage(query));
    }

    @GetMapping("/receiving-order/transfer/{purchaseOrderId}")
    public Result<List<ReceivingOrderItemVO>> loadReceivingItemsFromPurchaseOrder(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
        return Result.success(busPmsQueryService.loadReceivingItemsFromPurchaseOrder(purchaseOrderId));
    }

    @GetMapping("/receiving-order/{id}")
    public Result<ReceivingOrderVO> getReceivingOrderById(@PathVariable("id") Long id) {
        return Result.success(busPmsQueryService.getReceivingOrderById(id));
    }

    @PostMapping("/receiving-order")
    public Result<Long> createReceivingOrder(@RequestBody ReceivingOrderSaveDTO dto) {
        return Result.success(busPmsService.createReceivingOrder(dto));
    }

    @PutMapping("/receiving-order/{id}")
    public Result<Void> updateReceivingOrder(@PathVariable("id") Long id, @RequestBody ReceivingOrderSaveDTO dto) {
        busPmsService.updateReceivingOrder(id, dto);
        return Result.success();
    }

    @DeleteMapping("/receiving-order/{id}")
    public Result<Void> deleteReceivingOrder(@PathVariable("id") Long id) {
        busPmsService.deleteReceivingOrder(id);
        return Result.success();
    }

    @PostMapping("/receiving-order/{id}/post")
    public Result<Void> postReceivingOrder(@PathVariable("id") Long id) {
        busPmsService.postReceivingOrder(id);
        return Result.success();
    }

    // ===== 验收单 =====

    @PostMapping("/getAcceptanceOrderPage")
    public Result<PageResult<AcceptanceOrderVO>> getAcceptanceOrderPage(@RequestBody AcceptanceOrderQuery query) {
        return Result.success(busPmsQueryService.getAcceptanceOrderPage(query));
    }

    @GetMapping("/acceptance-order/{id}")
    public Result<AcceptanceOrderVO> getAcceptanceOrderById(@PathVariable("id") Long id) {
        return Result.success(busPmsQueryService.getAcceptanceOrderById(id));
    }

    @PostMapping("/acceptance-order")
    public Result<Long> createAcceptanceOrder(@RequestBody AcceptanceOrderSaveDTO dto) {
        return Result.success(busPmsService.createAcceptanceOrder(dto));
    }

    @PutMapping("/acceptance-order/{id}")
    public Result<Void> updateAcceptanceOrder(@PathVariable("id") Long id, @RequestBody AcceptanceOrderSaveDTO dto) {
        busPmsService.updateAcceptanceOrder(id, dto);
        return Result.success();
    }

    @DeleteMapping("/acceptance-order/{id}")
    public Result<Void> deleteAcceptanceOrder(@PathVariable("id") Long id) {
        busPmsService.deleteAcceptanceOrder(id);
        return Result.success();
    }

    @PostMapping("/acceptance-order/{id}/accept")
    public Result<Void> acceptAcceptanceOrder(@PathVariable("id") Long id) {
        busPmsService.acceptAcceptanceOrder(id);
        return Result.success();
    }

    @PostMapping("/acceptance-order/{id}/post")
    public Result<Void> postAcceptanceOrder(@PathVariable("id") Long id) {
        busPmsService.postAcceptanceOrder(id);
        return Result.success();
    }

    // ===== 采购入库单 =====

    @PostMapping("/getPurchaseInboundOrderPage")
    public Result<PageResult<PurchaseInboundOrderVO>> getPurchaseInboundOrderPage(@RequestBody PurchaseInboundOrderQuery query) {
        return Result.success(busPmsQueryService.getPurchaseInboundOrderPage(query));
    }

    @GetMapping("/purchase-inbound-order/{id}")
    public Result<PurchaseInboundOrderVO> getPurchaseInboundOrderById(@PathVariable("id") Long id) {
        return Result.success(busPmsQueryService.getPurchaseInboundOrderById(id));
    }

    @PostMapping("/purchase-inbound-order")
    public Result<Long> createPurchaseInboundOrder(@RequestBody PurchaseInboundOrderSaveDTO dto) {
        return Result.success(busPmsService.createPurchaseInboundOrder(dto));
    }

    @PutMapping("/purchase-inbound-order/{id}")
    public Result<Void> updatePurchaseInboundOrder(@PathVariable("id") Long id, @RequestBody PurchaseInboundOrderSaveDTO dto) {
        busPmsService.updatePurchaseInboundOrder(id, dto);
        return Result.success();
    }

    @DeleteMapping("/purchase-inbound-order/{id}")
    public Result<Void> deletePurchaseInboundOrder(@PathVariable("id") Long id) {
        busPmsService.deletePurchaseInboundOrder(id);
        return Result.success();
    }

    @PostMapping("/purchase-inbound-order/{id}/post")
    public Result<Void> postPurchaseInboundOrder(@PathVariable("id") Long id) {
        busPmsService.postPurchaseInboundOrder(id);
        return Result.success();
    }

    // ===== 拒收单 =====

    @PostMapping("/getRejectionOrderPage")
    public Result<PageResult<RejectionOrderVO>> getRejectionOrderPage(@RequestBody RejectionOrderQuery query) {
        return Result.success(busPmsQueryService.getRejectionOrderPage(query));
    }

    @GetMapping("/rejection-order/{id}")
    public Result<RejectionOrderVO> getRejectionOrderById(@PathVariable("id") Long id) {
        return Result.success(busPmsQueryService.getRejectionOrderById(id));
    }

    @PostMapping("/rejection-order")
    public Result<Long> createRejectionOrder(@RequestBody RejectionOrderSaveDTO dto) {
        return Result.success(busPmsService.createRejectionOrder(dto));
    }

    @PutMapping("/rejection-order/{id}")
    public Result<Void> updateRejectionOrder(@PathVariable("id") Long id, @RequestBody RejectionOrderSaveDTO dto) {
        busPmsService.updateRejectionOrder(id, dto);
        return Result.success();
    }

    @DeleteMapping("/rejection-order/{id}")
    public Result<Void> deleteRejectionOrder(@PathVariable("id") Long id) {
        busPmsService.deleteRejectionOrder(id);
        return Result.success();
    }

    @PostMapping("/rejection-order/{id}/post")
    public Result<Void> postRejectionOrder(@PathVariable("id") Long id) {
        busPmsService.postRejectionOrder(id);
        return Result.success();
    }
}
