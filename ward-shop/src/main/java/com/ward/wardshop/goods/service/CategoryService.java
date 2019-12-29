package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.api.model.CategoryGroupChangeForm;
import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.domain.CategoryGroup;
import com.ward.wardshop.goods.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 생성은 항상 가장 아래의 루트 카테고리로 생성한다.
     *
     * @param categoryName 생성할 카테고리 이름
     * @return 생성된 카테고리의 idx 값이 반환된다.
     */
    @Transactional
    public Long createCategory(String categoryName) {
        Integer newOrder = categoryRepository.findLastOrderingInRoot().orElse(0);

        Category newCategory = new Category(categoryName);

        categoryRepository.save(newCategory);

        newCategory.setRootGroupAfter(newOrder);
        return newCategory.getIdx();
    }

    @Transactional
    public void changeCategoryName(Long idx, String categoryName) {
        Category category = categoryRepository.findById(idx)
                .orElseThrow(EntityNotFoundException::new);

        category.changeName(categoryName);
    }

    /**
     * 1. 타겟 카테고리를 목표 카테고리의 아래에 놓는다.
     * 2. 타겟 카테고리와 목표 카테고리 사이의 order 값이 모두 1씩 증가한다.
     */
    @Transactional
    public void changeCategoryGroup(CategoryGroupChangeForm changeForm) {
        Category targetCategory = categoryRepository.findById(changeForm.getTargetIdx())
                .orElseThrow(EntityNotFoundException::new);

        Category preSiblingCategory = categoryRepository.findById(changeForm.getPreSiblingIdx())
                .orElse(null);

        CategoryGroup preSiblingGroup = (Objects.nonNull(preSiblingCategory)) ?
                preSiblingCategory.getCategoryGroup() :
                CategoryGroup.builder().group_idx(targetCategory.getIdx()).level(1).ordering(0).build();

        List<Category> betweenCategory = findBetweenCategoryOrder(
                targetCategory.getCategoryGroup(),
                preSiblingGroup);

        targetCategory.moveGroupAfter(preSiblingGroup);
        for (Category category : betweenCategory) {
            category.addOrder(1);
        }
    }

    private List<Category> findBetweenCategoryOrder(CategoryGroup targetGroup,
                                                    CategoryGroup preSiblingGroup) {
        if (targetGroup.getOrdering() > preSiblingGroup.getOrdering()) {
            return categoryRepository.findCategoriesByCategoryGroup_OrderingBetween(
                    preSiblingGroup.getOrdering() + 1,
                    targetGroup.getOrdering() - 1);
        }

        return categoryRepository.findCategoriesByCategoryGroup_OrderingBetween(
                targetGroup.getOrdering() + 1,
                preSiblingGroup.getOrdering() - 1);
    }
}
