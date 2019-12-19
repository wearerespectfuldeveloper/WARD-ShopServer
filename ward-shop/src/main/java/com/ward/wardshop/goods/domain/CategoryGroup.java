package com.ward.wardshop.goods.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryGroup {

    private Long group_idx;
    private Integer level;
    private Integer ordering;

    public void changeGroup(CategoryGroup preSibling) {
        this.group_idx = preSibling.group_idx;
        this.level = preSibling.level;
        this.ordering = preSibling.ordering + 1;
    }
}
