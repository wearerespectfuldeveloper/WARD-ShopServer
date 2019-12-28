package com.ward.wardshop.goods.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class CategoryGroup {

    private Long group_idx;
    private Integer level;
    private Integer ordering;

    @Builder
    public CategoryGroup(Long group_idx, Integer level, Integer ordering) {
        this.group_idx = group_idx;
        this.level = level;
        this.ordering = ordering;
    }

    public void addOrder(int val) {
        this.ordering += val;
    }
}
