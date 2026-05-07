package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;

public interface MissionService {

	MissionResponseDto getMission(Long missionId);

	MemberMissionListResponseDto getMemberMissions(
		Long memberId,
		MemberMissionStatus status,
		Long cursor,
		Integer size
	);
}
