package com.adrug.erp.app.oper.bus.controller;

import com.adrug.erp.app.oper.bus.service.BusOmsQueryService;
import com.adrug.erp.app.oper.bus.service.BusOmsService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;
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
 * 订单管理 Controller（应用层，读写分离）：POS 零售单。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/bus/oms")
public class BusOmsAppController {

    @Autowired
    private BusOmsService busOmsService;
    @Autowired
    private BusOmsQueryService busOmsQueryService;

    @PostMapping("/getPosRetailOrderPage")
    public Result<PageResult<PosRetailOrderVO>> getPosRetailOrderPage(@RequestBody PosRetailOrderQuery query) {
        return Result.success(busOmsQueryService.getPosRetailOrderPage(query));
    }

    @GetMapping("/pos-retail-order/{id}")
    public Result<PosRetailOrderVO> getPosRetailOrderById(@PathVariable("id") Long id) {
        return Result.success(busOmsQueryService.getPosRetailOrderById(id));
    }

    @PostMapping("/pos-retail-order")
    public Result<Long> createPosRetailOrder(@RequestBody PosRetailOrderSaveDTO dto) {
        return Result.success(busOmsService.createPosRetailOrder(dto));
    }

    @PutMapping("/pos-retail-order/{id}")
    public Result<Void> updatePosRetailOrder(@PathVariable("id") Long id, @RequestBody PosRetailOrderSaveDTO dto) {
        busOmsService.updatePosRetailOrder(id, dto);
        return Result.success();
    }

    @DeleteMapping("/pos-retail-order/{id}")
    public Result<Void> deletePosRetailOrder(@PathVariable("id") Long id) {
        busOmsService.deletePosRetailOrder(id);
        return Result.success();
    }
}
