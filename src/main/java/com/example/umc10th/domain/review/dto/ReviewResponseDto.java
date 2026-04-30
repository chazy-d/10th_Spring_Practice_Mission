package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponseDto(
	Long reviewId,
	Long userMissionId,
	Long missionId,
	Long storeId,
	String reviewContent,
	BigDecimal starRating,
	List<String> images,
	LocalDateTime createdAt
) {
	public ReviewResponseDto(Long reviewId, String reviewContent, BigDecimal starRating) {
		this(reviewId, null, null, null, reviewContent, starRating, List.of(), null);
	}
}
