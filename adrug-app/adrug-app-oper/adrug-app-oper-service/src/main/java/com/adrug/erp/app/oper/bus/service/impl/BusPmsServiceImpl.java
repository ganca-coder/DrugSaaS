package com.adrug.erp.app.oper.bus.service.impl;

import com.adrug.erp.app.oper.bus.service.BusPmsService;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseInboundOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.PurchaseOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.feign.AcceptanceOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.PurchaseInboundOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.PurchaseOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.ReceivingOrderFeign;
import com.adrug.erp.svc.bus.pms.feign.RejectionOrderFeign;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.PurchaseInboundOrderVO;
import com.adrug.erp.svc.bus.wms.dto.InventoryIncreaseDTO;
import com.adrug.erp.svc.bus.wms.feign.InventoryFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购管理服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class BusPmsServiceImpl implements BusPmsService {

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
    @Autowired
    private InventoryFeign inventoryFeign;

    @Override
    public Long createPurchaseOrder(PurchaseOrderSaveDTO dto) {
        return purchaseOrderFeign.create(dto).getData();
    }

    @Override
    public void updatePurchaseOrder(Long id, PurchaseOrderSaveDTO dto) {
        purchaseOrderFeign.update(id, dto);
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        purchaseOrderFeign.delete(id);
    }

    @Override
    public void postPurchaseOrder(Long id) {
        purchaseOrderFeign.post(id);
    }

    @Override
    public Long createReceivingOrder(ReceivingOrderSaveDTO dto) {
        return receivingOrderFeign.create(dto).getData();
    }

    @Override
    public void updateReceivingOrder(Long id, ReceivingOrderSaveDTO dto) {
        receivingOrderFeign.update(id, dto);
    }

    @Override
    public void deleteReceivingOrder(Long id) {
        receivingOrderFeign.delete(id);
    }

    @Override
    public void postReceivingOrder(Long id) {
        receivingOrderFeign.post(id);
    }

    @Override
    public Long createAcceptanceOrder(AcceptanceOrderSaveDTO dto) {
        return acceptanceOrderFeign.create(dto).getData();
    }

    @Override
    public void updateAcceptanceOrder(Long id, AcceptanceOrderSaveDTO dto) {
        acceptanceOrderFeign.update(id, dto);
    }

    @Override
    public void deleteAcceptanceOrder(Long id) {
        acceptanceOrderFeign.delete(id);
    }

    @Override
    public void acceptAcceptanceOrder(Long id) {
        acceptanceOrderFeign.accept(id);
    }

    @Override
    public void postAcceptanceOrder(Long id) {
        acceptanceOrderFeign.post(id);
    }

    @Override
    public Long createPurchaseInboundOrder(PurchaseInboundOrderSaveDTO dto) {
        return purchaseInboundOrderFeign.create(dto).getData();
    }

    @Override
    public void updatePurchaseInboundOrder(Long id, PurchaseInboundOrderSaveDTO dto) {
        purchaseInboundOrderFeign.update(id, dto);
    }

    @Override
    public void deletePurchaseInboundOrder(Long id) {
        purchaseInboundOrderFeign.delete(id);
    }

    @GlobalTransactional(name = "postPurchaseInboundOrder", rollbackFor = Exception.class)
    @Transactional
    @Override
    public void postPurchaseInboundOrder(Long id) {
        // 先取单据与明细校验：库存按商品维度入账，明细缺少商品ID会导致过账失败，提前拦截
        PurchaseInboundOrderVO order = purchaseInboundOrderFeign.getById(id).getData();
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "采购入库单无明细，无法过账");
        }
        for (PurchaseInboundOrderItemVO item : order.getItems()) {
            if (item.getDrugId() == null) {
                throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "明细行缺少商品ID，无法过账");
            }
        }
        // 过账采购入库单（svc 层只改状态）
        purchaseInboundOrderFeign.post(id);
        // 过账后增加库存：应用层编排跨模块调用（pms → wms）
        List<InventoryIncreaseDTO> increaseItems = new ArrayList<>();
        for (PurchaseInboundOrderItemVO item : order.getItems()) {
            InventoryIncreaseDTO dto = new InventoryIncreaseDTO();
            dto.setDrugId(item.getDrugId());
            dto.setDrugCode(item.getDrugCode());
            dto.setDrugName(item.getDrugName());
            dto.setSpec(item.getSpec());
            dto.setUnit(item.getUnit());
            dto.setWarehouseId(order.getWarehouseId());
            dto.setWarehouseName(order.getWarehouseName());
            dto.setBatchNo(item.getBatchNo());
            dto.setProductionDate(item.getProductionDate());
            dto.setExpiryDate(item.getExpiryDate());
            dto.setQuantity(item.getInboundQty());
            increaseItems.add(dto);
        }
        inventoryFeign.increase(increaseItems);
    }

    @Override
    public Long createRejectionOrder(RejectionOrderSaveDTO dto) {
        return rejectionOrderFeign.create(dto).getData();
    }

    @Override
    public void updateRejectionOrder(Long id, RejectionOrderSaveDTO dto) {
        rejectionOrderFeign.update(id, dto);
    }

    @Override
    public void deleteRejectionOrder(Long id) {
        rejectionOrderFeign.delete(id);
    }

    @Override
    public void postRejectionOrder(Long id) {
        rejectionOrderFeign.post(id);
    }
}
