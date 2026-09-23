package com.adrug.erp.svc.bus.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 订单管理（POS零售单）启动入口。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class OmsBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmsBootApplication.class, args);
    }
}
