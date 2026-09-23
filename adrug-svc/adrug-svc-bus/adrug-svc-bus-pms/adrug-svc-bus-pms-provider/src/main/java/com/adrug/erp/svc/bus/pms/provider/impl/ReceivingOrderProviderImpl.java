package com.adrug.erp.svc.bus.pms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.pms.convert.ReceivingOrderConvert;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.ReceivingOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrder;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrderItem;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrder;
import com.adrug.erp.svc.bus.pms.entity.PurchaseOrderItem;
import com.adrug.erp.svc.bus.pms.entity.ReceivingOrder;
import com.adrug.erp.svc.bus.pms.entity.ReceivingOrderItem;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.provider.ReceivingOrderProvider;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.AcceptanceOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.AcceptanceOrderRepository;
import com.adrug.erp.svc.bus.pms.repository.PurchaseOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.PurchaseOrderRepository;
import com.adrug.erp.svc.bus.pms.repository.ReceivingOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.ReceivingOrderRepository;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderItemVO;
import com.adrug.erp.svc.bus.pms.vo.ReceivingOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 收货单服务实现。
 */
@Service
public class ReceivingOrderProviderImpl implements ReceivingOrderProvider {

    @Autowired
    private ReceivingOrderRepository receivingOrderRepository;

    @Autowired
    private ReceivingOrderItemRepository receivingOrderItemRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private AcceptanceOrderRepository acceptanceOrderRepository;

    @Autowired
    private AcceptanceOrderItemRepository acceptanceOrderItemRepository;

    @Override
    public PageResult<ReceivingOrderVO> page(ReceivingOrderQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<ReceivingOrder> pageResult = receivingOrderRepository.selectPage(query);
        List<ReceivingOrderVO> records = ReceivingOrderConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public ReceivingOrderVO getById(Long id) {
        ReceivingOrder order = receivingOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        ReceivingOrderVO vo = ReceivingOrderConvert.toVO(order);
        vo.setItems(ReceivingOrderConvert.toItemVOList(receivingOrderItemRepository.selectByOrderId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ReceivingOrderSaveDTO dto) {
        ReceivingOrder order = ReceivingOrderConvert.toEntity(dto);
        order.setId(null);
        order.setStatus(BillStatusEnum.DRAFT);
        List<ReceivingOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumReceiveQty(items));
        receivingOrderRepository.insert(order);
        for (ReceivingOrderItem item : items) {
            item.setOrderId(order.getId());
        }
        receivingOrderItemRepository.insertBatch(items);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, ReceivingOrderSaveDTO dto) {
        ReceivingOrder existing = receivingOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        ReceivingOrder order = ReceivingOrderConvert.toEntity(dto);
        order.setId(id);
        // 保留原单据状态（状态由过账动作变更）
        order.setStatus(existing.getStatus());
        List<ReceivingOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumReceiveQty(items));
        receivingOrderRepository.updateById(order);
        receivingOrderItemRepository.deleteByOrderId(id);
        for (ReceivingOrderItem item : items) {
            item.setOrderId(id);
        }
        receivingOrderItemRepository.insertBatch(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        ReceivingOrder existing = receivingOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        receivingOrderRepository.deleteById(id);
        receivingOrderItemRepository.deleteByOrderId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Long id) {
        ReceivingOrder order = receivingOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        if (order.getStatus() == BillStatusEnum.COMPLETED) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "收货单已制单完成，不能重复过账");
        }
        // 只更新状态和过账时间，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
        ReceivingOrder update = new ReceivingOrder();
        update.setId(order.getId());
        update.setVersion(order.getVersion());
        update.setStatus(BillStatusEnum.COMPLETED);
        update.setPostTime(LocalDateTime.now());
        receivingOrderRepository.updateById(update);
        // 过账下推：生成验收单（制单保存）
        generateAcceptanceOrder(order);
    }

