package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.payload.ErrorCode;

public enum MemberErrorCode implements ErrorCode {
	MEMBER_NOT_FOUND("MEMBER404", "사용자를 찾을 수 없습니다.");

	private final String code;
	private final String message;

	MemberErrorCode(String code, String message) {
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
