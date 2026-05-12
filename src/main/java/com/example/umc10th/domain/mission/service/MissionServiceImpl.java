package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MissionServiceImpl implements MissionService {

	private final MissionRepository missionRepository;
	private final MissionConverter missionConverter;

	public MissionServiceImpl(MissionRepository missionRepository, MissionConverter missionConverter) {
		this.missionRepository = missionRepository;
		this.missionConverter = missionConverter;
	}

	@Override
	@Transactional(readOnly = true)
	public MissionResponseDto getMission(Long missionId) {
		return missionRepository.findById(missionId)
			.map(missionConverter::toResponse)
			.orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
	}
}
