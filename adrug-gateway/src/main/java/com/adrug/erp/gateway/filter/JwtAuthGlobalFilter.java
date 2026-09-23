package com.adrug.erp.gateway.filter;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.context.UserContextHolder;
import com.adrug.erp.common.core.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 网关 JWT 全局过滤器。
 * <p>
 * 解析请求头 {@code Authorization: Bearer <token>}，校验通过后把身份信息
 * （accountId/tenantId/orgId/username/roles/permissions/dataScope）注入下游请求头，传给 app 层。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // 白名单：登录接口
        if (path.endsWith("/auth/login")) {
            return chain.filter(exchange);
        }

        String auth = request.getHeaders().getFirst("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return unauthorized(exchange, "未登录");
        }

        try {
            Claims claims = JwtUtil.parse(auth.substring(7));
            Object tenantId = claims.get("tenantId");
            Object orgId = claims.get("orgId");
            Object username = claims.get("username");
            Object dataScope = claims.get("dataScope");

            ServerHttpRequest.Builder builder = request.mutate();
            if (tenantId != null) {
                builder.header(TenantContextHolder.HEADER, String.valueOf(tenantId));
            }
            builder.header(UserContextHolder.ACCOUNT_ID_HEADER, claims.getSubject());
            if (orgId != null) {
                builder.header(UserContextHolder.ORG_ID_HEADER, String.valueOf(orgId));
            }
            if (username != null) {
                builder.header(UserContextHolder.USERNAME_HEADER, String.valueOf(username));
            }
            builder.header(UserContextHolder.ROLES_HEADER, join(claims.get("roles")));
            builder.header(UserContextHolder.PERMISSIONS_HEADER, join(claims.get("permissions")));
            if (dataScope != null) {
                builder.header(UserContextHolder.DATA_SCOPE_HEADER, String.valueOf(dataScope));
            }
            builder.header(UserContextHolder.DATA_SCOPE_ORGS_HEADER, join(claims.get("dataScopeOrgIds")));

            return chain.filter(exchange.mutate().request(builder.build()).build());
        } catch (Exception e) {
            return unauthorized(exchange, "token 无效或已过期");
        }
    }

    private String join(Object value) {
        if (value instanceof Collection<?> c) {
            return c.stream().map(String::valueOf).collect(Collectors.joining(","));
        }
        return value == null ? "" : String.valueOf(value);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"code\":401,\"message\":\"" + message + "\",\"data\":null}";
        return response.writeWith(Mono.fromSupplier(
                () -> response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
