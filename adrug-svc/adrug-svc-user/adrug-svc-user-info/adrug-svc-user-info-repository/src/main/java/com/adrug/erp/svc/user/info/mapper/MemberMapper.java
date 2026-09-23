package com.adrug.erp.svc.user.info.mapper;

import com.adrug.erp.svc.user.info.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员信息 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL 预留 XML 方式实现，见 {@code resources/mapper/MemberMapper.xml}。
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 物理删除会员（预留）。
     *
     * @param id 会员主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
