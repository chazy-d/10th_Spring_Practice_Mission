package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record SignupRequestDto(
	@NotBlank(message = "닉네임은 필수입니다.")
	@Size(max = 50, message = "닉네임은 최대 50자까지 가능합니다.")
	String nickname,

	@NotBlank(message = "이름은 필수입니다.")
	@Size(max = 50, message = "이름은 최대 50자까지 가능합니다.")
	String name,

	Gender gender,
	LocalDate birthDate,
	Long regionId,
	String addressDetail,

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@Size(max = 100, message = "이메일은 최대 100자까지 가능합니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하로 입력해주세요.")
	String password,

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
