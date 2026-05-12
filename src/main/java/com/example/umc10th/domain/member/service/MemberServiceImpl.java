package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResponseDto;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberConverter memberConverter;

	public MemberServiceImpl(MemberRepository memberRepository, MemberConverter memberConverter) {
		this.memberRepository = memberRepository;
		this.memberConverter = memberConverter;
	}

	@Override
	public MemberResponseDto getMember(Long memberId) {
		return getMyPage(memberId);
	}

	@Override
	public MemberResponseDto getMyPage(Long memberId) {
		return memberRepository.findById(memberId)
			.map(memberConverter::toResponse)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
	}
}
