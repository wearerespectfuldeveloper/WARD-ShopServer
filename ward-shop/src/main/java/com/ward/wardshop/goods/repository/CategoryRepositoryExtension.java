package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryExtension {
    List<Category> findAllOrderByCategoryGroupOrder();
    Optional<Integer> findLastOrderingInRoot();
}
