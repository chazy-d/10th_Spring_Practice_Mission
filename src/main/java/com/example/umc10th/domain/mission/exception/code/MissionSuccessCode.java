package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MissionSuccessCode implements BaseSuccessCode {
	HOME_FOUND(HttpStatus.OK, "HOME200_1", "홈 화면 조회에 성공했습니다."),
	MEMBER_MISSION_LIST_FOUND(HttpStatus.OK, "MEMBER_MISSION200_1", "회원 미션 목록 조회에 성공했습니다."),
	MEMBER_MISSION_VERIFICATION_CREATED(HttpStatus.CREATED, "MEMBER_MISSION201_1", "미션 성공 인증 요청이 완료되었습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	MissionSuccessCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
