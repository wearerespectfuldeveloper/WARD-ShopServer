package com.ward.wardshop.goods.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import com.ward.wardshop.goods.domain.product.ProductStatus;

@Getter
@NoArgsConstructor
public class ProductUpdateForm {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Integer price;

    private Integer stockQuantity;
    
    @NotNull
    private ProductStatus status;
}
