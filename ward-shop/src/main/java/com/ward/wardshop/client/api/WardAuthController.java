package com.ward.wardshop.client.api;

import com.ward.wardshop.client.domain.dto.request.JoinRequest;
import com.ward.wardshop.client.domain.dto.request.LoginRequest;
import com.ward.wardshop.client.service.WardMemberAuthService;
import com.ward.wardshop.common.module.token.WardJwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class WardAuthController {

    private final WardMemberAuthService wardMemberAuthService;

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(@RequestBody JoinRequest joinRequest) {
        wardMemberAuthService.joinMember(joinRequest);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response, @RequestBody LoginRequest loginRequest) {
        WardJwtToken wardJwtToken = wardMemberAuthService.loginMember(loginRequest);

        response.addHeader("Authorization", wardJwtToken.getAccessToken());
        return ResponseEntity.ok("success");
    }
}
