package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
        extends JpaRepository<Product, Long>, ProductRepositoryExtension {

    @Query("select p from Product p left join fetch p.options o where p.idx = :productId order by o.sequence")
    Product findProductByIdFetchJoinOptions(Long productId);
}
