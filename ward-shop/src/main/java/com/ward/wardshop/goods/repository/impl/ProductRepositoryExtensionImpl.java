package com.ward.wardshop.goods.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ward.wardshop.goods.domain.product.Product;
import com.ward.wardshop.goods.domain.product.ProductStatus;
import com.ward.wardshop.goods.repository.ProductRepositoryExtension;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ward.wardshop.goods.domain.product.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryExtensionImpl implements ProductRepositoryExtension {

    private final JPAQueryFactory query;

    @Override
    public List<Product> getProductByCreatedDateDesc(Long categoryIdx, LocalDateTime createdDate) {
        return query.selectFrom(product)
                .where(
                        eqCategoryIdx(categoryIdx),
                        beforeCreatedDate(createdDate),
                        product.productStatus.eq(ProductStatus.ON_SALE)
                )
                .orderBy(product.createdDate.desc())
                .limit(20L)
                .fetch();
    }

    private Predicate eqCategoryIdx(Long categoryIdx) {
        if (Objects.isNull(categoryIdx)) {
            return null;
        }

        return product.category.idx.eq(categoryIdx);
    }

    private Predicate beforeCreatedDate(LocalDateTime createdDate) {
        if (Objects.isNull(createdDate)) {
            return null;
        }

        return product.createdDate.before(createdDate);
    }
}
