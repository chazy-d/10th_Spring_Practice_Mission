package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReviewRequestDto(
	String reviewContent,
	BigDecimal starRating,
	List<String> imageUrls
) {
}
