package com.example.umc10th.domain.category.repository;

import com.example.umc10th.domain.category.entity.mapping.MemberFoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFoodCategoryRepository extends JpaRepository<MemberFoodCategory, Long> {
}
