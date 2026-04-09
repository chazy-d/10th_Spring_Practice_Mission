package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;

public class ReviewConverter {

	public ReviewResponseDto toResponse(Review review) {
		return new ReviewResponseDto(review.getId(), review.getContent(), review.getStar());
	}
}
