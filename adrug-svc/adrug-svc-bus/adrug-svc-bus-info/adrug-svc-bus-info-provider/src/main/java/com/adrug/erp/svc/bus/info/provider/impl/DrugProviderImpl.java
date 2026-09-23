package com.adrug.erp.svc.bus.info.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.bus.info.convert.DrugConvert;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.entity.Drug;
import com.adrug.erp.svc.bus.info.provider.DrugProvider;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.repository.DrugRepository;
import com.adrug.erp.svc.bus.info.vo.DrugVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 药品信息服务实现。
 */
@Service
public class DrugProviderImpl implements DrugProvider {

    @Autowired
    private DrugRepository drugRepository;

    @Override
    public PageResult<DrugVO> page(DrugQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Drug> pageResult = drugRepository.selectPage(query);
        List<DrugVO> records = DrugConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public DrugVO getById(Long id) {
        Drug drug = drugRepository.selectById(id);
        if (drug == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return DrugConvert.toVO(drug);
    }

    @Override
    public List<DrugVO> getByIds(Collection<Long> ids) {
        List<Drug> drugs = drugRepository.selectByIds(ids);
        return DrugConvert.toVOList(drugs);
    }

    @Override
    public Long create(DrugSaveDTO dto) {
        Drug drug = DrugConvert.toEntity(dto);
        drug.setId(null);
        drugRepository.insert(drug);
        return drug.getId();
    }

    @Override
    public void update(Long id, DrugSaveDTO dto) {
        Drug existing = drugRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Drug drug = DrugConvert.toEntity(dto);
        drug.setId(id);
        drugRepository.updateById(drug);
    }

    @Override
    public void delete(Long id) {
        Drug existing = drugRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // @TableLogic 逻辑删除
        drugRepository.deleteById(id);
    }

    @Override
    public void deletePhysically(Long id) {
        Drug existing = drugRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        drugRepository.deletePhysically(id);
    }
}
