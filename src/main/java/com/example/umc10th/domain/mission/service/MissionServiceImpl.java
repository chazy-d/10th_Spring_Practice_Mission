package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MissionServiceImpl implements MissionService {

	private final MissionRepository missionRepository;
	private final MemberMissionRepository memberMissionRepository;
	private final MissionConverter missionConverter = new MissionConverter();

	public MissionServiceImpl(
		MissionRepository missionRepository,
		MemberMissionRepository memberMissionRepository
	) {
		this.missionRepository = missionRepository;
		this.memberMissionRepository = memberMissionRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public MissionResponseDto getMission(Long missionId) {
		return missionRepository.findById(missionId)
			.map(missionConverter::toResponse)
			.orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
	}

	@Override
	@Transactional(readOnly = true)
	public MemberMissionListResponseDto getMemberMissions(
		Long memberId,
		MemberMissionStatus status,
		Long cursor,
		Integer size
	) {
		List<MemberMissionStatus> statuses = toSearchStatuses(status);
		List<MemberMission> memberMissions = memberMissionRepository.findByMemberAndStatusesWithCursor(
			memberId,
			statuses,
			cursor,
			PageRequest.of(0, size + 1)
		);

		boolean hasNext = memberMissions.size() > size;
		List<MemberMission> responseMissions = hasNext ? memberMissions.subList(0, size) : memberMissions;
		Long nextCursor = hasNext && !responseMissions.isEmpty()
			? responseMissions.get(responseMissions.size() - 1).getId()
			: null;

		return new MemberMissionListResponseDto(
			status,
			responseMissions.stream()
				.map(missionConverter::toMemberMissionSummary)
				.toList(),
			size,
			nextCursor,
			hasNext
		);
	}

	private List<MemberMissionStatus> toSearchStatuses(MemberMissionStatus status) {
		if (status == MemberMissionStatus.IN_PROGRESS) {
			List<MemberMissionStatus> statuses = new ArrayList<>();
			statuses.add(MemberMissionStatus.IN_PROGRESS);
			statuses.add(MemberMissionStatus.SUCCESS_REQUESTED);
			return statuses;
		}

		return List.of(status);
	}
}
