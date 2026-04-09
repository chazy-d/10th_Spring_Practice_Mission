package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.service.MissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missions")
public class MissionController {

	private final MissionService missionService;

	public MissionController(MissionService missionService) {
		this.missionService = missionService;
	}
}
