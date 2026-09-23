package com.adrug.erp.svc.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 通用服务启动模块入口（菜单管理等）。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class CommonBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonBootApplication.class, args);
    }
}
