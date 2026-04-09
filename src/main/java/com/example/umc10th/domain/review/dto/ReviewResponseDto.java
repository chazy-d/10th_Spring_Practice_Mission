package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;

public record ReviewResponseDto(
	Long reviewId,
	String content,
	BigDecimal star
) {
}
