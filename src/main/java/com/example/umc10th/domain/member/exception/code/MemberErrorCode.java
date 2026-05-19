package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements BaseErrorCode {
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "회원을 찾을 수 없습니다."),
	REQUIRED_TERM_NOT_AGREED(HttpStatus.BAD_REQUEST, "MEMBER400_1", "필수 약관에 동의해야 합니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER409", "이미 사용 중인 이메일입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	MemberErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
