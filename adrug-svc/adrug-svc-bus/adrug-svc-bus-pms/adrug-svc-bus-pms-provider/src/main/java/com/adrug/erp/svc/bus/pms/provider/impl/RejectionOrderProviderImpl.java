package com.adrug.erp.svc.bus.pms.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.pms.convert.RejectionOrderConvert;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.entity.RejectionOrder;
import com.adrug.erp.svc.bus.pms.entity.RejectionOrderItem;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.provider.RejectionOrderProvider;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.RejectionOrderItemRepository;
import com.adrug.erp.svc.bus.pms.repository.RejectionOrderRepository;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 拒收单服务实现。
 */
@Service
public class RejectionOrderProviderImpl implements RejectionOrderProvider {

    @Autowired
    private RejectionOrderRepository rejectionOrderRepository;

    @Autowired
    private RejectionOrderItemRepository rejectionOrderItemRepository;

    @Override
    public PageResult<RejectionOrderVO> page(RejectionOrderQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<RejectionOrder> pageResult = rejectionOrderRepository.selectPage(query);
        List<RejectionOrderVO> records = RejectionOrderConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public RejectionOrderVO getById(Long id) {
        RejectionOrder order = rejectionOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        RejectionOrderVO vo = RejectionOrderConvert.toVO(order);
        vo.setItems(RejectionOrderConvert.toItemVOList(rejectionOrderItemRepository.selectByOrderId(id)));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RejectionOrderSaveDTO dto) {
        RejectionOrder order = RejectionOrderConvert.toEntity(dto);
        order.setId(null);
        order.setStatus(BillStatusEnum.DRAFT);
        rejectionOrderRepository.insert(order);
        List<RejectionOrderItem> items = RejectionOrderConvert.toItemEntityList(dto.getItems());
        for (RejectionOrderItem item : items) {
            item.setOrderId(order.getId());
        }
        rejectionOrderItemRepository.insertBatch(items);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RejectionOrderSaveDTO dto) {
        RejectionOrder existing = rejectionOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        RejectionOrder order = RejectionOrderConvert.toEntity(dto);
        order.setId(id);
        // 保留原单据状态（状态由过账动作变更）
        order.setStatus(existing.getStatus());
        rejectionOrderRepository.updateById(order);
        rejectionOrderItemRepository.deleteByOrderId(id);
        List<RejectionOrderItem> items = RejectionOrderConvert.toItemEntityList(dto.getItems());
        for (RejectionOrderItem item : items) {
            item.setOrderId(id);
        }
        rejectionOrderItemRepository.insertBatch(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        RejectionOrder existing = rejectionOrderRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        rejectionOrderRepository.deleteById(id);
        rejectionOrderItemRepository.deleteByOrderId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Long id) {
        RejectionOrder order = rejectionOrderRepository.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        if (order.getStatus() == BillStatusEnum.COMPLETED) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "拒收单已制单完成，不能重复过账");
        }
        // 只更新状态和过账时间，避免把分片键 tenant_id 放进 SET（ShardingSphere 禁止修改分片值）
        RejectionOrder update = new RejectionOrder();
        update.setId(order.getId());
        update.setVersion(order.getVersion());
        update.setStatus(BillStatusEnum.COMPLETED);
        update.setPostTime(LocalDateTime.now());
        rejectionOrderRepository.updateById(update);
    }
}
