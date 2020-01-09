package com.ward.wardshop.goods.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ward.wardshop.goods.repository.CategoryRepositoryExtension;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.ward.wardshop.goods.domain.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryExtensionImpl implements CategoryRepositoryExtension {
    private final JPAQueryFactory query;

    @Override
    public Optional<Integer> findLastOrderingInRoot() {
        return Optional.ofNullable(
                query.select(category.sequence.max())
                        .from(category)
                        .where(category.parentCategory.isNull())
                        .fetchOne());
    }
}
