package com.adrug.erp.svc.common.provider.impl;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.context.UserContext;
import com.adrug.erp.common.context.UserContextHolder;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.svc.common.convert.MenuConvert;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
import com.adrug.erp.svc.common.entity.Menu;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.repository.MenuRepository;
import com.adrug.erp.svc.common.provider.MenuProvider;
import com.adrug.erp.svc.common.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 菜单服务实现。
 */
@Service
public class MenuProviderImpl implements MenuProvider {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<MenuVO> tree() {
        Long tenantId = TenantContextHolder.get();
        // 默认菜单（tenant_id = 0）为底
        List<Menu> defaultMenus = menuRepository.selectByTenantId(0L);
        // 租户自定义菜单（tenant_id = 租户ID）
        List<Menu> customMenus = (tenantId != null && tenantId != 0)
                ? menuRepository.selectByTenantId(tenantId)
                : Collections.emptyList();

        List<MenuVO> defaultTree = MenuConvert.toTree(defaultMenus);
        List<MenuVO> customTree = MenuConvert.toTree(customMenus);
        // 默认菜单为底，租户自定义菜单按 menuCode 覆盖 / 合并
        List<MenuVO> merged = MenuConvert.mergeTree(defaultTree, customTree);
        // 按当前用户功能权限过滤
        UserContext context = UserContextHolder.get();
        List<String> permissions = context == null ? null : context.getPermissions();
        return MenuConvert.filterByPermissions(merged, permissions);
    }

    @Override
    public PageResult<MenuVO> page(MenuQuery query) {
        // 租户 ID 统一由请求头传递，服务端强制以上下文为准
        query.setTenantId(TenantContextHolder.get());
        PageResult<Menu> pageResult = menuRepository.selectPage(query);
        List<MenuVO> records = MenuConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public MenuVO getById(Long id) {
        Menu menu = menuRepository.selectById(id);
        if (menu == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return MenuConvert.toVO(menu);
    }

    @Override
    public Long create(MenuSaveDTO dto) {
        Menu menu = MenuConvert.toEntity(dto);
        menu.setId(null);
        menuRepository.insert(menu);
        return menu.getId();
    }

    @Override
    public void update(Long id, MenuSaveDTO dto) {
        Menu existing = menuRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Menu menu = MenuConvert.toEntity(dto);
        menu.setId(id);
        menuRepository.updateById(menu);
    }

    @Override
    public void delete(Long id) {
        Menu existing = menuRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // @TableLogic 逻辑删除
        menuRepository.deleteById(id);
    }

    @Override
    public void deletePhysically(Long id) {
        Menu existing = menuRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        menuRepository.deletePhysically(id);
    }
}
