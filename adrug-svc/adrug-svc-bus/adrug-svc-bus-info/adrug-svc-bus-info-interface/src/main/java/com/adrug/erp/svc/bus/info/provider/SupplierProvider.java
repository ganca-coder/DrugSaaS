package com.adrug.erp.svc.bus.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.dto.SupplierSaveDTO;
import com.adrug.erp.svc.bus.info.query.SupplierQuery;
import com.adrug.erp.svc.bus.info.vo.SupplierVO;

/**
 * 供应商信息服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code SupplierProviderImpl}。
 */
public interface SupplierProvider {

    /**
     * 分页查询供应商。
     */
    PageResult<SupplierVO> page(SupplierQuery query);

    /**
     * 按 ID 查询供应商（含证件信息）。
     */
    SupplierVO getById(Long id);

    /**
     * 新增供应商（含证件信息）。
     *
     * @return 新供应商主键
     */
    Long create(SupplierSaveDTO dto);

    /**
     * 变更供应商（含证件信息）。
     */
    void update(Long id, SupplierSaveDTO dto);

    /**
     * 删除供应商（逻辑删除，含证件信息）。
     */
    void delete(Long id);
}
