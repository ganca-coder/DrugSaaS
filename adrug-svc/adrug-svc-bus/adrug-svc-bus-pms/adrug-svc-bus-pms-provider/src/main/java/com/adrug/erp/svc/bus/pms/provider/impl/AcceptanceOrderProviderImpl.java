package com.adrug.erp.svc.bus.pms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.context.UserContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.pms.convert.AcceptanceOrderConvert;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderItemDTO;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrder;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrderItem;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrder;
import com.adrug.erp.svc.bus.pms.entity.PurchaseInboundOrderItem;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.provider.AcceptanceOrderProvider;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.AcceptanceOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.AcceptanceOrderRepository;
import com.adrug.erp.svc.bus.pms.repository.PurchaseInboundOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.PurchaseInboundOrderRepository;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 验收单服务实现。
 */
@Service
public class AcceptanceOrderProviderImpl implements AcceptanceOrderProvider {

    @Autowired
    private AcceptanceOrderRepository acceptanceOrderRepository;

    @Autowired
    private AcceptanceOrderItemRepository acceptanceOrderItemRepository;

    @Autowired
    private PurchaseInboundOrderRepository purchaseInboundOrderRepository;

    @Autowired
    private PurchaseInboundOrderItemRepository purchaseInboundOrderItemRepository;

