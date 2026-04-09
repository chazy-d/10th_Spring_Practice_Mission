package com.example.umc10th.domain.member.exception;

import com.example.umc10th.global.payload.ErrorCode;

public class MemberException extends RuntimeException {

	private final ErrorCode errorCode;

	public MemberException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
