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
                        getProductSearchCondition(categoryIdx, createdDate)
                )
                .orderBy(product.createdDate.desc())
                .limit(20L)
                .fetch();
    }

    private Predicate[] getProductSearchCondition(Long categoryIdx, LocalDateTime createdDate) {

        List<Predicate> predicates = Arrays.asList(
                product.category.idx.eq(categoryIdx),
                product.productStatus.eq(ProductStatus.ON_SALE)
        );

        if (Objects.nonNull(createdDate)) {
            predicates.add(product.createdDate.before(createdDate));
        }

        return (Predicate[]) predicates.toArray();
    }
}
