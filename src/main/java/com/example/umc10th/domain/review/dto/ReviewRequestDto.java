package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;

public record ReviewRequestDto(
	String content,
	BigDecimal star
) {
}
