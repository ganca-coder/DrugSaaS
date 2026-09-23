package com.adrug.erp.svc.bus.pms.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.entity.ReceivingOrder;
import com.adrug.erp.svc.bus.pms.query.ReceivingOrderQuery;
import com.adrug.erp.svc.bus.pms.repository.impl.ReceivingOrderRepositoryImpl;

/**
 * 收货单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link ReceivingOrderRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface ReceivingOrderRepository {

    /**
     * 分页查询收货单。
     */
    PageResult<ReceivingOrder> selectPage(ReceivingOrderQuery query);

    /**
     * 按 ID 查询收货单。
     */
    ReceivingOrder selectById(Long id);

    /**
     * 新增收货单（雪花主键回填到 {@code receivingOrder.id}）。
     */
    void insert(ReceivingOrder receivingOrder);

    /**
     * 变更收货单。
     */
    void updateById(ReceivingOrder receivingOrder);

    /**
     * 删除收货单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除收货单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
