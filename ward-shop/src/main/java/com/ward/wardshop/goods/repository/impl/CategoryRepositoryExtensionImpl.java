package com.ward.wardshop.goods.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepositoryExtension;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
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

        if (Objects.isNull(idx)) {
            List<Category> children = findRootCategories();

            return Category.Root.create(children);
        }

        return query.selectFrom(category)
                .leftJoin(category.childCategories).fetchJoin()
                .where(category.idx.eq(idx))
                .orderBy(category.sequence.asc())
                .fetchOne();
    }

    private List<Category> findRootCategories() {
        return query.selectFrom(category)
                .where(category.parentCategory.isNull())
                .orderBy(category.sequence.asc())
                .fetch();
    }

    @Override
    public Category findCategoryTargetParentFetchChildren(Long idx) {
        Long parentIdx = query.select(category.parentCategory.idx)
                .from(category)
                .where(category.idx.eq(idx))
                .orderBy(category.sequence.asc())
                .fetchOne();

        return findCategoryByIdxFetchChildren(parentIdx);
    }
}
