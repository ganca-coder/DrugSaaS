package com.adrug.erp.app.oper.bus.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.query.PurchaseInboundOrderQuery;
import com.adrug.erp.svc.bus.pms.query.PurchaseOrderQuery;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseOrderVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;

import java.util.List;

/**
 * 采购管理查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface BusPmsQueryService {

    PageResult<PurchaseOrderVO> getPurchaseOrderPage(PurchaseOrderQuery query);

    PurchaseOrderVO getPurchaseOrderById(Long id);

    PageResult<ReceivingOrderVO> getReceivingOrderPage(ReceivingOrderQuery query);

    ReceivingOrderVO getReceivingOrderById(Long id);

    PageResult<AcceptanceOrderVO> getAcceptanceOrderPage(AcceptanceOrderQuery query);

    AcceptanceOrderVO getAcceptanceOrderById(Long id);

    PageResult<PurchaseInboundOrderVO> getPurchaseInboundOrderPage(PurchaseInboundOrderQuery query);

    PurchaseInboundOrderVO getPurchaseInboundOrderById(Long id);

    PageResult<RejectionOrderVO> getRejectionOrderPage(RejectionOrderQuery query);

    RejectionOrderVO getRejectionOrderById(Long id);

    List<ReceivingOrderItemVO> loadReceivingItemsFromPurchaseOrder(Long purchaseOrderId);
}
