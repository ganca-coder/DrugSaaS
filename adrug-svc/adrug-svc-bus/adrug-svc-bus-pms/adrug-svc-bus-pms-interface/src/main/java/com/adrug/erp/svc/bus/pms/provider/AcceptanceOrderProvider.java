package com.adrug.erp.svc.bus.pms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.dto.AcceptanceOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.query.AcceptanceOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.AcceptanceOrderVO;

/**
 * 验收单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code AcceptanceOrderProviderImpl}。
 */
public interface AcceptanceOrderProvider {

    /**
     * 分页查询验收单。
     */
    PageResult<AcceptanceOrderVO> page(AcceptanceOrderQuery query);

    /**
     * 按 ID 查询验收单（含明细）。
     */
    AcceptanceOrderVO getById(Long id);

    /**
     * 新增验收单（含明细）。
     *
     * @return 新验收单主键
     */
    Long create(AcceptanceOrderSaveDTO dto);

    /**
     * 变更验收单（含明细）。
     */
    void update(Long id, AcceptanceOrderSaveDTO dto);

    /**
     * 删除验收单（逻辑删除，含明细）。
     */
    void delete(Long id);

    /**
     * 验收验收单（记录验收人/验收时间，验收后方可过账）。
     */
    void accept(Long id);

    /**
     * 过账验收单（状态：制单保存 → 制单完成）。
     * <p>
     * 过账前须已完成验收（acceptTime 非空）。
     */
    void post(Long id);
}
