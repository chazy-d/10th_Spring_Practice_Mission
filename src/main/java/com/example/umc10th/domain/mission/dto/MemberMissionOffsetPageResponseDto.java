package com.example.umc10th.domain.mission.dto;

import java.util.List;

public record MemberMissionOffsetPageResponseDto(
	List<MemberMissionListResponseDto.MemberMissionSummaryDto> missions,
	Integer pageNumber,
	Integer pageSize,
	Long totalElements,
	Integer totalPages,
	Boolean isFirst,
	Boolean isLast
) {
}
