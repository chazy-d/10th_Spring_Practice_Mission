package com.example.umc10th.api.home.controller;

import com.example.umc10th.api.home.dto.HomeResponseDto;
import com.example.umc10th.api.home.service.HomeService;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private final HomeService homeService;

	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@GetMapping("/api/home")
	public ApiResponse<HomeResponseDto> getHome(
		@RequestParam Long memberId,
		@RequestParam Long regionId,
		@RequestParam(required = false) Long cursor,
		@RequestParam Integer size
	) {
		HomeResponseDto response = homeService.getHome(memberId, regionId, cursor, size);

		return ApiResponse.onSuccess(MissionSuccessCode.HOME_FOUND, response);
	}
}
