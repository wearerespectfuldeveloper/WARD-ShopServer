package com.ward.wardshop.goods.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryQueryDto {
    private Long idx;
    private String name;
    private Integer sequence;
    private Long parentCategoryIdx;
}
