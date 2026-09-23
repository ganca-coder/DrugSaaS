package com.adrug.erp.common.db.helper;

import com.adrug.erp.common.context.UserContext;
import com.adrug.erp.common.context.UserContextHolder;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.List;

/**
 * 数据范围过滤助手。
 * <p>
 * 依据当前登录用户的数据范围（{@link UserContextHolder}）在查询上追加机构/创建人过滤：
 * <ul>
 *   <li>全部：不过滤</li>
 *   <li>自定义/本机构/本机构及下级：org_id IN (dataScopeOrgIds)</li>
 *   <li>仅本人：create_user_id = accountId</li>
 * </ul>
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public final class DataScopeHelper {

    private DataScopeHelper() {
    }

    /**
     * 在查询包装器上追加数据范围过滤。
     *
     * @param wrapper          查询包装器
     * @param orgIdFunc        机构 ID 列
     * @param createUserIdFunc 创建人列
     */
    public static <T> void apply(LambdaQueryWrapper<T> wrapper,
                                 SFunction<T, ?> orgIdFunc,
                                 SFunction<T, ?> createUserIdFunc) {
        Integer scope = UserContextHolder.getDataScope();
        if (scope == null || scope == DataScopeEnum.ALL.getCode()) {
            return;
        }
        UserContext context = UserContextHolder.get();
        if (scope == DataScopeEnum.SELF.getCode()) {
            Long accountId = context == null ? null : context.getAccountId();
            wrapper.eq(accountId != null, createUserIdFunc, accountId);
            return;
        }
        List<Long> orgIds = context == null ? null : context.getDataScopeOrgIds();
        if (orgIds != null && !orgIds.isEmpty()) {
            wrapper.in(orgIdFunc, orgIds);
        }
    }
}
