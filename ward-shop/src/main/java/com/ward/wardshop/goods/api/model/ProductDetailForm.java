package com.ward.wardshop.goods.api.model;

import javax.validation.constraints.NotNull;

import com.ward.wardshop.goods.domain.product.ProductDetailComponent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDetailForm {

    @NotNull
    private Long productIdx;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Integer price;

    private Integer stockQuantity;
    
    public ProductDetailForm(@NotNull Long productIdx, @NotNull String name, String description, @NotNull Integer price, Integer stockQuantity) {
        this.productIdx = productIdx;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public ProductDetailComponent toEntity() {
        return ProductDetailComponent.builder()
                .data(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}
