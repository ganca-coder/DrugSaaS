package com.adrug.erp.svc.user.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.TenantSaveDTO;
import com.adrug.erp.svc.user.info.query.TenantQuery;
import com.adrug.erp.svc.user.info.vo.TenantVO;

/**
 * 租户服务接口（依赖倒置契约）。
 */
public interface TenantProvider {

    /**
     * 分页查询租户。
     */
    PageResult<TenantVO> page(TenantQuery query);

    /**
     * 按 ID 查询租户。
     */
    TenantVO getById(Long id);

    /**
     * 新增租户。
     *
     * @return 新租户主键
     */
    Long create(TenantSaveDTO dto);

    /**
     * 变更租户。
     */
    void update(Long id, TenantSaveDTO dto);

    /**
     * 删除租户（逻辑删除）。
     */
    void delete(Long id);
}
