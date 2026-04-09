package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.payload.ErrorCode;

public enum MissionErrorCode implements ErrorCode {
	MISSION_NOT_FOUND("MISSION404", "미션을 찾을 수 없습니다.");

	private final String code;
	private final String message;

	MissionErrorCode(String code, String message) {
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
