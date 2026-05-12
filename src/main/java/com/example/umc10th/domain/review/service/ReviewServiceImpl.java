package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberMissionRepository memberMissionRepository;
	private final ReviewConverter reviewConverter;

	public ReviewServiceImpl(
		ReviewRepository reviewRepository,
		MemberMissionRepository memberMissionRepository,
		ReviewConverter reviewConverter
	) {
		this.reviewRepository = reviewRepository;
		this.memberMissionRepository = memberMissionRepository;
		this.reviewConverter = reviewConverter;
	}

	@Override
	@Transactional(readOnly = true)
	public ReviewResponseDto getReview(Long reviewId) {
		return reviewRepository.findById(reviewId)
			.map(reviewConverter::toResponse)
			.orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
	}

	@Override
	@Transactional
	public ReviewResponseDto createReview(Long memberMissionId, ReviewRequestDto request) {
		MemberMission memberMission = memberMissionRepository.findById(memberMissionId)
			.orElseThrow(() -> new ReviewException(ReviewErrorCode.MEMBER_MISSION_NOT_FOUND));

		if (memberMission.getStatus() != MemberMissionStatus.COMPLETED) {
			throw new ReviewException(ReviewErrorCode.MISSION_NOT_COMPLETED);
		}

		if (reviewRepository.existsByMemberMissionId(memberMissionId)) {
			throw new ReviewException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
		}

		Review review = reviewConverter.toReview(memberMission, request);
		Review savedReview = reviewRepository.save(review);

		return reviewConverter.toResponse(savedReview);
	}
}
