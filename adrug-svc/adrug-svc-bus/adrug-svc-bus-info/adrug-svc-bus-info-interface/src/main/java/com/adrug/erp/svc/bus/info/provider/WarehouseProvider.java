package com.adrug.erp.svc.bus.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.dto.WarehouseSaveDTO;
import com.adrug.erp.svc.bus.info.query.WarehouseQuery;
import com.adrug.erp.svc.bus.info.vo.WarehouseVO;

/**
 * 仓库信息服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code WarehouseProviderImpl}。
 */
public interface WarehouseProvider {

    /**
     * 分页查询仓库。
     */
    PageResult<WarehouseVO> page(WarehouseQuery query);

    /**
     * 按 ID 查询仓库。
     */
    WarehouseVO getById(Long id);

    /**
     * 新增仓库。
     *
     * @return 新仓库主键
     */
    Long create(WarehouseSaveDTO dto);

    /**
     * 变更仓库。
     */
    void update(Long id, WarehouseSaveDTO dto);

    /**
     * 删除仓库（逻辑删除）。
     */
    void delete(Long id);
}
