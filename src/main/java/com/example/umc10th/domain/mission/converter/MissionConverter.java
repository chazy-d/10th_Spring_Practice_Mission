package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.Mission;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MissionConverter {

	public MissionResponseDto toResponse(Mission mission) {
		return new MissionResponseDto(mission.getId(), mission.getConditionText(), mission.getRewardPoint());
	}

	public MemberMissionListResponseDto.MemberMissionSummaryDto toMemberMissionSummary(MemberMission memberMission) {
		Mission mission = memberMission.getMission();

		return new MemberMissionListResponseDto.MemberMissionSummaryDto(
			memberMission.getId(),
			mission.getId(),
			mission.getStore().getId(),
			mission.getStore().getName(),
			mission.getConditionText(),
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
