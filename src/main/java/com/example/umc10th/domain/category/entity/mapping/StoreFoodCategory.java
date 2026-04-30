package com.example.umc10th.domain.category.entity.mapping;

import com.example.umc10th.domain.category.entity.FoodCategory;
import com.example.umc10th.domain.store.entity.Store;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "store_food_category")
public class StoreFoodCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeFoodCategoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_category_id", nullable = false)
	private FoodCategory foodCategory;
}
