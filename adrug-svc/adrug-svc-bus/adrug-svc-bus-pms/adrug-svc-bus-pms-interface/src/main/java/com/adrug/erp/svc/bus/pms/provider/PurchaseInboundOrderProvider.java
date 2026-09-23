package com.adrug.erp.svc.bus.pms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;

/**
 * 采购入库单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code PurchaseInboundOrderProviderImpl}。
 */
public interface PurchaseInboundOrderProvider {

    /**
     * 分页查询采购入库单。
     */
    PageResult<PurchaseInboundOrderVO> page(PurchaseInboundOrderQuery query);

    /**
     * 按 ID 查询采购入库单（含明细）。
     */
    PurchaseInboundOrderVO getById(Long id);

    /**
     * 新增采购入库单（含明细）。
     *
     * @return 新采购入库单主键
     */
    Long create(PurchaseInboundOrderSaveDTO dto);

    /**
     * 变更采购入库单（含明细）。
     */
    void update(Long id, PurchaseInboundOrderSaveDTO dto);

    /**
     * 删除采购入库单（逻辑删除，含明细）。
     */
    void delete(Long id);

    /**
     * 过账采购入库单（状态：制单保存 → 制单完成）。
     * <p>
     * 过账后增加系统库存、产生应付账（由 WMS/财务模块后续接入）。
     */
    void post(Long id);
}
