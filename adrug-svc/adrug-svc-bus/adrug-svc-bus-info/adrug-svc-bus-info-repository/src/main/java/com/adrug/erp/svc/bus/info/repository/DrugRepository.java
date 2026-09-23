package com.adrug.erp.svc.bus.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.entity.Drug;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.repository.impl.DrugRepositoryImpl;

import java.util.Collection;
import java.util.List;

/**
 * 药品信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link DrugRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface DrugRepository {

    /**
     * 分页查询药品。
     */
    PageResult<Drug> selectPage(DrugQuery query);

    /**
     * 按 ID 查询药品。
     */
    Drug selectById(Long id);

    /**
     * 按 ID 集合批量查询药品。
     */
    List<Drug> selectByIds(Collection<Long> ids);

    /**
     * 新增药品（雪花主键回填到 {@code drug.id}）。
     */
    void insert(Drug drug);

    /**
     * 变更药品。
     */
    void updateById(Drug drug);

    /**
     * 删除药品（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除药品（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
