package com.ward.wardshop.client.service;

import com.ward.wardshop.client.domain.EmailAuthKey;
import com.ward.wardshop.client.domain.WardMember;
import com.ward.wardshop.client.domain.dto.request.JoinRequest;
import com.ward.wardshop.client.repository.EmailAuthKeyRepository;
import com.ward.wardshop.client.repository.WardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WardMemberAuthService implements UserDetailsService {

    private final WardMemberRepository wardMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthKeyRepository emailAuthKeyRepository;

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

    private void generateEmailAuthKey(WardMember newMember) {
        EmailAuthKey emailAuthKey = EmailAuthKey.generateMemberEmailKey(newMember);
        emailAuthKeyRepository.save(emailAuthKey);
    }

    private Boolean isExistsUserId(WardMember newMember) {
        return wardMemberRepository.existsWardMemberByUserId(newMember.getUserId());
    }
}
