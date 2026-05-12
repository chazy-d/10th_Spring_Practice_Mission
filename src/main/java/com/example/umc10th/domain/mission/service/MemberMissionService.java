package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MemberMissionOffsetPageResponseDto;
import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;

public interface MemberMissionService {

	MemberMissionListResponseDto getMemberMissions(
		Long memberId,
		MemberMissionStatus status,
		Long cursor,
		Integer size
	);

	MemberMissionOffsetPageResponseDto getInProgressMemberMissions(
		Long memberId,
		Integer pageNumber,
		Integer pageSize
	);
}
