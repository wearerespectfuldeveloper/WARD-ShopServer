package com.ward.wardshop.common.module.token;

import com.ward.wardshop.client.domain.WardMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
        Claims claims = createClaims(wardMember);
        return generateToken(claims);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Claims createClaims(WardMember wardMember) {
        Claims claims = Jwts.claims().setSubject(JwtConstants.SUB);
        claims.put(JwtConstants.IDX, wardMember.getIdx());
        claims.put(JwtConstants.USER_ID, wardMember.getUserId());
        claims.put(JwtConstants.AUTH, wardMember.getMemberAuthority());

        return claims;
    }

    private String generateToken(Claims claims) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
