package com.adrug.erp.app.term;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 终端接入启动入口（POS收银台、手机应用）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@EnableFeignClients(basePackages = "com.adrug.erp")
@SpringBootApplication(scanBasePackages = "com.adrug.erp")
public class TermBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TermBootApplication.class, args);
    }
}
