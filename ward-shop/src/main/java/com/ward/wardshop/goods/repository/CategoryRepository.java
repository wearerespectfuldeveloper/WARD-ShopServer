package com.ward.wardshop.goods.repository;

import com.ward.wardshop.goods.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository
        extends JpaRepository<Category, Long>, CategoryRepositoryExtension {
}