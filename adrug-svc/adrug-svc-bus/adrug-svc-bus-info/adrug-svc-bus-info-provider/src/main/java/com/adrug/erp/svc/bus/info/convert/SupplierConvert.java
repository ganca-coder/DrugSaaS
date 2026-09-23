package com.adrug.erp.svc.bus.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.dto.SupplierCertificateDTO;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.entity.Supplier;
import com.adrug.erp.svc.bus.info.entity.SupplierCertificate;
import com.adrug.erp.svc.bus.info.enums.SupplierStatusEnum;
import com.adrug.erp.svc.bus.info.vo.SupplierCertificateVO;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供应商信息实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型。
 */
public final class SupplierConvert {

    private SupplierConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static SupplierVO toVO(Supplier supplier) {
        SupplierVO vo = new SupplierVO();
        BeanUtils.copyProperties(supplier, vo);
        if (supplier.getStatus() != null) {
            vo.setStatus(supplier.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<SupplierVO> toVOList(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(SupplierConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static Supplier toEntity(SupplierSaveDTO dto) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(dto, supplier);
        supplier.setStatus(BaseEnum.ofCode(SupplierStatusEnum.class, dto.getStatus()));
        return supplier;
    }

    /**
     * 证件实体转视图对象。
     */
    public static SupplierCertificateVO toCertificateVO(SupplierCertificate certificate) {
        SupplierCertificateVO vo = new SupplierCertificateVO();
        BeanUtils.copyProperties(certificate, vo);
        return vo;
    }

    /**
     * 证件实体列表转视图对象列表。
     */
    public static List<SupplierCertificateVO> toCertificateVOList(List<SupplierCertificate> certificates) {
        return certificates.stream()
                .map(SupplierConvert::toCertificateVO)
                .collect(Collectors.toList());
    }

    /**
     * 证件入参列表转实体列表，序号缺省按顺序从 1 开始编号。
     */
    public static List<SupplierCertificate> toCertificateEntityList(List<SupplierCertificateDTO> dtos) {
        List<SupplierCertificate> certificates = new ArrayList<>();
        if (dtos == null) {
            return certificates;
        }
        for (int i = 0; i < dtos.size(); i++) {
            SupplierCertificateDTO dto = dtos.get(i);
            SupplierCertificate certificate = new SupplierCertificate();
            BeanUtils.copyProperties(dto, certificate);
            if (certificate.getSeqNo() == null) {
                certificate.setSeqNo(i + 1);
            }
            certificates.add(certificate);
        }
        return certificates;
    }
}
