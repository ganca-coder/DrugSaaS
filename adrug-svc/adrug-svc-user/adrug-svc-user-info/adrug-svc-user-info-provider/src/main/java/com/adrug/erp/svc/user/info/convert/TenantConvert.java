package com.adrug.erp.svc.user.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.entity.Tenant;
import com.adrug.erp.svc.user.info.enums.TenantStatusEnum;
import com.adrug.erp.svc.user.info.vo.TenantVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户实体 &lt;-&gt; 传输对象 转换器。
 */
public final class TenantConvert {

    private TenantConvert() {
    }

    public static TenantVO toVO(Tenant tenant) {
        TenantVO vo = new TenantVO();
        BeanUtils.copyProperties(tenant, vo);
        if (tenant.getStatus() != null) {
            vo.setStatus(tenant.getStatus().getCode());
        }
        return vo;
    }

    public static List<TenantVO> toVOList(List<Tenant> tenants) {
        return tenants.stream().map(TenantConvert::toVO).collect(Collectors.toList());
    }

    public static Tenant toEntity(TenantSaveDTO dto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenant.setStatus(BaseEnum.ofCode(TenantStatusEnum.class, dto.getStatus()));
        return tenant;
    }
}
