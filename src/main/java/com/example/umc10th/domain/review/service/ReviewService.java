package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewListResponseDto;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.enums.ReviewSortType;

public interface ReviewService {

	ReviewResponseDto getReview(Long reviewId);

	ReviewResponseDto createReview(Long memberMissionId, ReviewRequestDto request);

	ReviewListResponseDto getMyReviews(Long memberId, ReviewSortType sort, String cursor, Integer size);
}
