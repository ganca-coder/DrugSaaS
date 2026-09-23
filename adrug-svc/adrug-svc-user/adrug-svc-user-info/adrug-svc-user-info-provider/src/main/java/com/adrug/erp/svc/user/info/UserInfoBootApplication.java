package com.adrug.erp.svc.user.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动模块入口。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class UserInfoBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserInfoBootApplication.class, args);
    }
}