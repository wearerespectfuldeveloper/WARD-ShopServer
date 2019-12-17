package com.ward.wardshop.goods.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryForm {
    private String categoryName;
    private Long groupIdx;
    private Integer level;
    private Integer ordering;
}
