package com.ward.wardshop.goods.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@ToString(exclude = {"imgFile"})
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

    private MultipartFile imgFile;
}
