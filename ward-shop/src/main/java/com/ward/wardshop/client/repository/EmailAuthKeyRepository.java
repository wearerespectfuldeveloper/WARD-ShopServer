package com.ward.wardshop.client.repository;

import com.ward.wardshop.client.domain.EmailAuthKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthKeyRepository extends JpaRepository<EmailAuthKey, Long> {
}
