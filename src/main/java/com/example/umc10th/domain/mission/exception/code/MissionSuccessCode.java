package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import org.springframework.http.HttpStatus;

public enum MissionSuccessCode implements BaseSuccessCode {
	HOME_FOUND(HttpStatus.OK, "HOME200_1", "홈 화면 조회에 성공했습니다."),
	USER_MISSION_LIST_FOUND(HttpStatus.OK, "USER_MISSION200_1", "사용자 미션 목록 조회에 성공했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	MissionSuccessCode(HttpStatus status, String code, String message) {
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
