package com.adrug.erp.svc.bus.pms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;

/**
 * 采购订单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code PurchaseOrderProviderImpl}。
 */
public interface PurchaseOrderProvider {

    /**
     * 分页查询采购订单。
     */
    PageResult<PurchaseOrderVO> page(PurchaseOrderQuery query);

    /**
     * 按 ID 查询采购订单（含明细）。
     */
    PurchaseOrderVO getById(Long id);

    /**
     * 新增采购订单（含明细）。
     *
     * @return 新采购订单主键
     */
    Long create(PurchaseOrderSaveDTO dto);

    /**
     * 变更采购订单（含明细）。
     */
    void update(Long id, PurchaseOrderSaveDTO dto);

    /**
     * 删除采购订单（逻辑删除，含明细）。
     */
    void delete(Long id);

    /**
     * 过账采购订单（状态：制单保存 → 制单完成）。
     */
    void post(Long id);
}
