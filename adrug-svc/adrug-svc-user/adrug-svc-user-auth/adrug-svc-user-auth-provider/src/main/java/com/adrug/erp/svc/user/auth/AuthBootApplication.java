package com.adrug.erp.svc.user.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 授权中心启动模块入口。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class AuthBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthBootApplication.class, args);
    }
}
