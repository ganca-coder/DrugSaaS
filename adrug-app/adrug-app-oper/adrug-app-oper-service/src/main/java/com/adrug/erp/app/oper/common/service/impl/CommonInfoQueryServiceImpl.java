package com.adrug.erp.app.oper.common.service.impl;

import com.adrug.erp.app.oper.common.service.CommonInfoQueryService;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.common.feign.MenuFeign;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单查询服务实现（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Service
public class CommonInfoQueryServiceImpl implements CommonInfoQueryService {

    @Autowired
    private MenuFeign menuFeign;

    @Override
    public List<MenuVO> getMenuTree() {
        return menuFeign.tree().getData();
    }

    @Override
    public PageResult<MenuVO> getMenuPage(MenuQuery query) {
        return menuFeign.page(query).getData();
    }

    @Override
    public MenuVO getMenuById(Long id) {
        return menuFeign.getById(id).getData();
    }
}