    @Override
    public List<ReceivingOrderItemVO> loadItemsFromPurchaseOrder(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.selectById(purchaseOrderId);
        if (purchaseOrder == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        List<PurchaseOrderItem> poItems = purchaseOrderItemRepository.selectByOrderId(purchaseOrderId);
        List<ReceivingOrderItemVO> result = new ArrayList<>();
        for (PurchaseOrderItem poi : poItems) {
            ReceivingOrderItemVO vo = new ReceivingOrderItemVO();
            vo.setDrugId(poi.getDrugId());
            vo.setDrugCode(poi.getDrugCode());
            vo.setGenericName(poi.getGenericName());
            vo.setDrugName(poi.getDrugName());
            vo.setSpec(poi.getSpec());
            vo.setUnit(poi.getUnit());
            vo.setOrigin(poi.getOrigin());
            vo.setManufacturer(poi.getManufacturer());
            // 通知数量 = 采购数量
            vo.setNotifyQty(poi.getQty());
            vo.setPrice(poi.getPrice());
            vo.setSourceType("采购订单");
            vo.setSourceOrderNo(purchaseOrder.getOrderNo());
            result.add(vo);
        }
        return result;
    }

    /**
     * 收货单过账下推：生成一张验收单（制单保存）。
     */
    private void generateAcceptanceOrder(ReceivingOrder receivingOrder) {
        AcceptanceOrder acceptance = new AcceptanceOrder();
        acceptance.setOrderNo(receivingOrder.getOrderNo() + "-YS");
        acceptance.setSupplierId(receivingOrder.getSupplierId());
        acceptance.setSupplierName(receivingOrder.getSupplierName());
        acceptance.setWarehouseId(receivingOrder.getWarehouseId());
        acceptance.setWarehouseName(receivingOrder.getWarehouseName());
        acceptance.setSourceType("收货单");
        acceptance.setSourceOrderId(receivingOrder.getId());
        acceptance.setHandler(receivingOrder.getHandler());
        acceptance.setAcceptDate(LocalDate.now());
        acceptance.setStatus(BillStatusEnum.DRAFT);
        acceptance.setTotalQty(receivingOrder.getTotalQty());
        acceptanceOrderRepository.insert(acceptance);

        List<ReceivingOrderItem> items = receivingOrderItemRepository.selectByOrderId(receivingOrder.getId());
        List<AcceptanceOrderItem> acceptanceItems = new ArrayList<>();
        for (ReceivingOrderItem ri : items) {
            AcceptanceOrderItem ai = new AcceptanceOrderItem();
            ai.setOrderId(acceptance.getId());
            ai.setSeqNo(ri.getSeqNo());
            ai.setDrugId(ri.getDrugId());
            ai.setDrugCode(ri.getDrugCode());
            ai.setGenericName(ri.getGenericName());
            ai.setDrugName(ri.getDrugName());
            ai.setSpec(ri.getSpec());
            ai.setUnit(ri.getUnit());
            ai.setDosageForm(ri.getDosageForm());
            ai.setOrigin(ri.getOrigin());
            ai.setManufacturer(ri.getManufacturer());
            ai.setMarketingHolder(ri.getMarketingHolder());
            ai.setMarketingHolderAddress(ri.getMarketingHolderAddress());
            ai.setApprovalNo(ri.getApprovalNo());
            ai.setBatchNo(ri.getBatchNo());
            ai.setProductionDate(ri.getProductionDate());
            ai.setExpiryDate(ri.getExpiryDate());
            ai.setLocation(ri.getLocation());
            ai.setReceivedQty(ri.getReceiveQty());
            ai.setAcceptQty(ri.getReceiveQty());
            ai.setPrice(ri.getPrice());
            ai.setAmount(ri.getAmount());
            ai.setRejectQty(ri.getRejectQty());
            ai.setRejectReason(ri.getRejectReason());
            ai.setReceiver(ri.getReceiver());
            acceptanceItems.add(ai);
        }
        acceptanceOrderItemRepository.insertBatch(acceptanceItems);
    }

    /**
     * 明细入参列表转实体列表。
     */
    private List<ReceivingOrderItem> buildItems(List<ReceivingOrderItemDTO> itemDTOs) {
        List<ReceivingOrderItem> items = new ArrayList<>();
        if (itemDTOs == null) {
            return items;
        }
        for (ReceivingOrderItemDTO itemDTO : itemDTOs) {
            items.add(ReceivingOrderConvert.toItemEntity(itemDTO));
        }
        return items;
    }

    /**
     * 收货数量合计。
     */
    private BigDecimal sumReceiveQty(List<ReceivingOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (ReceivingOrderItem item : items) {
            if (item.getReceiveQty() != null) {
                total = total.add(item.getReceiveQty());
            }
        }
        return total;
    }
}
