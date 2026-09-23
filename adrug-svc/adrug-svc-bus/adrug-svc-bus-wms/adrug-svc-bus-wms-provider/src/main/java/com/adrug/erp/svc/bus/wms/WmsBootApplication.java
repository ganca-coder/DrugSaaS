package com.adrug.erp.svc.bus.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 仓库管理（库存）启动入口。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp", exclude = {DataSourceAutoConfiguration.class})
public class WmsBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsBootApplication.class, args);
    }
}
