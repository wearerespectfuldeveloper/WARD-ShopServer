package com.ward.wardshop.goods.api.model;

import javax.validation.constraints.NotNull;

import com.ward.wardshop.goods.domain.product.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}
