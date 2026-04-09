package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;

public record MissionRequestDto(
	LocalDate deadline,
	String condition,
	Integer rewardPoint
) {
}
