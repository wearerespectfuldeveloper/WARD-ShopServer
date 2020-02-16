package com.ward.wardshop.client.repository;

import com.ward.wardshop.client.domain.WardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardMemberRepository
        extends JpaRepository<WardMember, Long> {
    WardMember findByUserId(String username);
}
