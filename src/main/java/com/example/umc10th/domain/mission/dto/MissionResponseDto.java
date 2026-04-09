package com.example.umc10th.domain.mission.dto;

public record MissionResponseDto(
	Long missionId,
	String condition,
	Integer rewardPoint
) {
}
