package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.api.model.ProductUpdateForm;
import com.ward.wardshop.goods.service.ProductService;
import com.ward.wardshop.goods.service.dto.ProductDto;
import com.ward.wardshop.goods.service.dto.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProduct(ProductRequest productRequest) {
        return productService.getProductList(productRequest);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long createProduct(@Valid ProductForm productForm, MultipartFile imgFile) throws IOException {

        return productService.create(productForm, imgFile);
    }

    @PutMapping(value = "/{idx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long updateProduct(
            @PathVariable Long idx,
            @Valid ProductUpdateForm form,
            MultipartFile imgFile) throws IOException {
        return productService.update(idx, form, imgFile);
    }

    @DeleteMapping("/{idx}")
    public void deleteProduct(@PathVariable Long idx) throws IOException {
        productService.delete(idx);
    }
}
