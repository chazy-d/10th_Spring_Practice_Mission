package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements BaseSuccessCode {
	MEMBER_CREATED(HttpStatus.CREATED, "MEMBER201", "사용자 생성에 성공했습니다."),
	MEMBER_FOUND(HttpStatus.OK, "MEMBER200", "사용자 조회에 성공했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	MemberSuccessCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
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
