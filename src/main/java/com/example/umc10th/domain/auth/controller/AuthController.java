package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.dto.SignupRequestDto;
import com.example.umc10th.domain.auth.dto.SignupResponseDto;
import com.example.umc10th.domain.auth.service.AuthService;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ApiResponse<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto request) {
		SignupResponseDto response = authService.signup(request);
		return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_CREATED, response);
	}
}
