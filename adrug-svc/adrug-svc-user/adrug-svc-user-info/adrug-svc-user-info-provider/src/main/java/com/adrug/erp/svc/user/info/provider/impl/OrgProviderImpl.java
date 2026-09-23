package com.adrug.erp.svc.user.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.user.info.convert.OrgConvert;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.entity.Org;
import com.adrug.erp.svc.user.info.provider.OrgProvider;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.repository.OrgRepository;
import com.adrug.erp.svc.user.info.vo.OrgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构服务实现。
 */
@Service
public class OrgProviderImpl implements OrgProvider {

    @Autowired
    private OrgRepository orgRepository;

    @Override
    public PageResult<OrgVO> page(OrgQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Org> pageResult = orgRepository.selectPage(query);
        List<OrgVO> records = OrgConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public OrgVO getById(Long id) {
        Org org = orgRepository.selectById(id);
        if (org == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return OrgConvert.toVO(org);
    }

    @Override
    public List<Long> getDescendantOrgIds(Long orgId) {
        return orgRepository.selectDescendantIds(orgId);
    }

    @Override
    public Long create(OrgSaveDTO dto) {
        Org org = OrgConvert.toEntity(dto);
        org.setId(null);
        orgRepository.insert(org);
        return org.getId();
    }

    @Override
    public void update(Long id, OrgSaveDTO dto) {
        Org existing = orgRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Org org = OrgConvert.toEntity(dto);
        org.setId(id);
        orgRepository.updateById(org);
    }

    @Override
    public void delete(Long id) {
        Org existing = orgRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        orgRepository.deleteById(id);
    }
}
