package com.ward.wardshop.goods.domain;

import com.ward.wardshop.common.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @Column(nullable = false)
    private Long group_idx;

    private Integer level;

    private Integer ordering;

    @Builder
    public Category(String name) {
        this.name = name;
    }
}
