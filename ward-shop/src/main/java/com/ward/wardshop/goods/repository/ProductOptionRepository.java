package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
