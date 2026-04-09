package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.payload.ErrorCode;

public enum ReviewErrorCode implements ErrorCode {
	REVIEW_NOT_FOUND("REVIEW404", "리뷰를 찾을 수 없습니다.");

	private final String code;
	private final String message;

	ReviewErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
