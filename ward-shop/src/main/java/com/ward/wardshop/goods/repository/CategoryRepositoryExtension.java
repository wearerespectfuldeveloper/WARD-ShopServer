package com.ward.wardshop.goods.repository;

import java.util.Optional;

public interface CategoryRepositoryExtension {
    Optional<Integer> findLastOrderingInRoot();
}
