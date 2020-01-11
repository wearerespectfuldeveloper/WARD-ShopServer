package com.ward.wardshop.goods.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CategoryDto {
    private Long idx;
    private String name;
    private List<CategoryDto> childCategories;
}
