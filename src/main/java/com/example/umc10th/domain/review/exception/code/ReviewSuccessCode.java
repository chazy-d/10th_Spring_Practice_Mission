package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewSuccessCode implements BaseSuccessCode {
	REVIEW_CREATED(HttpStatus.CREATED, "REVIEW201_1", "리뷰 작성이 완료되었습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ReviewSuccessCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
