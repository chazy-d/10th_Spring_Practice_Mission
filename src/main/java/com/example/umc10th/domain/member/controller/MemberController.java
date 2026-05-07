package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberResponseDto;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/me")
	public ApiResponse<MemberResponseDto> getMyPage(
		@RequestParam Long memberId
	) {
		MemberResponseDto response = memberService.getMyPage(memberId);

		return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_FOUND, response);
	}
}
