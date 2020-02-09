package com.ward.wardshop.goods.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ward.wardshop.goods.domain.product.ProductDetailComponent;
import com.ward.wardshop.goods.repository.DetailComponentRepositoryExtension;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ward.wardshop.goods.domain.product.QProductDetailComponent.productDetailComponent;

@RequiredArgsConstructor
public class DetailComponentRepositoryExtensionImpl implements DetailComponentRepositoryExtension {

    private JPAQueryFactory query;

    @Override
    public List<ProductDetailComponent> findComponentsByProductIdx(Long productIdx) {
        return query.selectFrom(productDetailComponent)
                .where(productDetailComponent.product.idx.eq(productIdx))
                .orderBy(productDetailComponent.sequence.asc())
                .fetch();
    }
}
