package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.service.CategoryService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maincategories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Result createCategory(@RequestBody CategoryDto dto) {
        Long categoryIdx = categoryService.createCategory(dto.categoryName);

        Result result = new Result();
        result.setIdx(categoryIdx);

        return result;
    }

    @NoArgsConstructor
    @Data
    private static class CategoryDto {
        private String categoryName;
    }

    @NoArgsConstructor
    @Data
    private static class Result {
        private Long idx;
    }
}
