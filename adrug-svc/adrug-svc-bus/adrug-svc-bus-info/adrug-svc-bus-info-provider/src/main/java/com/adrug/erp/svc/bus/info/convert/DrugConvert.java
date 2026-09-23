package com.adrug.erp.svc.bus.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.entity.Drug;
import com.adrug.erp.svc.bus.info.enums.DrugStatusEnum;
import com.adrug.erp.svc.bus.info.enums.DrugTypeEnum;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 药品信息实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link BaseEnum#ofCode} 在 code 与枚举之间切换。
 */
public final class DrugConvert {

    private DrugConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static DrugVO toVO(Drug drug) {
        DrugVO vo = new DrugVO();
        BeanUtils.copyProperties(drug, vo);
        if (drug.getDrugType() != null) {
            vo.setDrugType(drug.getDrugType().getCode());
        }
        if (drug.getStatus() != null) {
            vo.setStatus(drug.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<DrugVO> toVOList(List<Drug> drugs) {
        return drugs.stream()
                .map(DrugConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static Drug toEntity(DrugSaveDTO dto) {
        Drug drug = new Drug();
        BeanUtils.copyProperties(dto, drug);
        drug.setDrugType(BaseEnum.ofCode(DrugTypeEnum.class, dto.getDrugType()));
        drug.setStatus(BaseEnum.ofCode(DrugStatusEnum.class, dto.getStatus()));
        return drug;
    }
}
