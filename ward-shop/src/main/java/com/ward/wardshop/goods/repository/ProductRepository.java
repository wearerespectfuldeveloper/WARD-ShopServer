package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
