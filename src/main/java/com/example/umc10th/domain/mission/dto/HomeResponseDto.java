package com.example.umc10th.domain.mission.dto;

import java.util.List;

public record HomeResponseDto(
	Integer userPoint,
	Long regionId,
	String regionName,
	Integer completedMissionCount,
	Integer goalMissionCount,
	Integer rewardPoint,
	List<HomeMissionDto> missions,
	Integer size,
	Long nextCursor,
	Boolean hasNext
) {
	public record HomeMissionDto(
		Long missionId,
		Long storeId,
		String storeName,
		String missionContent,
		Integer missionPoint,
		Integer daysRemaining
	) {
	}
}
