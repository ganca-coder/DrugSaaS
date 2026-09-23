package com.adrug.erp.app.oper.bus.controller;

import com.adrug.erp.app.oper.bus.service.BusInfoQueryService;
import com.adrug.erp.app.oper.bus.service.BusInfoService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.Result;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;
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
 * 基础信息 Controller（应用层，读写分离）：药品 / 供应商。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("app/oper/bus/info")
public class BusInfoAppController {

    @Autowired
    private BusInfoService busInfoService;
    @Autowired
    private BusInfoQueryService busInfoQueryService;

    // ===== 药品 =====

    @PostMapping("/getDrugPage")
    public Result<PageResult<DrugVO>> getDrugPage(@RequestBody DrugQuery query) {
        return Result.success(busInfoQueryService.getDrugPage(query));
    }

    @GetMapping("/drug/{id}")
    public Result<DrugVO> getDrugById(@PathVariable("id") Long id) {
        return Result.success(busInfoQueryService.getDrugById(id));
    }

    @PostMapping("/drug")
    public Result<Long> createDrug(@RequestBody DrugSaveDTO dto) {
        return Result.success(busInfoService.createDrug(dto));
    }

    @PutMapping("/drug/{id}")
    public Result<Void> updateDrug(@PathVariable("id") Long id, @RequestBody DrugSaveDTO dto) {
        busInfoService.updateDrug(id, dto);
        return Result.success();
    }

    @DeleteMapping("/drug/{id}")
    public Result<Void> deleteDrug(@PathVariable("id") Long id) {
        busInfoService.deleteDrug(id);
        return Result.success();
    }

    // ===== 供应商 =====

    @PostMapping("/getSupplierPage")
    public Result<PageResult<SupplierVO>> getSupplierPage(@RequestBody SupplierQuery query) {
        return Result.success(busInfoQueryService.getSupplierPage(query));
    }

    @GetMapping("/supplier/{id}")
    public Result<SupplierVO> getSupplierById(@PathVariable("id") Long id) {
        return Result.success(busInfoQueryService.getSupplierById(id));
    }

    @PostMapping("/supplier")
    public Result<Long> createSupplier(@RequestBody SupplierSaveDTO dto) {
        return Result.success(busInfoService.createSupplier(dto));
    }

    @PutMapping("/supplier/{id}")
    public Result<Void> updateSupplier(@PathVariable("id") Long id, @RequestBody SupplierSaveDTO dto) {
        busInfoService.updateSupplier(id, dto);
        return Result.success();
    }

    @DeleteMapping("/supplier/{id}")
    public Result<Void> deleteSupplier(@PathVariable("id") Long id) {
        busInfoService.deleteSupplier(id);
        return Result.success();
    }

    // ===== 仓库 =====

    @PostMapping("/getWarehousePage")
    public Result<PageResult<WarehouseVO>> getWarehousePage(@RequestBody WarehouseQuery query) {
        return Result.success(busInfoQueryService.getWarehousePage(query));
    }

    @GetMapping("/warehouse/{id}")
    public Result<WarehouseVO> getWarehouseById(@PathVariable("id") Long id) {
        return Result.success(busInfoQueryService.getWarehouseById(id));
    }

    @PostMapping("/warehouse")
    public Result<Long> createWarehouse(@RequestBody WarehouseSaveDTO dto) {
        return Result.success(busInfoService.createWarehouse(dto));
    }

    @PutMapping("/warehouse/{id}")
    public Result<Void> updateWarehouse(@PathVariable("id") Long id, @RequestBody WarehouseSaveDTO dto) {
        busInfoService.updateWarehouse(id, dto);
        return Result.success();
    }

    @DeleteMapping("/warehouse/{id}")
    public Result<Void> deleteWarehouse(@PathVariable("id") Long id) {
        busInfoService.deleteWarehouse(id);
        return Result.success();
    }
}
