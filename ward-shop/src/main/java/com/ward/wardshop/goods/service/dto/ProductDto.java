package com.ward.wardshop.goods.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long idx;
    private String name;
    private String description;
    private Integer price;
    private String imageResource;
    private LocalDateTime createdDate;
}
