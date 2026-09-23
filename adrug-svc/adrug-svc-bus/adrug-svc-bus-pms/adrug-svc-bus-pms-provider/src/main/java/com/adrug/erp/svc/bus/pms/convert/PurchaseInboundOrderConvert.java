package com.adrug.erp.svc.bus.pms.convert;

import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrder;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrderItem;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 采购入库单实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link com.adrug.erp.common.enums.BaseEnum#ofCode} 在 code 与枚举之间切换。
 */
public final class PurchaseInboundOrderConvert {

    private PurchaseInboundOrderConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static PurchaseInboundOrderVO toVO(PurchaseInboundOrder order) {
        PurchaseInboundOrderVO vo = new PurchaseInboundOrderVO();
        BeanUtils.copyProperties(order, vo);
        if (order.getStatus() != null) {
            vo.setStatus(order.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<PurchaseInboundOrderVO> toVOList(List<PurchaseInboundOrder> orders) {
        return orders.stream()
                .map(PurchaseInboundOrderConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static PurchaseInboundOrder toEntity(PurchaseInboundOrderSaveDTO dto) {
        PurchaseInboundOrder order = new PurchaseInboundOrder();
        BeanUtils.copyProperties(dto, order);
        return order;
    }

    /**
     * 明细实体转明细视图对象。
     */
    public static PurchaseInboundOrderItemVO toItemVO(PurchaseInboundOrderItem item) {
        PurchaseInboundOrderItemVO vo = new PurchaseInboundOrderItemVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }

    /**
     * 明细实体列表转明细视图对象列表。
     */
    public static List<PurchaseInboundOrderItemVO> toItemVOList(List<PurchaseInboundOrderItem> items) {
        return items.stream()
                .map(PurchaseInboundOrderConvert::toItemVO)
                .collect(Collectors.toList());
    }

    /**
     * 明细入参转明细实体。
     */
    public static PurchaseInboundOrderItem toItemEntity(PurchaseInboundOrderItemDTO dto) {
        PurchaseInboundOrderItem item = new PurchaseInboundOrderItem();
        BeanUtils.copyProperties(dto, item);
        return item;
    }
}
