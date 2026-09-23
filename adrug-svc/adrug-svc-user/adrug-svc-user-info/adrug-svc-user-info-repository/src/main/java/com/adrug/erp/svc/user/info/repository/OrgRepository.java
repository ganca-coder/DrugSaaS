package com.adrug.erp.svc.user.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.entity.Org;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.repository.impl.OrgRepositoryImpl;

import java.util.List;

/**
 * 机构数据访问接口（依赖倒置契约）。
 * <p>
 * 具体实现见 {@link OrgRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface OrgRepository {

    PageResult<Org> selectPage(OrgQuery query);

    Org selectById(Long id);

    List<Long> selectDescendantIds(Long orgId);

    void insert(Org org);

    void updateById(Org org);

    void deleteById(Long id);
}
