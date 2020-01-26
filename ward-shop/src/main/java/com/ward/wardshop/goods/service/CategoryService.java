package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.service.dto.CategoryDto;
import com.ward.wardshop.goods.repository.CategoryRepository;
import com.ward.wardshop.goods.repository.dto.CategoryQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryList() {
        List<CategoryQueryDto> data = categoryRepository.findAllQueryDto();

        List<CategoryDto> roots = data.stream()
                .filter(dto -> Objects.isNull(dto.getParentCategoryIdx()))
                .sorted(Comparator.comparing(CategoryQueryDto::getSequence))
                .map(dto -> new CategoryDto(dto.getIdx(), dto.getName(), new ArrayList<>()))
                .collect(toList());

        Map<Long, List<CategoryQueryDto>> children = data.stream()
                .filter(dto -> Objects.nonNull(dto.getParentCategoryIdx()))
                .collect(groupingBy(CategoryQueryDto::getParentCategoryIdx));

        assembleCategoryDto(roots, children);

        return roots;
    }

    private void assembleCategoryDto(List<CategoryDto> categoryDtos, Map<Long, List<CategoryQueryDto>> children) {
        for (CategoryDto categoryDto : categoryDtos) {
            List<CategoryQueryDto> categoryQueryDtos = children.get(categoryDto.getIdx());

            if (Objects.nonNull(categoryQueryDtos)) {
                List<CategoryDto> collectedChild = categoryQueryDtos.stream()
                        .sorted(Comparator.comparing(CategoryQueryDto::getSequence))
                        .map(dto -> new CategoryDto(dto.getIdx(), dto.getName(), new ArrayList<>()))
                        .collect(toList());

                categoryDto.getChildCategories().addAll(collectedChild);

                assembleCategoryDto(collectedChild, children);
            }
        }
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

    @Transactional
    public void moveCategory(Long targetIdx, Long destIdx, Integer sequence) {
        Category targetParent = categoryRepository
                .findCategoryTargetParentFetchChildren(targetIdx);

        Category destCategory = categoryRepository
                .findCategoryByIdxFetchChildren(destIdx);

        Category targetCategory = categoryRepository.findById(targetIdx)
                .orElseThrow(EntityNotFoundException::new);

        targetParent.deleteChildCategory(targetCategory);
        destCategory.addChildCategory(targetCategory, sequence);
    }
}
