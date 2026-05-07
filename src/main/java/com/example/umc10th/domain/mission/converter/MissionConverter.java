package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.Mission;

public class MissionConverter {

	public MissionResponseDto toResponse(Mission mission) {
		return new MissionResponseDto(mission.getId(), mission.getConditionText(), mission.getRewardPoint());
	}
}
