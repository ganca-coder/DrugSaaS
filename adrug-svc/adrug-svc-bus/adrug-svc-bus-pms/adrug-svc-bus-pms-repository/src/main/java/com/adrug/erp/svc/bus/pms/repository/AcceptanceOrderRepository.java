package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.entity.AcceptanceOrder;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.impl.AcceptanceOrderRepositoryImpl;

/**
 * 验收单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link AcceptanceOrderRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface AcceptanceOrderRepository {

    /**
     * 分页查询验收单。
     */
    PageResult<AcceptanceOrder> selectPage(AcceptanceOrderQuery query);

    /**
     * 按 ID 查询验收单。
     */
    AcceptanceOrder selectById(Long id);

    /**
     * 新增验收单（雪花主键回填到 {@code acceptanceOrder.id}）。
     */
    void insert(AcceptanceOrder acceptanceOrder);

    /**
     * 变更验收单。
     */
    void updateById(AcceptanceOrder acceptanceOrder);

    /**
     * 删除验收单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除验收单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
