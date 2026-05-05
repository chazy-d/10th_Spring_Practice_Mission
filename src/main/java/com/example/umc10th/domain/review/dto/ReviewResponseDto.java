package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponseDto(
	Long reviewId,
	Long memberMissionId,
	Long missionId,
	Long storeId,
	String reviewContent,
	BigDecimal starRating,
	List<String> imageUrls,
	LocalDateTime createdAt
) {
	public ReviewResponseDto(Long reviewId, String reviewContent, BigDecimal starRating) {
		this(reviewId, null, null, null, reviewContent, starRating, List.of(), null);
	}
}
