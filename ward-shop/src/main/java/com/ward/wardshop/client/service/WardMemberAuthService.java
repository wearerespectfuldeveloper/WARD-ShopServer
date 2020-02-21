package com.ward.wardshop.client.service;

import com.ward.wardshop.client.domain.EmailAuthKey;
import com.ward.wardshop.client.domain.WardMember;
import com.ward.wardshop.client.domain.dto.request.JoinRequest;
import com.ward.wardshop.client.domain.dto.request.LoginRequest;
import com.ward.wardshop.client.repository.EmailAuthKeyRepository;
import com.ward.wardshop.client.repository.WardMemberRepository;
import com.ward.wardshop.common.module.token.JwtProvider;
import com.ward.wardshop.common.module.token.WardJwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WardMemberAuthService implements UserDetailsService {

    private final WardMemberRepository wardMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthKeyRepository emailAuthKeyRepository;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        WardMember member = wardMemberRepository.findByUserId(userId);

        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("유저의 이메일이 존재하지 않습니다.");
        }

        return member;
    }

    @Transactional
    public void joinMember(JoinRequest joinRequest) {
        WardMember newMember = joinRequest.toEntity();

        if (isExistsUserId(newMember)) {
            throw new EntityExistsException("이미 존재하는 아이디 입니다.");
        }

        newMember.encryptPassword(passwordEncoder);
        wardMemberRepository.save(newMember);
        generateEmailAuthKey(newMember);
    }

    @Transactional(readOnly = true)
    public WardJwtToken loginMember(LoginRequest loginRequest) {
        WardMember wardMember = wardMemberRepository.findByUserId(loginRequest.getUserId());

        if (Objects.isNull(wardMember)) {
            throw new EntityNotFoundException("유저 정보가 잘못되었습니다.");
        }

        if (!wardMember.passwordCheck(passwordEncoder, loginRequest.getPassword())) {
            return null;
        }

        return jwtProvider.generateToken(wardMember);
    }

    private void generateEmailAuthKey(WardMember newMember) {
        EmailAuthKey emailAuthKey = EmailAuthKey.generateMemberEmailKey(newMember);
        emailAuthKeyRepository.save(emailAuthKey);
    }

    private Boolean isExistsUserId(WardMember newMember) {
        return wardMemberRepository.existsWardMemberByUserId(newMember.getUserId());
    }
}
