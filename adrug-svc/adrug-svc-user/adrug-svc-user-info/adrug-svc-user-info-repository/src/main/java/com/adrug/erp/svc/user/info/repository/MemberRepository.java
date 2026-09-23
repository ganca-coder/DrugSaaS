package com.adrug.erp.svc.user.info.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.user.info.entity.Member;
import com.adrug.erp.svc.user.info.query.MemberQuery;
import com.adrug.erp.svc.user.info.repository.impl.MemberRepositoryImpl;

/**
 * 会员信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link MemberRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface MemberRepository {

    /**
     * 分页查询会员。
     */
    PageResult<Member> selectPage(MemberQuery query);

    /**
     * 按 ID 查询会员。
     */
    Member selectById(Long id);

    /**
     * 新增会员（雪花主键回填到 {@code member.id}）。
     */
    void insert(Member member);

    /**
     * 变更会员。
     */
    void updateById(Member member);

    /**
     * 删除会员（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除会员（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
