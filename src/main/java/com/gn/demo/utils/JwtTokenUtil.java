package com.gn.demo.utils;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/8/2 11:16
 */

import com.gn.demo.config.security.constant.Constant;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Data
@RefreshScope
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.secretKey:abc123}")
    private String secretKey;

    @Value("${jwt.accessTokenExpireTime:PT2H}")
    private Duration accessTokenExpireTime;

    @Value("${jwt.refreshTokenExpireTime:PT8H}")
    private Duration refreshTokenExpireTime;

    @Value("${jwt.refreshTokenExpireAppTime:P30D}")
    private Duration refreshTokenExpireAppTime;

    @Value("${jwt.issuer:_gn_}")
    private String issuer;


    /**
     * 生成 access_token
     *
     * @param subject subject
     * @param claims  claims
     * @return String
     */
    public String getAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 签发 token
     *
     * @param issuer
     * @param subject
     * @param claims
     * @param ttlMillis
     * @param secret
     * @return
     */
    private String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMillis, String secret) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");
        if (null != claims) {
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        builder.setIssuedAt(now);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        builder.signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    //上面已经有生成 access_token 的方法了，下面加入生成 refresh_token 的方法（PC 端过期时间短一些）

    /**
     * 生成 PC refresh_token
     *
     * @param subject
     * @param claims
     * @return
     */
    public String getRefreshToken(String subject, Map<String, Object> claims) {
        log.debug("getRefreshToken start subject:[{}], claims:[{}]", subject, claims);
        return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 生成 app 端 refresh_token
     *
     * @param subject
     * @param claims
     * @return
     */
    public String getRefreshAppToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, refreshTokenExpireAppTime.toMillis(), secretKey);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            if (e instanceof ClaimJwtException) {
                claims = ((ClaimJwtException) e).getClaims();
            }
            log.error("getClaimsFromToken.error:", e);
        }
        return claims;
    }

    /**
     * 获取用户id
     *
     * @param token
     * @return
     */
    public String getUserId(String token) {
        String userId = null;

        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            log.error("getClaimsFromToken.error:", e);
        }
        return userId;
    }

    public String getUserName(String token) {
        String username = null;

        try {
            Claims claims = getClaimsFromToken(token);
            username = (String) claims.get(Constant.JWT_USER_NAME);
        } catch (Exception e) {
            log.error("getUserName.error:", e);
        }
        return username;
    }

    /**
     * 验证token是否过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("isTokenExpired.error:", e);
            return true;
        }
    }

    /**
     * 校验令牌
     *
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (null != claimsFromToken && !isTokenExpired(token));
    }


    public String refreshToken(String refreshToken, Map<String, Object> claims) {
        String refreshedToken;
        try {
            //刷新token时如果为空，说明原 用户信息没变，所以直接引用原token里的内容
            Claims parserClaims = getClaimsFromToken(refreshToken);
            if (null == claims) {
                claims = parserClaims;
            }
            refreshedToken = generateToken(parserClaims.getIssuer(), parserClaims.getSubject(), claims, accessTokenExpireTime.toMillis(), secretKey);
        } catch (Exception e) {
            refreshedToken = null;
            log.error("refreshToken.error:", e);
        }
        return refreshedToken;
    }

    /**
     * 获取token的剩余过期时间
     * @param token
     * @return
     */
    public long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration().getTime() - nowMillis;
        } catch (Exception e) {
            log.error("getRemainingTime.error:", e);
        }
        return result;
    }
}


