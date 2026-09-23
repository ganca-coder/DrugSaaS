package com.adrug.erp.svc.bus.pms.convert;

import com.adrug.erp.svc.bus.pms.dto.RejectionOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.RejectionOrder;
import com.adrug.erp.svc.bus.pms.entity.RejectionOrderItem;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拒收单实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型。
 */
public final class RejectionOrderConvert {

    private RejectionOrderConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code）。
     */
    public static RejectionOrderVO toVO(RejectionOrder order) {
        RejectionOrderVO vo = new RejectionOrderVO();
        BeanUtils.copyProperties(order, vo);
        if (order.getStatus() != null) {
            vo.setStatus(order.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<RejectionOrderVO> toVOList(List<RejectionOrder> orders) {
        return orders.stream()
                .map(RejectionOrderConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体。
     */
    public static RejectionOrder toEntity(RejectionOrderSaveDTO dto) {
        RejectionOrder order = new RejectionOrder();
        BeanUtils.copyProperties(dto, order);
        return order;
    }

    /**
     * 明细实体转明细视图对象。
     */
    public static RejectionOrderItemVO toItemVO(RejectionOrderItem item) {
        RejectionOrderItemVO vo = new RejectionOrderItemVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }

    /**
     * 明细实体列表转明细视图对象列表。
     */
    public static List<RejectionOrderItemVO> toItemVOList(List<RejectionOrderItem> items) {
        return items.stream()
                .map(RejectionOrderConvert::toItemVO)
                .collect(Collectors.toList());
    }

    /**
     * 明细入参列表转明细实体列表，序号缺省按顺序从 1 开始编号。
     */
    public static List<RejectionOrderItem> toItemEntityList(List<RejectionOrderItemDTO> itemDTOs) {
        List<RejectionOrderItem> items = new ArrayList<>();
        if (itemDTOs == null) {
            return items;
        }
        for (int i = 0; i < itemDTOs.size(); i++) {
            RejectionOrderItemDTO dto = itemDTOs.get(i);
            RejectionOrderItem item = new RejectionOrderItem();
            BeanUtils.copyProperties(dto, item);
            if (item.getSeqNo() == null) {
                item.setSeqNo(i + 1);
            }
            items.add(item);
        }
        return items;
    }
}
