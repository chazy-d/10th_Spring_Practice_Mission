package com.example.umc10th.domain.category.repository;

import com.example.umc10th.domain.category.entity.mapping.StoreFoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFoodCategoryRepository extends JpaRepository<StoreFoodCategory, Long> {
}
