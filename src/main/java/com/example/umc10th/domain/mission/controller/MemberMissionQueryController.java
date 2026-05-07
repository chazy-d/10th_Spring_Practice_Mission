package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MemberMissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberMissionQueryController {

	private final MemberMissionService memberMissionService;

	public MemberMissionQueryController(MemberMissionService memberMissionService) {
		this.memberMissionService = memberMissionService;
	}

	@GetMapping("/api/member-missions")
	public ApiResponse<MemberMissionListResponseDto> getMemberMissions(
		@RequestParam Long memberId,
		@RequestParam MemberMissionStatus status,
		@RequestParam(required = false) Long cursor,
		@RequestParam Integer size
	) {
		MemberMissionListResponseDto response = memberMissionService.getMemberMissions(memberId, status, cursor, size);

		return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_LIST_FOUND, response);
	}
}
