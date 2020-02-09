package com.ward.wardshop.goods.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOptionForm {
    private String name;
    private Integer price;
    private Integer sequence;
}
