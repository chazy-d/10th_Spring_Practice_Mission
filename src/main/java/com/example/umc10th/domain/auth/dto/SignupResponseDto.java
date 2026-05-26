package com.example.umc10th.domain.auth.dto;

import java.time.LocalDateTime;

public record SignupResponseDto(
	Long memberId,
	String nickname,
	Long regionId,
	String email,
	String phoneNumber,
	LocalDateTime createdAt
) {
}
