package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.domain.product.Product;
import com.ward.wardshop.goods.repository.CategoryRepository;
import com.ward.wardshop.goods.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long create(ProductForm productForm) {
        //TODO 이미지 저장 기능 구현

        Category category = categoryRepository.findById(productForm.getCategoryIdx())
                .orElseThrow(() -> new IllegalArgumentException("Is not exists category."));

        Product newProduct = Product.builder()
                .name(productForm.getName())
                .description(productForm.getDescription())
                .price(productForm.getPrice())
                .stockQuantity(productForm.getStockQuantity())
                .category(category)
                .build();

        productRepository.save(newProduct);
        return newProduct.getIdx();
    }
}
