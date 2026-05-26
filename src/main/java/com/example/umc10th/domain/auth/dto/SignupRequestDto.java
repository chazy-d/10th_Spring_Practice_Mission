package com.example.umc10th.domain.auth.dto;

import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

	@NotNull(message = "지역 ID는 필수입니다.")
	@Positive(message = "지역 ID는 양수여야 합니다.")
	Long regionId,

	@NotBlank(message = "주소는 필수입니다.")
	@Size(max = 255, message = "주소는 최대 255자까지 가능합니다.")
	String address,

	@NotBlank(message = "상세 주소는 필수입니다.")
	@Size(max = 255, message = "상세 주소는 최대 255자까지 가능합니다.")
	String addressDetail,

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@Size(max = 100, message = "이메일은 최대 100자까지 가능합니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하로 입력해주세요.")
	String password,

	@Size(max = 20, message = "전화번호는 최대 20자까지 가능합니다.")
	String phoneNumber,

	@Valid
	@NotEmpty(message = "약관 동의 목록은 최소 1개 이상이어야 합니다.")
	List<TermAgreementRequest> terms,

	@NotNull(message = "선호 음식 카테고리 목록은 필수입니다.")
	List<
		@NotNull(message = "선호 음식 카테고리 ID는 필수입니다.")
		@Positive(message = "선호 음식 카테고리 ID는 양수여야 합니다.")
		Long> favoriteFoodCategoryIds
) {
	public record TermAgreementRequest(
		@NotNull(message = "약관 ID는 필수입니다.")
		@Positive(message = "약관 ID는 양수여야 합니다.")
		Long termId,

		@NotNull(message = "약관 동의 여부는 필수입니다.")
		Boolean isAgree
	) {
	}
}
