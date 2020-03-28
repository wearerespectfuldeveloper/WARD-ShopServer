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

    private String description; //todo ProductDetail 의 컴포넌트는 description 이 필요없어 보여요!

    @NotNull
    private Integer price;      //todo 위의 description 과 마찬가지로 price 가 필요없어 보여요!

    private Integer stockQuantity;  //todo 위의 description 과 마찬가지로 stockQuantity 는 필요없어 보여요!
    
    public ProductDetailForm(@NotNull Long productIdx, @NotNull String name, String description, @NotNull Integer price, Integer stockQuantity) {
        this.productIdx = productIdx;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /*
     *  //todo ProductDetailComponent 엔티티에는 description 과 price 필드가 필요 없을거라는 생각이 듭니다. :)
     */
    public ProductDetailComponent toEntity() {
        return ProductDetailComponent.builder()
                .data(name)
                .build();
    }

/*
    public ProductDetailComponent toEntity() {
        return ProductDetailComponent.builder()
                .data(name)
                .sequence(sequence)
                .build();
    }
*/
}
