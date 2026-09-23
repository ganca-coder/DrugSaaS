package com.adrug.erp.app.oper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动模块入口。
 */
@EnableFeignClients(basePackages = "com.adrug.erp")
@SpringBootApplication(scanBasePackages = "com.adrug.erp")
public class OperBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperBootApplication.class, args);
    }
}