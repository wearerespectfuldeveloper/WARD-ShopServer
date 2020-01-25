package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.api.model.ProductUpdateForm;
import com.ward.wardshop.goods.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long createProduct(@Valid ProductForm productForm, MultipartFile imgFile) throws IOException {

        return productService.create(productForm, imgFile);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long updateProduct(@Valid ProductUpdateForm form, MultipartFile imgFile) throws IOException {
        return productService.update(form, imgFile);
    }
}
