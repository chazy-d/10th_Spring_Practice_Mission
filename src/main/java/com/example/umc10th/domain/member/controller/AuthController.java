package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.SignupRequestDto;
import com.example.umc10th.domain.member.dto.SignupResponseDto;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@PostMapping("/signup")
	public ApiResponse<SignupResponseDto> signup(@RequestBody SignupRequestDto request) {
		SignupResponseDto response = new SignupResponseDto(
			1L,
			request.nickname(),
			request.regionId(),
			request.email(),
			request.phoneNumber(),
			LocalDateTime.now()
		);

		return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_CREATED, response);
	}
}
