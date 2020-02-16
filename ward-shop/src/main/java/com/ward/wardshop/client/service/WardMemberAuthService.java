package com.ward.wardshop.client.service;

import com.ward.wardshop.client.domain.WardMember;
import com.ward.wardshop.client.repository.WardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WardMemberAuthService implements UserDetailsService {

    private final WardMemberRepository wardMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        WardMember member = wardMemberRepository.findByUserId(userId);

        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("유저의 이메일이 존재하지 않습니다.");
        }

        return member;
    }
}
