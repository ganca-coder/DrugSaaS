package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusPmsQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.feign.AcceptanceOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.PurchaseInboundOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.PurchaseOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.ReceivingOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.RejectionOrderFeign;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购管理查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusPmsQueryServiceImpl implements BusPmsQueryService {

    @Autowired
    private PurchaseOrderFeign purchaseOrderFeign;
    @Autowired
    private ReceivingOrderFeign receivingOrderFeign;
    @Autowired
    private AcceptanceOrderFeign acceptanceOrderFeign;
    @Autowired
    private PurchaseInboundOrderFeign purchaseInboundOrderFeign;
    @Autowired
    private RejectionOrderFeign rejectionOrderFeign;

    @Override
    public PageResult<PurchaseOrderVO> getPurchaseOrderPage(PurchaseOrderQuery query) {
        return purchaseOrderFeign.page(query).getData();
    }

    @Override
    public PurchaseOrderVO getPurchaseOrderById(Long id) {
        return purchaseOrderFeign.getById(id).getData();
    }

    @Override
    public PageResult<ReceivingOrderVO> getReceivingOrderPage(ReceivingOrderQuery query) {
        return receivingOrderFeign.page(query).getData();
    }

    @Override
    public ReceivingOrderVO getReceivingOrderById(Long id) {
        return receivingOrderFeign.getById(id).getData();
    }

    @Override
    public PageResult<AcceptanceOrderVO> getAcceptanceOrderPage(AcceptanceOrderQuery query) {
        return acceptanceOrderFeign.page(query).getData();
    }

    @Override
    public AcceptanceOrderVO getAcceptanceOrderById(Long id) {
        return acceptanceOrderFeign.getById(id).getData();
    }

    @Override
    public PageResult<PurchaseInboundOrderVO> getPurchaseInboundOrderPage(PurchaseInboundOrderQuery query) {
        return purchaseInboundOrderFeign.page(query).getData();
    }

    @Override
    public PurchaseInboundOrderVO getPurchaseInboundOrderById(Long id) {
        return purchaseInboundOrderFeign.getById(id).getData();
    }

    @Override
    public PageResult<RejectionOrderVO> getRejectionOrderPage(RejectionOrderQuery query) {
        return rejectionOrderFeign.page(query).getData();
    }

    @Override
    public RejectionOrderVO getRejectionOrderById(Long id) {
        return rejectionOrderFeign.getById(id).getData();
    }

    @Override
    public List<ReceivingOrderItemVO> loadReceivingItemsFromPurchaseOrder(Long purchaseOrderId) {
        return receivingOrderFeign.loadItemsFromPurchaseOrder(purchaseOrderId).getData();
    }
}
