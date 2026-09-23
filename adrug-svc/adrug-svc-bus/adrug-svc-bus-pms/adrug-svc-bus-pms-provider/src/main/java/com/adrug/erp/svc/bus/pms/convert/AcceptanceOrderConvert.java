package com.adrug.erp.svc.bus.pms.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrder;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrderItem;
import com.adrug.erp.svc.bus.pms.enums.UnqualifiedHandleEnum;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 验收单实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link BaseEnum#ofCode} 在 code 与枚举之间切换。
 */
public final class AcceptanceOrderConvert {

    private AcceptanceOrderConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static AcceptanceOrderVO toVO(AcceptanceOrder order) {
        AcceptanceOrderVO vo = new AcceptanceOrderVO();
        BeanUtils.copyProperties(order, vo);
        if (order.getStatus() != null) {
            vo.setStatus(order.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<AcceptanceOrderVO> toVOList(List<AcceptanceOrder> orders) {
        return orders.stream()
                .map(AcceptanceOrderConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static AcceptanceOrder toEntity(AcceptanceOrderSaveDTO dto) {
        AcceptanceOrder order = new AcceptanceOrder();
        BeanUtils.copyProperties(dto, order);
        return order;
    }

    /**
     * 明细实体转明细视图对象（枚举 → code）。
     */
    public static AcceptanceOrderItemVO toItemVO(AcceptanceOrderItem item) {
        AcceptanceOrderItemVO vo = new AcceptanceOrderItemVO();
        BeanUtils.copyProperties(item, vo);
        if (item.getHandleType() != null) {
            vo.setHandleType(item.getHandleType().getCode());
        }
        return vo;
    }

    /**
     * 明细实体列表转明细视图对象列表。
     */
    public static List<AcceptanceOrderItemVO> toItemVOList(List<AcceptanceOrderItem> items) {
        return items.stream()
                .map(AcceptanceOrderConvert::toItemVO)
                .collect(Collectors.toList());
    }

    /**
     * 明细入参转明细实体（code → 枚举）。
     */
    public static AcceptanceOrderItem toItemEntity(AcceptanceOrderItemDTO dto) {
        AcceptanceOrderItem item = new AcceptanceOrderItem();
        BeanUtils.copyProperties(dto, item);
        item.setHandleType(BaseEnum.ofCode(UnqualifiedHandleEnum.class, dto.getHandleType()));
        return item;
    }
}
