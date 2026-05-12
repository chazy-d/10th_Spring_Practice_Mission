package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewListResponseDto(
	List<ReviewSummaryDto> reviews,
	Integer size,
	String nextCursor,
	Boolean hasNext
) {
	public record ReviewSummaryDto(
		Long reviewId,
		Long storeId,
		String storeName,
		String reviewContent,
		BigDecimal starRating,
		LocalDateTime createdAt
	) {
	}
}
