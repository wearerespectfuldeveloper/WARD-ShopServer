package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.dto.CategoryQueryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryExtension {
    Optional<Integer> findLastOrderingInRoot();

    List<CategoryQueryDto> findAllQueryDto();

    Category findCategoryByIdxFetchChildren(Long idx);

    Category findCategoryTargetParentFetchChildren(Long idx);
}
