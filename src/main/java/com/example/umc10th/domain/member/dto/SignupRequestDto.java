package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import java.time.LocalDate;
import java.util.List;

public record SignupRequestDto(
	String nickname,
	String name,
	Gender gender,
	LocalDate birthDate,
	Long regionId,
	String addressDetail,
	String email,
	String phoneNumber,
	List<TermAgreementRequest> terms,
	List<Long> favoriteFoodCategoryIds
) {
	public record TermAgreementRequest(
		Long termId,
		Boolean isAgree
	) {
	}
}
