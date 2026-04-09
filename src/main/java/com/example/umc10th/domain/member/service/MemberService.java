package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberResponseDto;

public interface MemberService {

	MemberResponseDto getMember(Long memberId);
}
