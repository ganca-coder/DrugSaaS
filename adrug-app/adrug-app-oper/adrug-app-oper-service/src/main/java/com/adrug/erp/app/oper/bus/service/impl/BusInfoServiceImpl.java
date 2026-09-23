package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusInfoService;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.feign.DrugFeign;
import com.adrug.erp.svc.bus.info.feign.SupplierFeign;
import com.adrug.erp.svc.bus.info.feign.WarehouseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础信息服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusInfoServiceImpl implements BusInfoService {

    @Autowired
    private DrugFeign drugFeign;
    @Autowired
    private SupplierFeign supplierFeign;
    @Autowired
    private WarehouseFeign warehouseFeign;

    @Override
    public Long createDrug(DrugSaveDTO dto) {
        return drugFeign.create(dto).getData();
    }

    @Override
    public void updateDrug(Long id, DrugSaveDTO dto) {
        drugFeign.update(id, dto);
    }

    @Override
    public void deleteDrug(Long id) {
        drugFeign.delete(id);
    }

    @Override
    public Long createSupplier(SupplierSaveDTO dto) {
        return supplierFeign.create(dto).getData();
    }

    @Override
    public void updateSupplier(Long id, SupplierSaveDTO dto) {
        supplierFeign.update(id, dto);
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierFeign.delete(id);
    }

    @Override
    public Long createWarehouse(WarehouseSaveDTO dto) {
        return warehouseFeign.create(dto).getData();
    }

    @Override
    public void updateWarehouse(Long id, WarehouseSaveDTO dto) {
        warehouseFeign.update(id, dto);
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseFeign.delete(id);
    }
}
