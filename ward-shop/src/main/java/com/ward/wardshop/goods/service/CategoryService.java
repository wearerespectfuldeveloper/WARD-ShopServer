package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public void getCategoryList() {
        //TODO 트리형 카테고리 리스트 DTO 반환
    }

    /**
     * 카테고리 생성은 항상 가장 아래의 루트 카테고리로 생성한다.
     *
     * @param categoryName 생성할 카테고리 이름
     * @return 생성된 카테고리의 idx 값이 반환된다.
     */
    @Transactional
    public Long createCategory(String categoryName) {
        int newOrder = categoryRepository.findLastOrderingInRoot().orElse(0) + 1;

        Category newCategory = new Category(categoryName);
        newCategory.addSequence(newOrder);
        categoryRepository.save(newCategory);

        return newCategory.getIdx();
    }

    @Transactional
    public void changeCategoryName(Long idx, String categoryName) {
        Category category = categoryRepository.findById(idx)
                .orElseThrow(EntityNotFoundException::new);

        category.changeName(categoryName);
    }
}
