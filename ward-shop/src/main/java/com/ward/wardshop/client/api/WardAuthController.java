package com.ward.wardshop.client.api;

import com.ward.wardshop.client.domain.dto.request.JoinRequest;
import com.ward.wardshop.client.service.WardMemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class WardAuthController {

    private final WardMemberAuthService wardMemberAuthService;

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(JoinRequest joinRequest) {
        wardMemberAuthService.joinMember(joinRequest);

        return ResponseEntity.ok("success");
    }
}
