package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;

/**
 * 采购管理服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusPmsService {

    Long createPurchaseOrder(PurchaseOrderSaveDTO dto);

    void updatePurchaseOrder(Long id, PurchaseOrderSaveDTO dto);

    void deletePurchaseOrder(Long id);

    void postPurchaseOrder(Long id);

    Long createReceivingOrder(ReceivingOrderSaveDTO dto);

    void updateReceivingOrder(Long id, ReceivingOrderSaveDTO dto);

    void deleteReceivingOrder(Long id);

    void postReceivingOrder(Long id);

    Long createAcceptanceOrder(AcceptanceOrderSaveDTO dto);

    void updateAcceptanceOrder(Long id, AcceptanceOrderSaveDTO dto);

    void deleteAcceptanceOrder(Long id);

    void acceptAcceptanceOrder(Long id);

    void postAcceptanceOrder(Long id);

    Long createPurchaseInboundOrder(PurchaseInboundOrderSaveDTO dto);

    void updatePurchaseInboundOrder(Long id, PurchaseInboundOrderSaveDTO dto);

    void deletePurchaseInboundOrder(Long id);

    void postPurchaseInboundOrder(Long id);

    Long createRejectionOrder(RejectionOrderSaveDTO dto);

    void updateRejectionOrder(Long id, RejectionOrderSaveDTO dto);

    void deleteRejectionOrder(Long id);

    void postRejectionOrder(Long id);
}
