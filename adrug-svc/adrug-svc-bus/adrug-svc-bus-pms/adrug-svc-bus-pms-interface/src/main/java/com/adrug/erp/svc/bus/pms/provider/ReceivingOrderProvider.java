package com.adrug.erp.svc.bus.pms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;

import java.util.List;

/**
 * 收货单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code ReceivingOrderProviderImpl}。
 */
public interface ReceivingOrderProvider {

    /**
     * 分页查询收货单。
     */
    PageResult<ReceivingOrderVO> page(ReceivingOrderQuery query);

    /**
     * 按 ID 查询收货单（含明细）。
     */
    ReceivingOrderVO getById(Long id);

    /**
     * 新增收货单（含明细）。
     *
     * @return 新收货单主键
     */
    Long create(ReceivingOrderSaveDTO dto);

    /**
     * 变更收货单（含明细）。
     */
    void update(Long id, ReceivingOrderSaveDTO dto);

    /**
     * 删除收货单（逻辑删除，含明细）。
     */
    void delete(Long id);

    /**
     * 过账收货单（状态：制单保存 → 制单完成），并下推生成验收单。
     */
    void post(Long id);

    /**
     * 转单：加载采购订单明细，用于生成收货单明细。
     *
     * @param purchaseOrderId 采购订单ID
     * @return 收货单明细模板
     */
    List<ReceivingOrderItemVO> loadItemsFromPurchaseOrder(Long purchaseOrderId);
}
