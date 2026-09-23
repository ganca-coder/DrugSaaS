package com.adrug.erp.svc.bus.pms.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.ReceivingOrder;
import com.adrug.erp.svc.bus.pms.entity.ReceivingOrderItem;
import com.adrug.erp.svc.bus.pms.enums.SourceTypeEnum;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收货单实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link BaseEnum#ofCode} 在 code 与枚举之间切换。
 */
public final class ReceivingOrderConvert {

    private ReceivingOrderConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static ReceivingOrderVO toVO(ReceivingOrder order) {
        ReceivingOrderVO vo = new ReceivingOrderVO();
        BeanUtils.copyProperties(order, vo);
        if (order.getSourceType() != null) {
            vo.setSourceType(order.getSourceType().getCode());
        }
        if (order.getStatus() != null) {
            vo.setStatus(order.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<ReceivingOrderVO> toVOList(List<ReceivingOrder> orders) {
        return orders.stream()
                .map(ReceivingOrderConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static ReceivingOrder toEntity(ReceivingOrderSaveDTO dto) {
        ReceivingOrder order = new ReceivingOrder();
        BeanUtils.copyProperties(dto, order);
        order.setSourceType(BaseEnum.ofCode(SourceTypeEnum.class, dto.getSourceType()));
        return order;
    }

    /**
     * 明细实体转明细视图对象。
     */
    public static ReceivingOrderItemVO toItemVO(ReceivingOrderItem item) {
        ReceivingOrderItemVO vo = new ReceivingOrderItemVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }

    /**
     * 明细实体列表转明细视图对象列表。
     */
    public static List<ReceivingOrderItemVO> toItemVOList(List<ReceivingOrderItem> items) {
        return items.stream()
                .map(ReceivingOrderConvert::toItemVO)
                .collect(Collectors.toList());
    }

    /**
     * 明细入参转明细实体。
     */
    public static ReceivingOrderItem toItemEntity(ReceivingOrderItemDTO dto) {
        ReceivingOrderItem item = new ReceivingOrderItem();
        BeanUtils.copyProperties(dto, item);
        return item;
    }
}
