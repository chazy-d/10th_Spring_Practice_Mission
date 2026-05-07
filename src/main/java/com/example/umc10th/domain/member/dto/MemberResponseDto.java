package com.example.umc10th.domain.member.dto;

public record MemberResponseDto(
	Long memberId,
	String name,
	String nickname,
	String profileImageUrl,
	String email,
	String phoneNumber,
	Integer point
) {
}
