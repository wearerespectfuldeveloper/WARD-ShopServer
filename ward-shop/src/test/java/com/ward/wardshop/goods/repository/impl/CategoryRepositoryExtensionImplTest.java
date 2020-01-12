package com.ward.wardshop.goods.repository.impl;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class CategoryRepositoryExtensionImplTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findCategoryByIdxFetchChildrenTest() {
        //given
        Long idx = 1L;

        //when
        Category category = categoryRepository.findCategoryByIdxFetchChildren(idx);

        //then
        assertNotNull(category);
    }

    @Test
    public void findCategoryByIdxFetchParentTest() {
        //given
        Long idx = 5L;

        //when
        Category category = categoryRepository.findCategoryTargetParentFetchChildren(idx);

        //then
        assertNotNull(category);
    }
}