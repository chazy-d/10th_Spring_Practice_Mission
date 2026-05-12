package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.review.dto.ReviewListResponseDto;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.PageRequest;
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

	@Override
	@Transactional(readOnly = true)
	public ReviewListResponseDto getMyReviews(Long memberId, ReviewSortType sort, String cursor, Integer size) {
		List<Review> reviews = switch (sort) {
			case ID -> findMyReviewsById(memberId, cursor, size);
			case RATING -> findMyReviewsByRating(memberId, cursor, size);
		};

		boolean hasNext = reviews.size() > size;
		List<Review> responseReviews = hasNext ? reviews.subList(0, size) : reviews;
		String nextCursor = createNextCursor(sort, hasNext, responseReviews);

		return new ReviewListResponseDto(
			responseReviews.stream()
				.map(reviewConverter::toSummary)
				.toList(),
			size,
			nextCursor,
			hasNext
		);
	}

	private List<Review> findMyReviewsById(Long memberId, String cursor, Integer size) {
		Long idCursor = parseIdCursor(cursor);

		return reviewRepository.findMyReviewsByIdCursor(
			memberId,
			idCursor,
			PageRequest.of(0, size + 1)
		);
	}

	private List<Review> findMyReviewsByRating(Long memberId, String cursor, Integer size) {
		RatingCursor ratingCursor = parseRatingCursor(cursor);

		return reviewRepository.findMyReviewsByRatingCursor(
			memberId,
			ratingCursor.rating(),
			ratingCursor.reviewId(),
			PageRequest.of(0, size + 1)
		);
	}

	private Long parseIdCursor(String cursor) {
		if (cursor == null || cursor.isBlank()) {
			return null;
		}

		try {
			return Long.valueOf(cursor);
		} catch (NumberFormatException exception) {
			throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_CURSOR);
		}
	}

	private RatingCursor parseRatingCursor(String cursor) {
		if (cursor == null || cursor.isBlank()) {
			return new RatingCursor(null, null);
		}

		String[] cursorValues = cursor.split(":");
		if (cursorValues.length != 2) {
			throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_CURSOR);
		}

		try {
			return new RatingCursor(
				new BigDecimal(cursorValues[0]),
				Long.valueOf(cursorValues[1])
			);
		} catch (NumberFormatException exception) {
			throw new ReviewException(ReviewErrorCode.INVALID_REVIEW_CURSOR);
		}
	}

	private String createNextCursor(ReviewSortType sort, boolean hasNext, List<Review> reviews) {
		if (!hasNext || reviews.isEmpty()) {
			return null;
		}

		Review lastReview = reviews.get(reviews.size() - 1);
		if (sort == ReviewSortType.RATING) {
			return lastReview.getRating().toPlainString() + ":" + lastReview.getId();
		}

		return String.valueOf(lastReview.getId());
	}

	private record RatingCursor(
		BigDecimal rating,
		Long reviewId
	) {
	}
}
