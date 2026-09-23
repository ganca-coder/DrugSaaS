package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusInfoQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.feign.DrugFeign;
import com.adrug.erp.svc.bus.info.feign.SupplierFeign;
import com.adrug.erp.svc.bus.info.feign.WarehouseFeign;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础信息查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusInfoQueryServiceImpl implements BusInfoQueryService {

    @Autowired
    private DrugFeign drugFeign;
    @Autowired
    private SupplierFeign supplierFeign;
    @Autowired
    private WarehouseFeign warehouseFeign;

    @Override
    public PageResult<DrugVO> getDrugPage(DrugQuery query) {
        return drugFeign.page(query).getData();
    }

    @Override
    public DrugVO getDrugById(Long id) {
        return drugFeign.getById(id).getData();
    }

    @Override
    public PageResult<SupplierVO> getSupplierPage(SupplierQuery query) {
        return supplierFeign.page(query).getData();
    }

    @Override
    public SupplierVO getSupplierById(Long id) {
        return supplierFeign.getById(id).getData();
    }

    @Override
    public PageResult<WarehouseVO> getWarehousePage(WarehouseQuery query) {
        return warehouseFeign.page(query).getData();
    }

    @Override
    public WarehouseVO getWarehouseById(Long id) {
        return warehouseFeign.getById(id).getData();
    }
}
