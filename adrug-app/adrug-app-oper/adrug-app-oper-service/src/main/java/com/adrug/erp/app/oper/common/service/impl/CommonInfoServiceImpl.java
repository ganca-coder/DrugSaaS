package com.adrug.erp.app.oper.common.service.impl;

import com.adrug.erp.app.oper.common.service.CommonInfoService;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
import com.adrug.erp.svc.common.feign.MenuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单服务实现（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class CommonInfoServiceImpl implements CommonInfoService {

    @Autowired
    private MenuFeign menuFeign;

    @Override
    public Long createMenu(MenuSaveDTO dto) {
        return menuFeign.create(dto).getData();
    }

    @Override
    public void updateMenu(Long id, MenuSaveDTO dto) {
        menuFeign.update(id, dto);
    }

    @Override
    public void deleteMenu(Long id) {
        menuFeign.delete(id);
    }
}
