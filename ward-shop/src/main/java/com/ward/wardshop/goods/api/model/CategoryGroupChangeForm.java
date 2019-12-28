package com.ward.wardshop.goods.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryGroupChangeForm {
    private Long targetIdx;
    private Long preSiblingIdx;
}
