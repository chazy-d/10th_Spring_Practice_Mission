package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionQueryController {

	private final MissionService missionService;

	public MissionQueryController(MissionService missionService) {
		this.missionService = missionService;
	}

	@GetMapping("/api/member-missions")
	public ApiResponse<MemberMissionListResponseDto> getMemberMissions(
		@RequestParam Long memberId,
		@RequestParam MemberMissionStatus status,
		@RequestParam(required = false) Long cursor,
		@RequestParam Integer size
	) {
		MemberMissionListResponseDto response = missionService.getMemberMissions(memberId, status, cursor, size);

		return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_LIST_FOUND, response);
	}
}
