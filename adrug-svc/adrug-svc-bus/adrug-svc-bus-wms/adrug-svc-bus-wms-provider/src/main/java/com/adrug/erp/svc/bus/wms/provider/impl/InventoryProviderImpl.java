package com.adrug.erp.svc.bus.wms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.wms.convert.InventoryConvert;
import com.adrug.erp.svc.bus.wms.dto.InventoryIncreaseDTO;
import com.adrug.erp.svc.bus.wms.entity.Inventory;
import com.adrug.erp.svc.bus.wms.provider.InventoryProvider;
import com.adrug.erp.svc.bus.wms.query.InventoryQuery;
import com.adrug.erp.svc.bus.wms.repository.InventoryRepository;
import com.adrug.erp.svc.bus.wms.vo.InventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存服务实现。
 */
@Service
public class InventoryProviderImpl implements InventoryProvider {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public PageResult<InventoryVO> page(InventoryQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Inventory> pageResult = inventoryRepository.selectPage(query);
        List<InventoryVO> records = InventoryConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increase(List<InventoryIncreaseDTO> items) {
        if (items == null) {
            return;
        }
        for (InventoryIncreaseDTO item : items) {
            if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            Inventory existing = inventoryRepository.selectByKey(item.getDrugId(), item.getWarehouseId(), item.getBatchNo());
            if (existing != null) {
                // 只更新数量，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
                BigDecimal currentQty = existing.getQuantity() == null ? BigDecimal.ZERO : existing.getQuantity();
                Inventory update = new Inventory();
                update.setId(existing.getId());
                update.setVersion(existing.getVersion());
                update.setQuantity(currentQty.add(item.getQuantity()));
                inventoryRepository.updateById(update);
            } else {
                Inventory inventory = InventoryConvert.toEntity(item);
                inventory.setBatchNo(item.getBatchNo() == null ? "" : item.getBatchNo());
                inventory.setQuantity(item.getQuantity());
                inventoryRepository.insert(inventory);
            }
        }
    }
}
