package com.example.umc10th.domain.member.dto;

public record MemberResponseDto(
	Long memberId,
	String name,
	String email
) {
}
