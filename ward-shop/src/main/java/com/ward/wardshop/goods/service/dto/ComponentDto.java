package com.ward.wardshop.goods.service.dto;

import com.ward.wardshop.goods.domain.product.ComponentType;
import com.ward.wardshop.goods.domain.product.ProductDetailComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ComponentDto {
    private Long idx;
    private ComponentType type;
    private String data;
    private Integer sequence;
}
