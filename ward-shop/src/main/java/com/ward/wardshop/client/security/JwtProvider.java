package com.ward.wardshop.client.security;

import com.ward.wardshop.client.domain.WardMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @PostConstruct
    protected void encodingSecretKey() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    public String generateToken(WardMember wardMember) {
        Claims claims = Jwts.claims().setSubject(JwtSpec.SUB);
        claims.put(JwtSpec.IDX, wardMember.getIdx());
        claims.put(JwtSpec.USER_ID, wardMember.getUserId());
        claims.put(JwtSpec.EMAIL, wardMember.getEmail());
        claims.put(JwtSpec.AUTH, wardMember.getAuthorities());

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private static class JwtSpec {
        private static String SUB = "wardMember";
        private static String IDX = "idx";
        private static String USER_ID = "userId";
        private static String EMAIL = "email";
        private static String AUTH = "auth";
    }
}
