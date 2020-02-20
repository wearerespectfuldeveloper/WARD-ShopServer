package com.ward.wardshop.client.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@NoArgsConstructor
@Entity
public class EmailAuthKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "wardMemberIdx")
    private WardMember wardMember;

    @Column(nullable = false, length = 20)
    private String key;

    @CreatedDate
    private LocalDateTime createdDate;

    private EmailAuthKey(WardMember wardMember, String key) {
        this.wardMember = wardMember;
        this.key = key;
    }

    public static EmailAuthKey generateMemberEmailKey(WardMember wardMember) {
        return new EmailAuthKey(wardMember, createRandomKey());
    }

    private static String createRandomKey() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(3);

            switch (randomIndex) {
                case 0:
                    //a - z
                    builder.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    //A - Z
                    builder.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    //0 ~ 9
                    builder.append(random.nextInt(10));
                    break;
            }
        }

        return builder.toString();
    }
}
