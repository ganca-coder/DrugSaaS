package com.adrug.erp.svc.bus.pms.convert;

import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrder;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrderItem;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 采购订单实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link com.adrug.erp.common.enums.BaseEnum#ofCode} 在 code 与枚举之间切换。
 */
public final class PurchaseOrderConvert {

    private PurchaseOrderConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static PurchaseOrderVO toVO(PurchaseOrder order) {
        PurchaseOrderVO vo = new PurchaseOrderVO();
        BeanUtils.copyProperties(order, vo);
        if (order.getStatus() != null) {
            vo.setStatus(order.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<PurchaseOrderVO> toVOList(List<PurchaseOrder> orders) {
        return orders.stream()
                .map(PurchaseOrderConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static PurchaseOrder toEntity(PurchaseOrderSaveDTO dto) {
        PurchaseOrder order = new PurchaseOrder();
        BeanUtils.copyProperties(dto, order);
        return order;
    }

    /**
     * 明细实体转明细视图对象。
     */
    public static PurchaseOrderItemVO toItemVO(PurchaseOrderItem item) {
        PurchaseOrderItemVO vo = new PurchaseOrderItemVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }

    /**
     * 明细实体列表转明细视图对象列表。
     */
    public static List<PurchaseOrderItemVO> toItemVOList(List<PurchaseOrderItem> items) {
        return items.stream()
                .map(PurchaseOrderConvert::toItemVO)
                .collect(Collectors.toList());
    }

    /**
     * 明细入参转明细实体。
     */
    public static PurchaseOrderItem toItemEntity(PurchaseOrderItemDTO dto) {
        PurchaseOrderItem item = new PurchaseOrderItem();
        BeanUtils.copyProperties(dto, item);
        return item;
    }
}
