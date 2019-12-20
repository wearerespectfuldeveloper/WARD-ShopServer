package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String createProduct(@Valid ProductForm productForm, MultipartFile imgFile) {
        Long productIdx = productService.create(productForm);

        return "aaa";
    }
}
