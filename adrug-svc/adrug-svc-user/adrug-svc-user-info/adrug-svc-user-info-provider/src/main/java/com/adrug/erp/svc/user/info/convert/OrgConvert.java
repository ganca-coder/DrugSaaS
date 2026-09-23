package com.adrug.erp.svc.user.info.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.entity.Org;
import com.adrug.erp.svc.user.info.enums.OrgStatusEnum;
import com.adrug.erp.svc.user.info.enums.OrgTypeEnum;
import com.adrug.erp.svc.user.info.vo.OrgVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 机构实体 &lt;-&gt; 传输对象 转换器。
 */
public final class OrgConvert {

    private OrgConvert() {
    }

    public static OrgVO toVO(Org org) {
        OrgVO vo = new OrgVO();
        BeanUtils.copyProperties(org, vo);
        if (org.getOrgType() != null) {
            vo.setOrgType(org.getOrgType().getCode());
        }
        if (org.getStatus() != null) {
            vo.setStatus(org.getStatus().getCode());
        }
        return vo;
    }

    public static List<OrgVO> toVOList(List<Org> orgs) {
        return orgs.stream().map(OrgConvert::toVO).collect(Collectors.toList());
    }

    public static Org toEntity(OrgSaveDTO dto) {
        Org org = new Org();
        BeanUtils.copyProperties(dto, org);
        org.setOrgType(BaseEnum.ofCode(OrgTypeEnum.class, dto.getOrgType()));
        org.setStatus(BaseEnum.ofCode(OrgStatusEnum.class, dto.getStatus()));
        return org;
    }
}
