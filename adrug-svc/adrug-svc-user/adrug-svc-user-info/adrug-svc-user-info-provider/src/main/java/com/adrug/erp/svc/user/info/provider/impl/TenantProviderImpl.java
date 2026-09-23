package com.adrug.erp.svc.user.info.provider.impl;

import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.info.convert.TenantConvert;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.entity.Tenant;
import com.adrug.erp.svc.user.info.provider.TenantProvider;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.repository.TenantRepository;
import com.adrug.erp.svc.user.info.vo.TenantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户服务实现。
 */
@Service
public class TenantProviderImpl implements TenantProvider {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public PageResult<TenantVO> page(TenantQuery query) {
        PageResult<Tenant> pageResult = tenantRepository.selectPage(query);
        List<TenantVO> records = TenantConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public TenantVO getById(Long id) {
        Tenant tenant = tenantRepository.selectById(id);
        if (tenant == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return TenantConvert.toVO(tenant);
    }

    @Override
    public Long create(TenantSaveDTO dto) {
        Tenant tenant = TenantConvert.toEntity(dto);
        tenant.setId(null);
        tenantRepository.insert(tenant);
        return tenant.getId();
    }

    @Override
    public void update(Long id, TenantSaveDTO dto) {
        Tenant existing = tenantRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Tenant tenant = TenantConvert.toEntity(dto);
        tenant.setId(id);
        tenantRepository.updateById(tenant);
    }

    @Override
    public void delete(Long id) {
        Tenant existing = tenantRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        tenantRepository.deleteById(id);
    }
}
