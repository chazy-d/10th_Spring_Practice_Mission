package com.example.umc10th.domain.category.repository;

import com.example.umc10th.domain.category.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
