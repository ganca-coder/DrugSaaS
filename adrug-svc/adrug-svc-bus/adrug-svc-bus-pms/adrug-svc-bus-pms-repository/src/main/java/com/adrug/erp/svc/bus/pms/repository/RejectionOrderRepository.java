package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.entity.RejectionOrder;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.impl.RejectionOrderRepositoryImpl;

/**
 * 拒收单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link RejectionOrderRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface RejectionOrderRepository {

    /**
     * 分页查询拒收单。
     */
    PageResult<RejectionOrder> selectPage(RejectionOrderQuery query);

    /**
     * 按 ID 查询拒收单。
     */
    RejectionOrder selectById(Long id);

    /**
     * 新增拒收单（雪花主键回填到 {@code rejectionOrder.id}）。
     */
    void insert(RejectionOrder rejectionOrder);

    /**
     * 变更拒收单。
     */
    void updateById(RejectionOrder rejectionOrder);

    /**
     * 删除拒收单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除拒收单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