    @Override
    public PageResult<AcceptanceOrderVO> page(AcceptanceOrderQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<AcceptanceOrder> pageResult = acceptanceOrderRepository.selectPage(query);
        List<AcceptanceOrderVO> records = AcceptanceOrderConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public AcceptanceOrderVO getById(Long id) {
        AcceptanceOrder order = acceptanceOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        AcceptanceOrderVO vo = AcceptanceOrderConvert.toVO(order);
        vo.setItems(AcceptanceOrderConvert.toItemVOList(acceptanceOrderItemRepository.selectByOrderId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(AcceptanceOrderSaveDTO dto) {
        AcceptanceOrder order = AcceptanceOrderConvert.toEntity(dto);
        order.setId(null);
        order.setStatus(BillStatusEnum.DRAFT);
        List<AcceptanceOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumAcceptQty(items));
        acceptanceOrderRepository.insert(order);
        for (AcceptanceOrderItem item : items) {
            item.setOrderId(order.getId());
        }
        acceptanceOrderItemRepository.insertBatch(items);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, AcceptanceOrderSaveDTO dto) {
        AcceptanceOrder existing = acceptanceOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        AcceptanceOrder order = AcceptanceOrderConvert.toEntity(dto);
        order.setId(id);
        // 保留原单据状态（状态由过账动作变更）
        order.setStatus(existing.getStatus());
        List<AcceptanceOrderItem> items = buildItems(dto.getItems());
        order.setTotalQty(sumAcceptQty(items));
        acceptanceOrderRepository.updateById(order);
        acceptanceOrderItemRepository.deleteByOrderId(id);
        for (AcceptanceOrderItem item : items) {
            item.setOrderId(id);
        }
        acceptanceOrderItemRepository.insertBatch(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        AcceptanceOrder existing = acceptanceOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        acceptanceOrderRepository.deleteById(id);
        acceptanceOrderItemRepository.deleteByOrderId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accept(Long id) {
        AcceptanceOrder order = acceptanceOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        if (order.getAcceptTime() != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "验收单已验收，不能重复验收");
        }
        // 只更新验收人和验收时间，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
        AcceptanceOrder update = new AcceptanceOrder();
        update.setId(order.getId());
        update.setVersion(order.getVersion());
        update.setAcceptUserId(UserContextHolder.getAccountId());
        update.setAcceptTime(LocalDateTime.now());
        acceptanceOrderRepository.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Long id) {
        AcceptanceOrder order = acceptanceOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 验收单须先验收后方可过账
        if (order.getAcceptTime() == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "验收单须先完成验收后方可过账");
        }
        if (order.getStatus() == BillStatusEnum.COMPLETED) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "验收单已制单完成，不能重复过账");
        }
        // 只更新状态和过账时间，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
        AcceptanceOrder update = new AcceptanceOrder();
        update.setId(order.getId());
        update.setVersion(order.getVersion());
        update.setStatus(BillStatusEnum.COMPLETED);
        update.setPostTime(LocalDateTime.now());
        acceptanceOrderRepository.updateById(update);
        // 过账下推：生成采购入库单（制单保存）
        generateInboundOrder(order);
    }

    /**
     * 验收单过账下推：生成一张采购入库单（制单保存）。
     */
    private void generateInboundOrder(AcceptanceOrder acceptance) {
        List<AcceptanceOrderItem> items = acceptanceOrderItemRepository.selectByOrderId(acceptance.getId());
        List<PurchaseInboundOrderItem> inboundItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (AcceptanceOrderItem ai : items) {
            PurchaseInboundOrderItem bi = new PurchaseInboundOrderItem();
            bi.setSeqNo(ai.getSeqNo());
            bi.setDrugId(ai.getDrugId());
            bi.setDrugCode(ai.getDrugCode());
            bi.setGenericName(ai.getGenericName());
            bi.setDrugName(ai.getDrugName());
            bi.setSpec(ai.getSpec());
            bi.setUnit(ai.getUnit());
            bi.setDosageForm(ai.getDosageForm());
            bi.setOrigin(ai.getOrigin());
            bi.setManufacturer(ai.getManufacturer());
            bi.setProductionLicenseNo(ai.getProductionLicenseNo());
            bi.setMarketingHolder(ai.getMarketingHolder());
            bi.setMarketingHolderAddress(ai.getMarketingHolderAddress());
            bi.setApprovalNo(ai.getApprovalNo());
            bi.setBatchNo(ai.getBatchNo());
            bi.setProductionDate(ai.getProductionDate());
            bi.setExpiryDate(ai.getExpiryDate());
            bi.setInboundQty(ai.getAcceptQty());
            bi.setPrice(ai.getPrice());
            bi.setAmount(ai.getAmount());
            if (ai.getAmount() != null) {
                totalAmount = totalAmount.add(ai.getAmount());
            }
            inboundItems.add(bi);
        }

        PurchaseInboundOrder inbound = new PurchaseInboundOrder();
        inbound.setOrderNo(acceptance.getOrderNo() + "-RK");
        inbound.setSupplierId(acceptance.getSupplierId());
        inbound.setSupplierName(acceptance.getSupplierName());
        inbound.setWarehouseId(acceptance.getWarehouseId());
        inbound.setWarehouseName(acceptance.getWarehouseName());
        inbound.setHandler(acceptance.getHandler());
        inbound.setAcceptor(acceptance.getAcceptUserId() == null ? null : String.valueOf(acceptance.getAcceptUserId()));
        inbound.setSourceOrderId(acceptance.getId());
        inbound.setInboundDate(LocalDate.now());
        inbound.setStatus(BillStatusEnum.DRAFT);
        inbound.setTotalQty(acceptance.getTotalQty());
        inbound.setTotalAmount(totalAmount);
        purchaseInboundOrderRepository.insert(inbound);

        for (PurchaseInboundOrderItem bi : inboundItems) {
            bi.setOrderId(inbound.getId());
        }
        purchaseInboundOrderItemRepository.insertBatch(inboundItems);
    }

    /**
     * 明细入参列表转实体列表。
     */
    private List<AcceptanceOrderItem> buildItems(List<AcceptanceOrderItemDTO> itemDTOs) {
        List<AcceptanceOrderItem> items = new ArrayList<>();
        if (itemDTOs == null) {
            return items;
        }
        for (AcceptanceOrderItemDTO itemDTO : itemDTOs) {
            items.add(AcceptanceOrderConvert.toItemEntity(itemDTO));
        }
        return items;
    }

    /**
     * 验收数量合计。
     */
    private BigDecimal sumAcceptQty(List<AcceptanceOrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (AcceptanceOrderItem item : items) {
            if (item.getAcceptQty() != null) {
                total = total.add(item.getAcceptQty());
            }
        }
        return total;
    }
}
