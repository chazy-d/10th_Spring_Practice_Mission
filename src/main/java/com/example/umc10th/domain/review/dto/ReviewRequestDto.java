package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record ReviewRequestDto(
	@NotBlank(message = "리뷰 내용은 필수입니다.")
	@Size(max = 500, message = "리뷰 내용은 최대 500자까지 입력할 수 있습니다.")
	String reviewContent,

	@NotNull(message = "별점은 필수입니다.")
	@DecimalMin(value = "1.0", message = "별점은 1점 이상이어야 합니다.")
	@DecimalMax(value = "5.0", message = "별점은 5점 이하이어야 합니다.")
	BigDecimal starRating,

	List<String> imageUrls
) {
}
