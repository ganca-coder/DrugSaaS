package com.adrug.erp.svc.bus.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.info.convert.WarehouseConvert;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.entity.Warehouse;
import com.adrug.erp.svc.bus.info.provider.WarehouseProvider;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.repository.WarehouseRepository;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仓库信息服务实现。
 */
@Service
public class WarehouseProviderImpl implements WarehouseProvider {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public PageResult<WarehouseVO> page(WarehouseQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Warehouse> pageResult = warehouseRepository.selectPage(query);
        List<WarehouseVO> records = WarehouseConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public WarehouseVO getById(Long id) {
        Warehouse warehouse = warehouseRepository.selectById(id);
        if (warehouse == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return WarehouseConvert.toVO(warehouse);
    }

    @Override
    public Long create(WarehouseSaveDTO dto) {
        Warehouse warehouse = WarehouseConvert.toEntity(dto);
        warehouse.setId(null);
        warehouseRepository.insert(warehouse);
        return warehouse.getId();
    }

    @Override
    public void update(Long id, WarehouseSaveDTO dto) {
        Warehouse existing = warehouseRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Warehouse warehouse = WarehouseConvert.toEntity(dto);
        warehouse.setId(id);
        warehouseRepository.updateById(warehouse);
    }

    @Override
    public void delete(Long id) {
        Warehouse existing = warehouseRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // @TableLogic 逻辑删除
        warehouseRepository.deleteById(id);
    }
}
