package com.adrug.erp.svc.user.auth.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.svc.user.auth.dto.RoleSaveDTO;
import com.adrug.erp.svc.user.auth.entity.Role;
import com.adrug.erp.svc.user.auth.enums.RoleStatusEnum;
import com.adrug.erp.svc.user.auth.vo.RoleVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色实体 &lt;-&gt; 传输对象 转换器。
 */
public final class RoleConvert {

    private RoleConvert() {
    }

    public static RoleVO toVO(Role role) {
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        if (role.getStatus() != null) {
            vo.setStatus(role.getStatus().getCode());
        }
        if (role.getDataScope() != null) {
            vo.setDataScope(role.getDataScope().getCode());
        }
        return vo;
    }

    public static List<RoleVO> toVOList(List<Role> roles) {
        return roles.stream().map(RoleConvert::toVO).collect(Collectors.toList());
    }

    public static Role toEntity(RoleSaveDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        role.setStatus(BaseEnum.ofCode(RoleStatusEnum.class, dto.getStatus()));
        role.setDataScope(BaseEnum.ofCode(DataScopeEnum.class, dto.getDataScope()));
        return role;
    }
}
