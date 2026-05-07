package com.example.umc10th.api.home.converter;

import com.example.umc10th.api.home.dto.HomeResponseDto;
import com.example.umc10th.domain.mission.entity.Mission;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HomeConverter {

	public HomeResponseDto.HomeMissionDto toHomeMissionDto(Mission mission) {
		return new HomeResponseDto.HomeMissionDto(
			mission.getId(),
			mission.getStore().getId(),
			mission.getStore().getName(),
			mission.getConditionText(),
			mission.getRewardPoint(),
			calculateDaysRemaining(mission)
		);
	}

	private Integer calculateDaysRemaining(Mission mission) {
		if (mission.getEndedAt() == null) {
			return null;
		}

		long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), mission.getEndedAt().toLocalDate());

		return (int) Math.max(daysRemaining, 0);
	}
}
