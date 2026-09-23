package com.adrug.erp.svc.bus.info.repository;

import com.adrug.erp.svc.bus.info.entity.SupplierCertificate;
import com.adrug.erp.svc.bus.info.repository.impl.SupplierCertificateRepositoryImpl;

import java.util.List;

/**
 * 供应商证件信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link SupplierCertificateRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface SupplierCertificateRepository {

    /**
     * 按供应商 ID 查询证件信息。
     */
    List<SupplierCertificate> selectBySupplierId(Long supplierId);

    /**
     * 批量新增证件信息。
     */
    void insertBatch(List<SupplierCertificate> certificates);

    /**
     * 按供应商 ID 删除证件信息（逻辑删除）。
     */
    void deleteBySupplierId(Long supplierId);

    /**
     * 物理删除证件信息（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
