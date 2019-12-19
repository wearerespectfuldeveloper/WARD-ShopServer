package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 생성은 항상 가장 아래의 루트 카테고리로 생성한다.
     *
     * @param categoryName
     * @return
     */
    @Transactional
    public Long createCategory(String categoryName) {
        Integer newOrder = categoryRepository.findLastOrderingInRoot().orElse(0);

        Category newCategory = new Category(categoryName);

        categoryRepository.save(newCategory);

        newCategory.setRootGroup(newOrder);
        return newCategory.getIdx();
    }
}
