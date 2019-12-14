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

    private String description;

    @NotNull
    private Integer price;

    private Integer stockQuantity;

    public ProductForm(@NotNull Long categoryIdx, @NotNull String name, String description, @NotNull Integer price, Integer stockQuantity) {
        this.categoryIdx = categoryIdx;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
