package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MemberMissionInProgressRequestDto;
import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.dto.MemberMissionOffsetPageResponseDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MemberMissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		@RequestParam
		@Positive(message = "회원 ID는 양수여야 합니다.")
		Long memberId,
		@RequestParam MemberMissionStatus status,
		@RequestParam(required = false)
		@Positive(message = "커서는 양수여야 합니다.")
		Long cursor,
		@RequestParam
		@Min(value = 1, message = "조회 크기는 1 이상이어야 합니다.")
		@Max(value = 50, message = "조회 크기는 최대 50까지 가능합니다.")
		Integer size
	) {
		MemberMissionListResponseDto response = memberMissionService.getMemberMissions(memberId, status, cursor, size);

		return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_LIST_FOUND, response);
	}

	@PostMapping("/api/member-missions/in-progress")
	public ApiResponse<MemberMissionOffsetPageResponseDto> getInProgressMemberMissions(
		@Valid @RequestBody MemberMissionInProgressRequestDto request,
		@RequestParam(defaultValue = "0")
		@Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
		Integer pageNumber,
		@RequestParam(defaultValue = "10")
		@Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
		@Max(value = 50, message = "페이지 크기는 최대 50까지 가능합니다.")
		Integer pageSize
	) {
		MemberMissionOffsetPageResponseDto response = memberMissionService.getInProgressMemberMissions(
			request.memberId(),
			pageNumber,
			pageSize
		);

		return ApiResponse.onSuccess(MissionSuccessCode.IN_PROGRESS_MEMBER_MISSION_PAGE_FOUND, response);
	}
}
