package com.ward.wardshop.client.domain;

import com.ward.wardshop.common.audit.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Validation 관련 애너테이션은 엔티티에 포함시키지 않도록 한다. 아래의 링크를 참조
 * https://jojoldu.tistory.com/129
 */
@Getter
@NoArgsConstructor
@Entity
public class WardMember extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 50)
    private String memberName;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority;

    private WardMember(String userId, String email, String password, String memberName, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.memberAuthority = MemberAuthority.ROLE_NON_AUTH_MEMBER;
    }

    private WardMember(Long idx, String userId, MemberAuthority memberAuthority) {
        this.idx = idx;
        this.userId = userId;
        this.memberAuthority = memberAuthority;
    }

    public static WardMember createNewWardMember(String userId, String email, String password, String memberName, String phoneNumber) {
        return new WardMember(userId, email, password, memberName, phoneNumber);
    }

    public static WardMember getEntityForAuth(Long idx, String userId, MemberAuthority memberAuthority) {
        return new WardMember(idx, userId, memberAuthority);
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public boolean passwordCheck(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memberAuthority.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
