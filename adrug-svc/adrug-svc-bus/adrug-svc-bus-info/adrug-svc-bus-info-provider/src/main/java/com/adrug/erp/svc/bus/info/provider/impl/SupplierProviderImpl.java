package com.adrug.erp.svc.bus.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.info.convert.SupplierConvert;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.entity.Supplier;
import com.adrug.erp.svc.bus.info.entity.SupplierCertificate;
import com.adrug.erp.svc.bus.info.provider.SupplierProvider;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.repository.SupplierCertificateRepository;
import com.adrug.erp.svc.bus.info.repository.SupplierRepository;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商信息服务实现。
 */
@Service
public class SupplierProviderImpl implements SupplierProvider {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierCertificateRepository supplierCertificateRepository;

    @Override
    public PageResult<SupplierVO> page(SupplierQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Supplier> pageResult = supplierRepository.selectPage(query);
        List<SupplierVO> records = SupplierConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public SupplierVO getById(Long id) {
        Supplier supplier = supplierRepository.selectById(id);
        if (supplier == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        SupplierVO vo = SupplierConvert.toVO(supplier);
        vo.setCertificates(SupplierConvert.toCertificateVOList(supplierCertificateRepository.selectBySupplierId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(SupplierSaveDTO dto) {
        Supplier supplier = SupplierConvert.toEntity(dto);
        supplier.setId(null);
        supplierRepository.insert(supplier);
        // 保存证件信息
        List<SupplierCertificate> certificates = SupplierConvert.toCertificateEntityList(dto.getCertificates());
        for (SupplierCertificate certificate : certificates) {
            certificate.setSupplierId(supplier.getId());
        }
        supplierCertificateRepository.insertBatch(certificates);
        return supplier.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SupplierSaveDTO dto) {
        Supplier existing = supplierRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Supplier supplier = SupplierConvert.toEntity(dto);
        supplier.setId(id);
        supplierRepository.updateById(supplier);
        // 重写证件信息：先删后插
        supplierCertificateRepository.deleteBySupplierId(id);
        List<SupplierCertificate> certificates = SupplierConvert.toCertificateEntityList(dto.getCertificates());
        for (SupplierCertificate certificate : certificates) {
            certificate.setSupplierId(id);
        }
        supplierCertificateRepository.insertBatch(certificates);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Supplier existing = supplierRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        supplierRepository.deleteById(id);
        supplierCertificateRepository.deleteBySupplierId(id);
    }
}
