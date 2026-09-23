package com.adrug.erp.svc.bus.oms.convert;

import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderItemDTO;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.entity.PosRetailOrder;
import com.adrug.erp.svc.bus.oms.entity.PosRetailOrderItem;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderItemVO;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * POS零售单实体 <-> 传输对象 转换器。
 * <p>
 * 字段均为普通类型（无枚举），直接属性拷贝即可。
 */
public final class PosRetailOrderConvert {

    private PosRetailOrderConvert() {
    }

    /**
     * 实体转视图对象。
     */
    public static PosRetailOrderVO toVO(PosRetailOrder order) {
        PosRetailOrderVO vo = new PosRetailOrderVO();
        BeanUtils.copyProperties(order, vo);
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<PosRetailOrderVO> toVOList(List<PosRetailOrder> orders) {
        return orders.stream()
                .map(PosRetailOrderConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体。
     */
    public static PosRetailOrder toEntity(PosRetailOrderSaveDTO dto) {
        PosRetailOrder order = new PosRetailOrder();
        BeanUtils.copyProperties(dto, order);
        return order;
    }

    /**
     * 明细实体转明细视图对象。
     */
    public static PosRetailOrderItemVO toItemVO(PosRetailOrderItem item) {
        PosRetailOrderItemVO vo = new PosRetailOrderItemVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }

    /**
     * 明细实体列表转明细视图对象列表。
     */
    public static List<PosRetailOrderItemVO> toItemVOList(List<PosRetailOrderItem> items) {
        return items.stream()
                .map(PosRetailOrderConvert::toItemVO)
                .collect(Collectors.toList());
    }

    /**
     * 明细入参转明细实体。
     */
    public static PosRetailOrderItem toItemEntity(PosRetailOrderItemDTO dto) {
        PosRetailOrderItem item = new PosRetailOrderItem();
        BeanUtils.copyProperties(dto, item);
        return item;
    }
}
