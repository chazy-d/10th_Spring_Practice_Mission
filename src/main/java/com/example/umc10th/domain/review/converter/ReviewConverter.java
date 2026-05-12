package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.review.dto.ReviewListResponseDto;
import com.example.umc10th.domain.review.dto.ReviewResponseDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.dto.ReviewRequestDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

	public Review toReview(MemberMission memberMission, ReviewRequestDto request) {
		Review review = Review.create(
			request.reviewContent(),
			request.starRating(),
			memberMission.getMission().getStore(),
			memberMission.getMember(),
			memberMission
		);

		List<String> imageUrls = request.imageUrls() == null ? List.of() : request.imageUrls();
		imageUrls.forEach(review::addPhoto);

		return review;
	}

	public ReviewResponseDto toResponse(Review review) {
		return new ReviewResponseDto(
			review.getId(),
			review.getMemberMission().getId(),
			review.getMemberMission().getMission().getId(),
			review.getStore().getId(),
			review.getContent(),
			review.getRating(),
			review.getImageUrls(),
			review.getCreatedAt()
		);
	}

	public ReviewListResponseDto.ReviewSummaryDto toSummary(Review review) {
		return new ReviewListResponseDto.ReviewSummaryDto(
			review.getId(),
			review.getStore().getId(),
			review.getStore().getName(),
			review.getContent(),
			review.getRating(),
			review.getCreatedAt()
		);
	}
}
