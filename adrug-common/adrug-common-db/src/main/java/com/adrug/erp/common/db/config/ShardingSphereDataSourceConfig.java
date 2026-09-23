package com.adrug.erp.common.db.config;

import org.apache.shardingsphere.driver.ShardingSphereDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * ShardingSphere 数据源配置。
 * <p>
 * 通过 {@link ShardingSphereDriver}（JDBC 驱动）加载 {@code classpath:shardingsphere.yaml}，
 * 对 MyBatis-Plus 暴露统一的 {@link DataSource}。当前包含：
 * <ul>
 *   <li>通用库 {@code adrug_common}（单库，不分租户）</li>
 *   <li>业务库 {@code adrug_bus_info_0/_1}（库级分租户，tenant_id % 2）</li>
 * </ul>
 */
@Configuration
public class ShardingSphereDataSourceConfig {

    @Bean
    @Primary
    public DataSource shardingSphereDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(ShardingSphereDriver.class.getName());
        dataSource.setUrl("jdbc:shardingsphere:classpath:shardingsphere.yaml");
        return dataSource;
    }
}
