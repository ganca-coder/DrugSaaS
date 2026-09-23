package com.adrug.erp.svc.user.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.OrgSaveDTO;
import com.adrug.erp.svc.user.info.query.OrgQuery;
import com.adrug.erp.svc.user.info.vo.OrgVO;

import java.util.List;

/**
 * 机构服务接口（依赖倒置契约）。
 */
public interface OrgProvider {

    /**
     * 分页查询机构。
     */
    PageResult<OrgVO> page(OrgQuery query);

    /**
     * 按 ID 查询机构。
     */
    OrgVO getById(Long id);

    /**
     * 查询机构及所有下级机构 ID（含自身）。
     */
    List<Long> getDescendantOrgIds(Long orgId);

    /**
     * 新增机构。
     *
     * @return 新机构主键
     */
    Long create(OrgSaveDTO dto);

    /**
     * 变更机构。
     */
    void update(Long id, OrgSaveDTO dto);

    /**
     * 删除机构（逻辑删除）。
     */
    void delete(Long id);
}
