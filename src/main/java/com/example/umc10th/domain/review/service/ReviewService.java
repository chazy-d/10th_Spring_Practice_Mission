package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewListResponseDto;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;

public interface ReviewService {

	ReviewResponseDto getReview(Long reviewId);

	ReviewResponseDto createReview(Long memberMissionId, ReviewRequestDto request);

	ReviewListResponseDto getMyReviewsByIdCursor(Long memberId, Long cursor, Integer size);
}
