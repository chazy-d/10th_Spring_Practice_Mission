package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.HomeResponseDto;
import com.example.umc10th.domain.mission.dto.UserMissionListResponseDto;
import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionQueryController {

	@GetMapping("/api/home")
	public ApiResponse<HomeResponseDto> getHome(
		@RequestParam Long regionId,
		@RequestParam(required = false) Long cursor,
		@RequestParam Integer size
	) {
		HomeResponseDto response = new HomeResponseDto(
			999999,
			regionId,
			"오금동",
			7,
			10,
			1000,
			List.of(
				new HomeResponseDto.HomeMissionDto(
					101L,
					1L,
					"춘리마라탕",
					"10,000원 이상의 식사 시",
					500,
					7
				)
			),
			size,
			12L,
			true
		);

		return ApiResponse.onSuccess(MissionSuccessCode.HOME_FOUND, response);
	}

	@GetMapping("/api/user-missions")
	public ApiResponse<UserMissionListResponseDto> getUserMissions(
		@RequestParam(required = false) Long regionId,
		@RequestParam UserMissionStatus status,
		@RequestParam(required = false) Long cursor,
		@RequestParam Integer size
	) {
		LocalDateTime now = LocalDateTime.now();
		UserMissionListResponseDto response = new UserMissionListResponseDto(
			status,
			List.of(
				new UserMissionListResponseDto.UserMissionSummaryDto(
					44L,
					101L,
					1L,
					"춘리마라탕",
					"10,000원 이상의 식사 시",
					500,
					status,
					now,
					status == UserMissionStatus.SUCCESS_REQUESTED ? now : null,
					status == UserMissionStatus.COMPLETED ? now : null,
					7
				)
			),
			size,
			cursor == null ? null : cursor - 1,
			false
		);

		return ApiResponse.onSuccess(MissionSuccessCode.USER_MISSION_LIST_FOUND, response);
	}
}
