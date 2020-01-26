package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepositoryExtension {
    List<Product> getProductByCreatedDateDesc(Long categoryIdx, LocalDateTime createdDate);
}
