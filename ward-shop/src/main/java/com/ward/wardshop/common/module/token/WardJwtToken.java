package com.ward.wardshop.common.module.token;

import com.ward.wardshop.client.domain.MemberAuthority;
import com.ward.wardshop.client.domain.WardMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static com.ward.wardshop.common.module.token.JwtConstants.*;

@Slf4j
@AllArgsConstructor
public class WardJwtToken {

    private String accessToken;
    private String jwtSecret;

    public Authentication getAuthentication() {
        Claims claims = getClaims();
        WardMember authEntity = getAuthEntity(claims);

        return new UsernamePasswordAuthenticationToken(authEntity, "", authEntity.getAuthorities());
    }

    public String getAccessToken() {
        return "Bearer " + accessToken;
    }

    private Claims getClaims() {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(accessToken)
                .getBody();
    }

    private WardMember getAuthEntity(Claims claims) {
        return WardMember.getEntityForAuth(
                claims.get(IDX, Long.class),
                claims.get(USER_ID, String.class),
                MemberAuthority.valueOf(claims.get(AUTH, String.class))
        );
    }
}
