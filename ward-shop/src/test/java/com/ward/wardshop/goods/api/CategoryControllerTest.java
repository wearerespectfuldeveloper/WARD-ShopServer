package com.ward.wardshop.goods.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward.wardshop.goods.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("local")
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void setup() {
        when(
                categoryService.createCategory(anyString())
        ).thenReturn(1L);
    }

    @Test
    @DisplayName("메인 카테고리 등록 테스트")
    void createMainCategoryTest() throws Exception {
        //given
        Map<String, String> param = new HashMap<>();
        param.put("categoryName", "testCategory");

        //when
        final ResultActions actions = mvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(param))
        ).andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("idx").value(1L));
    }
}