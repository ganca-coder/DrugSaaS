package com.adrug.erp.svc.bus.wms.convert;

import com.adrug.erp.svc.bus.wms.dto.InventoryIncreaseDTO;
import com.adrug.erp.svc.bus.wms.entity.Inventory;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存实体 <-> 传输对象 转换器。
 */
public final class InventoryConvert {

    private InventoryConvert() {
    }

    /**
     * 实体转视图对象。
     */
    public static InventoryVO toVO(Inventory inventory) {
        InventoryVO vo = new InventoryVO();
        BeanUtils.copyProperties(inventory, vo);
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<InventoryVO> toVOList(List<Inventory> inventories) {
        return inventories.stream()
                .map(InventoryConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 增加库存入参转实体。
     */
    public static Inventory toEntity(InventoryIncreaseDTO dto) {
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(dto, inventory);
        return inventory;
    }
}
