package com.adrug.erp.svc.bus.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.bus.info.dto.DrugSaveDTO;
import com.adrug.erp.svc.bus.info.query.DrugQuery;
import com.adrug.erp.svc.bus.info.vo.DrugVO;

import java.util.Collection;
import java.util.List;

/**
 * 药品信息服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code DrugProviderImpl}。
 */
public interface DrugProvider {

    /**
     * 分页查询药品。
     */
    PageResult<DrugVO> page(DrugQuery query);

    /**
     * 按 ID 查询药品。
     */
    DrugVO getById(Long id);

    /**
     * 按 ID 集合批量查询药品。
     */
    List<DrugVO> getByIds(Collection<Long> ids);

    /**
     * 新增药品。
     *
     * @return 新药品主键
     */
    Long create(DrugSaveDTO dto);

    /**
     * 变更药品。
     */
    void update(Long id, DrugSaveDTO dto);

    /**
     * 删除药品（逻辑删除）。
     */
    void delete(Long id);

    /**
     * 物理删除药品（预留）。
     */
    void deletePhysically(Long id);
}
