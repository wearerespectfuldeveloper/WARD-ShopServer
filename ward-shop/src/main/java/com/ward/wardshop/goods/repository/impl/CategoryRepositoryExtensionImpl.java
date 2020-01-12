package com.ward.wardshop.goods.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepositoryExtension;
import lombok.RequiredArgsConstructor;

import java.util.List;
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

    @Override
    public List<CategoryQueryDto> findAllQueryDto() {
        return query.select(
                Projections.constructor(
                        CategoryQueryDto.class,
                        category.idx,
                        category.name,
                        category.sequence,
                        category.parentCategory.idx
                ))
                .from(category)
                .fetch();
    }

    @Override
    public Category findCategoryByIdxFetchChildren(Long idx) {
        return query.selectFrom(category)
                .leftJoin(category.childCategories).fetchJoin()
                .where(category.idx.eq(idx))
                .fetchOne();
    }

    @Override
    public Category findCategoryByIdxFetchParent(Long idx) {
        return query.selectFrom(category)
                .leftJoin(category.parentCategory).fetchJoin()
                .where(category.idx.eq(idx))
                .fetchOne();
    }
}
