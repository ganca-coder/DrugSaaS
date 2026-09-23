package com.adrug.erp.svc.bus.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 采购管理（采购订单、收货单、验收单、采购入库单）启动入口。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class PmsBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmsBootApplication.class, args);
    }
}
