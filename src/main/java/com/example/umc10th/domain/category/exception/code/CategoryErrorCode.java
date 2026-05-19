package com.example.umc10th.domain.category.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CategoryErrorCode implements BaseErrorCode {
	FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY404", "음식 카테고리를 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	CategoryErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
