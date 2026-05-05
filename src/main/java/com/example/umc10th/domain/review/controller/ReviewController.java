package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

	@PostMapping("/api/member-missions/{memberMissionId}/review")
	public ApiResponse<ReviewResponseDto> createReview(
		@PathVariable Long memberMissionId,
		@RequestBody ReviewRequestDto request
	) {
		ReviewResponseDto response = new ReviewResponseDto(
			1L,
			memberMissionId,
			101L,
			1L,
			request.reviewContent(),
			request.starRating(),
			request.imageUrls() == null ? List.of() : request.imageUrls(),
			LocalDateTime.now()
		);

		return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, response);
	}
}
