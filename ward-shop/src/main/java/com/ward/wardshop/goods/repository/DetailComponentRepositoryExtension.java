package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.product.ProductDetailComponent;

import java.util.List;

public interface DetailComponentRepositoryExtension {
    List<ProductDetailComponent> findComponentsByProductIdx(Long productIdx);
}
