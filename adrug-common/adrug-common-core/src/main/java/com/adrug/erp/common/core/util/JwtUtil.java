package com.adrug.erp.common.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * JWT 工具类（签发与校验共用）。
 * <p>
 * 授权中心（adrug-svc-user-auth）用 {@link #generate} 签发，网关用 {@link #parse} 校验并透传身份。
 * HS256，携带 accountId(subject)/tenantId/orgId/username；角色、权限后续扩展。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public final class JwtUtil {

    // TODO 密钥应外置到配置中心，且长度需 ≥ 32 字节（HS256）
    private static final String SECRET = "adrug-erp-jwt-secret-2026-change-me-in-production-please";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /** token 有效期：2 小时 */
    private static final long EXPIRATION_MILLIS = 2 * 60 * 60 * 1000L;

    private JwtUtil() {
    }

    /**
     * 生成 token。
     */
    public static String generate(Long accountId, Long tenantId, Long orgId, String username,
                                  List<String> roles, List<String> permissions,
                                  Integer dataScope, List<Long> dataScopeOrgIds) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(accountId))
                .claim("tenantId", tenantId)
                .claim("orgId", orgId)
                .claim("username", username)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .claim("dataScope", dataScope)
                .claim("dataScopeOrgIds", dataScopeOrgIds)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + EXPIRATION_MILLIS))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析并校验 token，返回声明（失败抛异常）。
     */
    public static Claims parse(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
    }
}
