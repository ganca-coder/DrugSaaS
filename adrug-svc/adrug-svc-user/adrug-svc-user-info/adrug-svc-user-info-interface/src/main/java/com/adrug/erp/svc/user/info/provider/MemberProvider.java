package com.adrug.erp.svc.user.info.provider;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.dto.MemberSaveDTO;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.vo.MemberVO;

/**
 * 会员信息服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 provider 模块 {@code MemberProviderImpl}。
 */
public interface MemberProvider {

    /**
     * 分页查询会员。
     */
    PageResult<MemberVO> page(MemberQuery query);

    /**
     * 按 ID 查询会员。
     */
    MemberVO getById(Long id);

    /**
     * 新增会员。
     *
     * @return 新会员主键
     */
    Long create(MemberSaveDTO dto);

    /**
     * 变更会员。
     */
    void update(Long id, MemberSaveDTO dto);

    /**
     * 删除会员（逻辑删除）。
     */
    void delete(Long id);

    /**
     * 物理删除会员（预留）。
     */
    void deletePhysically(Long id);
}
