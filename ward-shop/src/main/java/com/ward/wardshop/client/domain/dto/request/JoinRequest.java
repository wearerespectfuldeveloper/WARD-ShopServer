package com.ward.wardshop.client.domain.dto.request;

import com.ward.wardshop.client.domain.WardMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank
    private String userId;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String memberName;

    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String phoneNumber;

    public WardMember toEntity() {
        return WardMember.createNewWardMember(userId, email, password, memberName, phoneNumber);
    }
}
