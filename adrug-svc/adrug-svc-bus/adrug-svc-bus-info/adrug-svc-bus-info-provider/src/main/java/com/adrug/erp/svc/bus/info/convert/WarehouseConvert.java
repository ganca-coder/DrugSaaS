package com.adrug.erp.svc.bus.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.entity.Warehouse;
import com.adrug.erp.svc.bus.info.enums.WarehouseStatusEnum;
import com.adrug.erp.svc.bus.info.enums.WarehouseTypeEnum;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 仓库信息实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型。
 */
public final class WarehouseConvert {

    private WarehouseConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static WarehouseVO toVO(Warehouse warehouse) {
        WarehouseVO vo = new WarehouseVO();
        BeanUtils.copyProperties(warehouse, vo);
        if (warehouse.getWarehouseType() != null) {
            vo.setWarehouseType(warehouse.getWarehouseType().getCode());
        }
        if (warehouse.getStatus() != null) {
            vo.setStatus(warehouse.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<WarehouseVO> toVOList(List<Warehouse> warehouses) {
        return warehouses.stream()
                .map(WarehouseConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static Warehouse toEntity(WarehouseSaveDTO dto) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(dto, warehouse);
        warehouse.setWarehouseType(BaseEnum.ofCode(WarehouseTypeEnum.class, dto.getWarehouseType()));
        warehouse.setStatus(BaseEnum.ofCode(WarehouseStatusEnum.class, dto.getStatus()));
        return warehouse;
    }
}
