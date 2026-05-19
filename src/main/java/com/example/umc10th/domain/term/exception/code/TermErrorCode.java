package com.example.umc10th.domain.term.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TermErrorCode implements BaseErrorCode {
	TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "TERM404", "약관을 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	TermErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
