package com.example.umc10th.api.home.service;

import com.example.umc10th.api.home.converter.HomeConverter;
import com.example.umc10th.api.home.dto.HomeResponseDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.region.entity.MemberRegionGoal;
import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.entity.RegionGoal;
import com.example.umc10th.domain.region.exception.RegionException;
import com.example.umc10th.domain.region.exception.code.RegionErrorCode;
import com.example.umc10th.domain.region.repository.MemberRegionGoalRepository;
import com.example.umc10th.domain.region.repository.RegionGoalRepository;
import com.example.umc10th.domain.region.repository.RegionRepository;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HomeServiceImpl implements HomeService {

	private static final List<MemberMissionStatus> IN_PROGRESS_STATUSES = List.of(
		MemberMissionStatus.IN_PROGRESS,
		MemberMissionStatus.SUCCESS_REQUESTED
	);

	private final MemberRepository memberRepository;
	private final RegionRepository regionRepository;
	private final RegionGoalRepository regionGoalRepository;
	private final MemberRegionGoalRepository memberRegionGoalRepository;
	private final MissionRepository missionRepository;
	private final HomeConverter homeConverter = new HomeConverter();

	public HomeServiceImpl(
		MemberRepository memberRepository,
		RegionRepository regionRepository,
		RegionGoalRepository regionGoalRepository,
		MemberRegionGoalRepository memberRegionGoalRepository,
		MissionRepository missionRepository
	) {
		this.memberRepository = memberRepository;
		this.regionRepository = regionRepository;
		this.regionGoalRepository = regionGoalRepository;
		this.memberRegionGoalRepository = memberRegionGoalRepository;
		this.missionRepository = missionRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public HomeResponseDto getHome(Long memberId, Long regionId, Long cursor, Integer size) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
		Region region = regionRepository.findById(regionId)
			.orElseThrow(() -> new RegionException(RegionErrorCode.REGION_NOT_FOUND));

		RegionGoal regionGoal = regionGoalRepository.findByRegionIdAndTargetMonth(regionId, YearMonth.now())
			.orElse(null);
		Integer goalMissionCount = regionGoal == null ? 0 : regionGoal.getGoalMissionCount();
		Integer rewardPoint = regionGoal == null ? 0 : regionGoal.getRewardPoint();
		Integer completedMissionCount = getCompletedMissionCount(memberId, regionGoal);

		List<Mission> missions = missionRepository.findAvailableMissionsWithCursor(
			memberId,
			regionId,
			IN_PROGRESS_STATUSES,
			LocalDateTime.now(),
			cursor,
			PageRequest.of(0, size + 1)
		);

		boolean hasNext = missions.size() > size;
		List<Mission> responseMissions = hasNext ? missions.subList(0, size) : missions;
		Long nextCursor = hasNext && !responseMissions.isEmpty()
			? responseMissions.get(responseMissions.size() - 1).getId()
			: null;

		return new HomeResponseDto(
			member.getPoint(),
			region.getId(),
			region.getName(),
			completedMissionCount,
			goalMissionCount,
			rewardPoint,
			responseMissions.stream()
				.map(homeConverter::toHomeMissionDto)
				.toList(),
			size,
			nextCursor,
			hasNext
		);
	}

	private Integer getCompletedMissionCount(Long memberId, RegionGoal regionGoal) {
		if (regionGoal == null) {
			return 0;
		}

		return memberRegionGoalRepository.findByMemberIdAndRegionGoalId(memberId, regionGoal.getId())
			.map(MemberRegionGoal::getCompletedMissionCount)
			.orElse(0);
	}
}
