package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;

/**
 * 基础信息服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusInfoService {

    /**
     * 新增药品。
     *
     * @return 新药品主键
     */
    Long createDrug(DrugSaveDTO dto);

    /**
     * 变更药品。
     */
    void updateDrug(Long id, DrugSaveDTO dto);

    /**
     * 删除药品（逻辑删除）。
     */
    void deleteDrug(Long id);

    /**
     * 新增供应商。
     *
     * @return 新供应商主键
     */
    Long createSupplier(SupplierSaveDTO dto);

    /**
     * 变更供应商。
     */
    void updateSupplier(Long id, SupplierSaveDTO dto);

    /**
     * 删除供应商（逻辑删除）。
     */
    void deleteSupplier(Long id);

    /**
     * 新增仓库。
     *
     * @return 新仓库主键
     */
    Long createWarehouse(WarehouseSaveDTO dto);

    /**
     * 变更仓库。
     */
    void updateWarehouse(Long id, WarehouseSaveDTO dto);

    /**
     * 删除仓库（逻辑删除）。
     */
    void deleteWarehouse(Long id);
}
