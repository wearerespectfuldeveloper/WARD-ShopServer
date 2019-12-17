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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    //TODO 카테고리의 조회 권한을 제외한 생성, 수정, 삭제는 관리자에 한에서만 접근하도록 변경해야한다.

    private final CategoryService categoryService;

    @PostMapping
    public Result createCategory(@RequestBody String categoryName) {
        Long categoryIdx = categoryService.createCategory(categoryName);

        Result result = new Result();
        result.setIdx(categoryIdx);

        return result;
    }

    @NoArgsConstructor
    @Data
    private static class Result {
        private Long idx;
    }
}
