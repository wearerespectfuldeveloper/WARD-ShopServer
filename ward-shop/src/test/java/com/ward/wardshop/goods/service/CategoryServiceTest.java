package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @WithAnonymousUser
    @Transactional
    void categoryCreateTest() {
        //given
        String categoryName = "a";

        //when
        Long categoryIdx = categoryService.createCategory(categoryName);
        Optional<Category> category = categoryRepository.findById(categoryIdx);

        //then
        log.info(category.get().getCategoryGroup().getGroup_idx().toString());
        assertEquals(categoryIdx, 1);
        assertTrue(category.isPresent());
    }
}