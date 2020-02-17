package com.ward.wardshop.goods.service;

import com.ward.wardshop.common.module.image.ImageManager;
import com.ward.wardshop.common.module.image.impl.FilenameResolver;
import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.api.model.ProductUpdateForm;
import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.domain.product.Product;
import com.ward.wardshop.goods.domain.product.ProductStatus;
import com.ward.wardshop.goods.repository.CategoryRepository;
import com.ward.wardshop.goods.repository.ProductRepository;
import com.ward.wardshop.goods.service.dto.ProductDto;
import com.ward.wardshop.goods.service.dto.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageManager imageManager;

    private static final String PRODUCT_PATH = "product/";

    @Transactional(readOnly = true)
    public List<ProductDto> getProductList(ProductRequest productRequest) {
        List<Product> products = productRepository.getProductByCreatedDateDesc(
                productRequest.getCategoryIdx(),
                productRequest.getCreatedDate()
        );

        return convertToDto(products);
    }

    private List<ProductDto> convertToDto(List<Product> products) {
        return products.stream()
                .map(p -> ProductDto.builder()
                        .idx(p.getIdx())
                        .name(p.getName())
                        .description(p.getDescription())
                        .imageResource(p.getImageResource())
                        .price(p.getPrice())
                        .createdDate(p.getCreatedDate())
                        .build())
                .collect(toList());
    }

    @Transactional
    public Long create(ProductForm productForm, MultipartFile multipartFile) throws IOException {
        Category category = categoryRepository.findById(productForm.getCategoryIdx())
                .orElseThrow(() -> new IllegalArgumentException("Is not exists category."));

        Product newProduct = createProductEntityWithCategory(productForm, category);

        if (Objects.nonNull(multipartFile)) {
            newProduct.createImageResource(saveImage(multipartFile));
        }

        productRepository.save(newProduct);
        return newProduct.getIdx();
    }

    @Transactional
    public Long update(Long idx,
                       ProductUpdateForm form,
                       MultipartFile multipartFile) throws IOException {
        Product updatedProduct = productRepository.findById(idx)
                .orElseThrow(EntityNotFoundException::new);

        updatedProduct.updateData(form);

        if (Objects.nonNull(multipartFile)) {
            deleteImage(updatedProduct);
            updatedProduct.createImageResource(saveImage(multipartFile));
        }

        return updatedProduct.getIdx();
    }

    @Transactional
    public void delete(Long idx) throws IOException {
        Product deletedProduct = productRepository.findById(idx)
                .orElseThrow(EntityNotFoundException::new);

        deleteImage(deletedProduct);
        productRepository.delete(deletedProduct);
    }

    @Transactional
    public Long setOnSale(Long idx) {
        Product product = productRepository.findById(idx).orElseThrow(EntityNotFoundException::new);

        product.changeStatus(ProductStatus.ON_SALE);
        return product.getIdx();
    }

    private Product createProductEntityWithCategory(ProductForm productForm, Category category) {
        Product newProduct = productForm.toEntity();
        newProduct.changeCategory(category);

        return newProduct;
    }

    private String saveImage(MultipartFile uploadImage) throws IOException {
        return imageManager.uploadImg(
                uploadImage,
                PRODUCT_PATH + FilenameResolver.generate(uploadImage.getOriginalFilename())
        );
    }

    private void deleteImage(Product product) throws IOException {
        if (Objects.nonNull(product.getImageResource())) {
            imageManager.deleteImg(product.getImageResource());
        }
    }
}
