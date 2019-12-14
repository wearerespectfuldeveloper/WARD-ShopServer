package com.ward.wardshop.goods.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProductForm {

    @NotNull
    private Long categoryIdx;

    @NotNull
    private String name;

    private String desc;

    @NotNull
    private Integer price;

    private Integer stockQuantity;
}
