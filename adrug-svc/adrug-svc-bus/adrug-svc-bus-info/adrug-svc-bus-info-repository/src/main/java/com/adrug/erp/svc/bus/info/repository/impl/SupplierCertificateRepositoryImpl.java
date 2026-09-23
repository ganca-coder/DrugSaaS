package com.adrug.erp.svc.bus.info.repository.impl;

import com.adrug.erp.svc.bus.info.entity.SupplierCertificate;
import com.adrug.erp.svc.bus.info.mapper.SupplierCertificateMapper;
import com.adrug.erp.svc.bus.info.repository.SupplierCertificateRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商证件信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class SupplierCertificateRepositoryImpl implements SupplierCertificateRepository {

    private final SupplierCertificateMapper supplierCertificateMapper;

    public SupplierCertificateRepositoryImpl(SupplierCertificateMapper supplierCertificateMapper) {
        this.supplierCertificateMapper = supplierCertificateMapper;
    }

    @Override
    public List<SupplierCertificate> selectBySupplierId(Long supplierId) {
        return supplierCertificateMapper.selectList(new LambdaQueryWrapper<SupplierCertificate>()
                .eq(SupplierCertificate::getSupplierId, supplierId)
                .orderByAsc(SupplierCertificate::getId));
    }

    @Override
    public void insertBatch(List<SupplierCertificate> certificates) {
        if (certificates == null) {
            return;
        }
        certificates.forEach(supplierCertificateMapper::insert);
    }

    @Override
    public void deleteBySupplierId(Long supplierId) {
        supplierCertificateMapper.delete(new LambdaQueryWrapper<SupplierCertificate>()
                .eq(SupplierCertificate::getSupplierId, supplierId));
    }

    @Override
    public int deletePhysically(Long id) {
        return supplierCertificateMapper.deletePhysically(id);
    }
}
