package com.example.umc10th.domain.review.exception;

import com.example.umc10th.global.payload.ErrorCode;

public class ReviewException extends RuntimeException {

	private final ErrorCode errorCode;

	public ReviewException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
