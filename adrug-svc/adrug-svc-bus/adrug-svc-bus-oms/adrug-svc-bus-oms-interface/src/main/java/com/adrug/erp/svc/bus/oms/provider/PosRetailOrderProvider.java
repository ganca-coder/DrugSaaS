package com.adrug.erp.svc.bus.oms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.oms.dto.PosRetailOrderSaveDTO;
import com.adrug.erp.svc.bus.oms.query.PosRetailOrderQuery;
import com.adrug.erp.svc.bus.oms.vo.PosRetailOrderVO;

/**
 * POS零售单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code PosRetailOrderProviderImpl}。
 */
public interface PosRetailOrderProvider {

    /**
     * 分页查询 POS 零售单。
     */
    PageResult<PosRetailOrderVO> page(PosRetailOrderQuery query);

    /**
     * 按 ID 查询 POS 零售单（含明细）。
     */
    PosRetailOrderVO getById(Long id);

    /**
     * 新增 POS 零售单（含明细）。
     *
     * @return 新 POS 零售单主键
     */
    Long create(PosRetailOrderSaveDTO dto);

    /**
     * 变更 POS 零售单（含明细）。
     */
    void update(Long id, PosRetailOrderSaveDTO dto);

    /**
     * 删除 POS 零售单（逻辑删除，含明细）。
     */
    void delete(Long id);
}
