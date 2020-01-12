package com.ward.wardshop.goods.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryLocationForm {
    private Long targetIdx;
    private Long destIdx;
    private Integer sequence;
}
