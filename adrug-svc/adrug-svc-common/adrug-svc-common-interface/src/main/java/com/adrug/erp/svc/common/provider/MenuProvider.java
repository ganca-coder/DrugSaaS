package com.adrug.erp.svc.common.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.vo.MenuVO;

import java.util.List;

/**
 * 菜单服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code MenuProviderImpl}。
 * <p>
 * 核心方法 {@link #tree(Long)} 返回「租户生效菜单树」：默认菜单（tenant_id=0）为底，
 * 租户自定义菜单（tenant_id=租户ID）按 {@code menuCode} 覆盖同名项，租户新增项合并进来。
 */
public interface MenuProvider {

    /**
     * 查询当前租户生效菜单树。
     * <p>
     * 先取默认菜单（tenant_id=0），再用租户自定义菜单按 menu_code 覆盖；
     * 租户 ID 从请求头上下文（{@code X-Tenant-Id}）读取，为 0 时仅返回默认菜单。
     *
     * @return 生效菜单树（仅根节点列表，子节点挂在 children）
     */
    List<MenuVO> tree();

    /**
     * 分页查询菜单。
     */
    PageResult<MenuVO> page(MenuQuery query);

    /**
     * 按 ID 查询菜单。
     */
    MenuVO getById(Long id);

    /**
     * 新增菜单。
     *
     * @return 新菜单主键
     */
    Long create(MenuSaveDTO dto);

    /**
     * 变更菜单。
     */
    void update(Long id, MenuSaveDTO dto);

    /**
     * 删除菜单（逻辑删除）。
     */
    void delete(Long id);

    /**
     * 物理删除菜单（预留）。
     * <p>
     * 真正从数据库移除记录，仅用于特殊清理场景；业务常规删除请使用逻辑删除。
     */
    void deletePhysically(Long id);
}
