package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.SignupRequestDto;
import com.example.umc10th.domain.member.dto.SignupResponseDto;

public interface AuthService {

	SignupResponseDto signup(SignupRequestDto request);
}
