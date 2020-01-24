package com.ward.wardshop.goods.service;

import com.ward.wardshop.common.module.ImageManager;
import com.ward.wardshop.common.module.impl.FilenameResolver;
import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.domain.product.Product;
import com.ward.wardshop.goods.repository.CategoryRepository;
import com.ward.wardshop.goods.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageManager imageManager;

    private static final String PRODUCT_PATH = "product/";

    @Transactional
    public Long create(ProductForm productForm, MultipartFile multipartFile) throws IOException {
        Category category = categoryRepository.findById(productForm.getCategoryIdx())
                .orElseThrow(() -> new IllegalArgumentException("Is not exists category."));

        Product newProduct = createProductEntityWithCategory(productForm, category);
        productRepository.save(newProduct);

        if (Objects.nonNull(multipartFile)) {
            saveImage(multipartFile);
        }

        return newProduct.getIdx();
    }

    private Product createProductEntityWithCategory(ProductForm productForm, Category category) {
        Product newProduct = productForm.toEntity();
        newProduct.changeCategory(category);

        return newProduct;
    }

    private void saveImage(MultipartFile uploadImage) throws IOException {
        imageManager.uploadImg(
                uploadImage,
                PRODUCT_PATH + FilenameResolver.generate(uploadImage.getOriginalFilename())
        );
    }
}
