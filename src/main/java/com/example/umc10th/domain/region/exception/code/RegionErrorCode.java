package com.example.umc10th.domain.region.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RegionErrorCode implements BaseErrorCode {
	REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION404", "지역을 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	RegionErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
