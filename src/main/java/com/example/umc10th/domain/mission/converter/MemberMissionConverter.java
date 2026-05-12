package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class MemberMissionConverter {

	public MemberMissionListResponseDto.MemberMissionSummaryDto toSummary(MemberMission memberMission) {
		Mission mission = memberMission.getMission();

		return new MemberMissionListResponseDto.MemberMissionSummaryDto(
			memberMission.getId(),
			mission.getId(),
			mission.getStore().getId(),
			mission.getStore().getName(),
			mission.getContent(),
			mission.getRewardPoint(),
			memberMission.getStatus(),
			memberMission.getStartedAt(),
			memberMission.getSuccessRequestedAt(),
			memberMission.getCompletedAt(),
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
