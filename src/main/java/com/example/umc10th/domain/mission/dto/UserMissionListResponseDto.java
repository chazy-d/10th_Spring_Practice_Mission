package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import java.time.LocalDateTime;
import java.util.List;

public record UserMissionListResponseDto(
	UserMissionStatus status,
	List<UserMissionSummaryDto> missions,
	Integer size,
	Long nextCursor,
	Boolean hasNext
) {
	public record UserMissionSummaryDto(
		Long userMissionId,
		Long missionId,
		Long storeId,
		String storeName,
		String missionContent,
		Integer missionPoint,
		UserMissionStatus status,
		LocalDateTime challengedAt,
		LocalDateTime successRequestedAt,
		LocalDateTime completedAt,
		Integer daysRemaining
	) {
	}
}
