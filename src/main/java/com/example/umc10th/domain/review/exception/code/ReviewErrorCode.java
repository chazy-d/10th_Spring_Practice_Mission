package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewErrorCode implements BaseErrorCode {
	REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404", "리뷰를 찾을 수 없습니다."),
	MEMBER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_1", "리뷰를 작성할 회원 미션을 찾을 수 없습니다."),
	MISSION_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "REVIEW400_1", "완료된 미션에만 리뷰를 작성할 수 있습니다."),
	INVALID_REVIEW_CURSOR(HttpStatus.BAD_REQUEST, "REVIEW400_2", "리뷰 커서 형식이 올바르지 않습니다."),
	REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "REVIEW409_1", "이미 리뷰가 작성된 미션입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ReviewErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
