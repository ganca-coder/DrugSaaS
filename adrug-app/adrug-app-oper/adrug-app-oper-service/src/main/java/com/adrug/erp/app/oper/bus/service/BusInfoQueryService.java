package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;

/**
 * 基础信息查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusInfoQueryService {

    /**
     * 分页查询药品。
     */
    PageResult<DrugVO> getDrugPage(DrugQuery query);

    /**
     * 按 ID 查询药品。
     */
    DrugVO getDrugById(Long id);

    /**
     * 分页查询供应商。
     */
    PageResult<SupplierVO> getSupplierPage(SupplierQuery query);

    /**
     * 按 ID 查询供应商。
     */
    SupplierVO getSupplierById(Long id);

    /**
     * 分页查询仓库。
     */
    PageResult<WarehouseVO> getWarehousePage(WarehouseQuery query);

    /**
     * 按 ID 查询仓库。
     */
    WarehouseVO getWarehouseById(Long id);
}
