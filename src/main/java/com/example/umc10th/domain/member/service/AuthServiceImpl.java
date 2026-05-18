package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.SignupRequestDto;
import com.example.umc10th.domain.member.dto.SignupResponseDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public SignupResponseDto signup(SignupRequestDto request) {
		if (memberRepository.existsByEmail(request.email())) {
			throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
		}

		String passwordHash = passwordEncoder.encode(request.password());
		Member member = Member.createLocal(
			request.name(),
			request.nickname(),
			request.gender(),
			request.birthDate(),
			request.email(),
			passwordHash,
			request.phoneNumber()
		);

		Member savedMember = memberRepository.save(member);
		LocalDateTime createdAt = savedMember.getCreatedAt() != null
			? savedMember.getCreatedAt()
			: LocalDateTime.now();

		return new SignupResponseDto(
			savedMember.getId(),
			savedMember.getNickname(),
			request.regionId(),
			savedMember.getEmail(),
			savedMember.getPhoneNumber(),
			createdAt
		);
	}
}
