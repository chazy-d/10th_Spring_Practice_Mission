package com.example.umc10th.domain.category.entity.mapping;

import com.example.umc10th.domain.category.entity.FoodCategory;
import com.example.umc10th.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "member_food_category")
public class MemberFoodCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberFoodCategoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_category_id", nullable = false)
	private FoodCategory foodCategory;
}
