package com.adrug.erp.common.db.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.context.UserContextHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 通用配置。
 * <p>
 * 提供分页插件与审计字段自动填充，使 {@code BaseEntity} 上的
 * {@code @TableLogic} / {@code @TableField(fill = ...)} 注解生效。
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页插件（MySQL 方言）。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 审计字段自动填充。
     * <p>
     * 创建/更新人（createUserId / updateUserId）待接入登录上下文后再填充。
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                LocalDateTime now = LocalDateTime.now();
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
                // 租户/机构隔离字段：从身份上下文填充；缺省给 0
                this.strictInsertFill(metaObject, "tenantId", Long.class, TenantContextHolder.get());
                Long orgId = UserContextHolder.getOrgId();
                this.strictInsertFill(metaObject, "orgId", Long.class, orgId != null ? orgId : 0L);
                // 创建人：登录账号 ID（未登录时为空）
                this.strictInsertFill(metaObject, "createUserId", Long.class, UserContextHolder.getAccountId());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            }
        };
    }
}
