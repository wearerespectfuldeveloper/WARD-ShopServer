package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.api.model.CategoryLocationForm;
import com.ward.wardshop.goods.service.dto.CategoryDto;
import com.ward.wardshop.goods.service.CategoryService;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    //TODO 카테고리의 조회 권한을 제외한 생성, 수정, 삭제는 관리자에 한에서만 접근하도록 변경해야한다.

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategoryList() {
        return categoryService.getCategoryList();
    }

    @PostMapping
    public Result createCategory(@RequestBody CategoryNameForm categoryName) {
        Long categoryIdx = categoryService.createCategory(categoryName.getCategoryName());

        Result result = new Result();
        result.setIdx(categoryIdx);

        return result;
    }

    @PutMapping("/{idx}/name")
    public ResponseEntity<String> changeCategoryName(@PathVariable Long idx,
                                                     @RequestBody CategoryNameForm form) {
        categoryService.changeCategoryName(idx, form.getCategoryName());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/move")
    public ResponseEntity<String> changeCategoryLocation(@RequestBody CategoryLocationForm form) {
        categoryService.moveCategory(form.getTargetIdx(), form.getDestIdx(), form.getSequence());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @NoArgsConstructor
    @Data
    private static class Result {
        private Long idx;
    }

    @NoArgsConstructor
    @Getter
    private static class CategoryNameForm {
        private String categoryName;
    }
}
