package com.ward.wardshop.goods.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductUpdateForm {

    @NotNull
    private Long productIdx;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Integer price;

    private Integer stockQuantity;
}
