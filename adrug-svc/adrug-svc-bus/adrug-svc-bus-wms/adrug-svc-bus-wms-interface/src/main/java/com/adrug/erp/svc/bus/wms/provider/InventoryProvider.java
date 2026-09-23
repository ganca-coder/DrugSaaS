package com.adrug.erp.svc.bus.wms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.wms.dto.InventoryIncreaseDTO;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;

import java.util.List;

/**
 * 库存服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code InventoryProviderImpl}。
 */
public interface InventoryProvider {

    /**
     * 分页查询库存。
     */
    PageResult<InventoryVO> page(InventoryQuery query);

    /**
     * 增加库存（入库过账时调用）。
     *
     * @param items 入库明细（商品 + 仓库 + 批号 + 数量）
     */
    void increase(List<InventoryIncreaseDTO> items);
}
