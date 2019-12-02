package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.category.MainCategory;
import com.ward.wardshop.goods.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    @Transactional
    public Long createCategory(String categoryName) {
        MainCategory savedCategory =
                mainCategoryRepository.save(MainCategory.builder().name(categoryName).build());

        return savedCategory.getIdx();
    }
}
