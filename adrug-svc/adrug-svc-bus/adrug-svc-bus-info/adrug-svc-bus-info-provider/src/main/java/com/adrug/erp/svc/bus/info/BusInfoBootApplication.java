package com.adrug.erp.svc.bus.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 基础信息（药品信息）启动入口。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class BusInfoBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusInfoBootApplication.class, args);
    }
}
