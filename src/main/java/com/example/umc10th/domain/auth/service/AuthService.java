package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.dto.LoginRequestDto;
import com.example.umc10th.domain.auth.dto.LoginResponseDto;
import com.example.umc10th.domain.auth.dto.SignupRequestDto;
import com.example.umc10th.domain.auth.dto.SignupResponseDto;

public interface AuthService {

	SignupResponseDto signup(SignupRequestDto request);

	LoginResponseDto login(LoginRequestDto request);
}
