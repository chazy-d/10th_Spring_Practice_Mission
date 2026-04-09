package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.global.payload.ErrorCode;

public class MissionException extends RuntimeException {

	private final ErrorCode errorCode;

	public MissionException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
