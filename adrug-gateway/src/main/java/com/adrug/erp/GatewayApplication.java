package com.adrug.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关入口。
 * <p>
 * 仅扫描网关自身包（com.adrug.erp.gateway），避免加载 common-core 中面向 Servlet 的过滤器
 * （网关为 WebFlux 响应式技术栈）。
 */
@SpringBootApplication(scanBasePackages = "com.adrug.erp.gateway")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
