package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MemberMissionConverter;
import com.example.umc10th.domain.mission.dto.MemberMissionListResponseDto;
import com.example.umc10th.domain.mission.dto.MemberMissionOffsetPageResponseDto;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberMissionServiceImpl implements MemberMissionService {

	private final MemberMissionRepository memberMissionRepository;
	private final MemberMissionConverter memberMissionConverter;

	public MemberMissionServiceImpl(
		MemberMissionRepository memberMissionRepository,
		MemberMissionConverter memberMissionConverter
	) {
		this.memberMissionRepository = memberMissionRepository;
		this.memberMissionConverter = memberMissionConverter;
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
				.map(memberMissionConverter::toSummary)
				.toList(),
			size,
			nextCursor,
			hasNext
		);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberMissionOffsetPageResponseDto getInProgressMemberMissions(
		Long memberId,
		Integer pageNumber,
		Integer pageSize
	) {
		List<MemberMissionStatus> statuses = toSearchStatuses(MemberMissionStatus.IN_PROGRESS);
		PageRequest pageRequest = PageRequest.of(
			pageNumber,
			pageSize,
			Sort.by(Sort.Direction.DESC, "id")
		);
		Page<MemberMission> page = memberMissionRepository.findByMemberAndStatusesWithOffset(
			memberId,
			statuses,
			pageRequest
		);

		return new MemberMissionOffsetPageResponseDto(
			page.getContent().stream()
				.map(memberMissionConverter::toSummary)
				.toList(),
			page.getNumber(),
			page.getSize(),
			page.getTotalElements(),
			page.getTotalPages(),
			page.isFirst(),
			page.isLast()
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
