package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("local")
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
        String categoryName = "new";

        //when
        Long categoryIdx = categoryService.createCategory(categoryName);
        Optional<Category> category = categoryRepository.findById(categoryIdx);

        //then
        log.info(category.get().getIdx().toString());
        assertTrue(category.isPresent());
    }

    @Test
    @WithAnonymousUser
    @Transactional
    void changeParentTest() {
        //given
        Category destination = categoryRepository.findById(2L)
                .orElseThrow(EntityNotFoundException::new);
        Category target = categoryRepository.findById(5L)
                .orElseThrow(EntityNotFoundException::new);
        int destinationSequence = 1;

        //when
        target.deleteParent();
        destination.addChildCategory(target, destinationSequence);
        categoryRepository.save(target);

        //then
        assertEquals(target.getSequence(), destinationSequence);
    }

    @Test
    @WithAnonymousUser
    @Transactional
    void getCategoryList() {
        //given
        categoryService.getCategoryList();
    }
}