package com.adrug.erp.svc.bus.pms.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.pms.dto.RejectionOrderSaveDTO;
import com.adrug.erp.svc.bus.pms.query.RejectionOrderQuery;
import com.adrug.erp.svc.bus.pms.vo.RejectionOrderVO;

/**
 * 拒收单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code RejectionOrderProviderImpl}。
 */
public interface RejectionOrderProvider {

    /**
     * 分页查询拒收单。
     */
    PageResult<RejectionOrderVO> page(RejectionOrderQuery query);

    /**
     * 按 ID 查询拒收单（含明细）。
     */
    RejectionOrderVO getById(Long id);

    /**
     * 新增拒收单（含明细）。
     *
     * @return 新拒收单主键
     */
    Long create(RejectionOrderSaveDTO dto);

    /**
     * 变更拒收单（含明细）。
     */
    void update(Long id, RejectionOrderSaveDTO dto);

    /**
     * 删除拒收单（逻辑删除，含明细）。
     */
    void delete(Long id);

    /**
     * 过账拒收单（状态：制单保存 → 制单完成）。
     */
    void post(Long id);
}
