package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}