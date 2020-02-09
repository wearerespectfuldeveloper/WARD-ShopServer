package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.service.ProductDetailComponentService;
import com.ward.wardshop.goods.service.dto.ComponentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductDetailComponentController {

    private final ProductDetailComponentService productDetailComponentService;

    @GetMapping("/product/{productIdx}/components")
    public List<ComponentDto> getComponents(@PathVariable Long productIdx) {
        return productDetailComponentService.getProductDetailComponents(productIdx);
    }
}
