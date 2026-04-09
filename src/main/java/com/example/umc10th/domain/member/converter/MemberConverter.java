package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResponseDto;
import com.example.umc10th.domain.member.entity.Member;

public class MemberConverter {

	public MemberResponseDto toResponse(Member member) {
		return new MemberResponseDto(member.getId(), member.getName(), member.getEmail());
	}
}
