package com.example.umc10th.api.home.service;

import com.example.umc10th.api.home.dto.HomeResponseDto;

public interface HomeService {

	HomeResponseDto getHome(Long memberId, Long regionId, Long cursor, Integer size);
}
