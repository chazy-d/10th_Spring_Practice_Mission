package com.example.umc10th.domain.auth.oauth.dto;

import com.example.umc10th.domain.member.enums.SocialType;

public record KakaoDTO(
	String socialUid,
	String email,
	String nickname,
	String profileImageUrl
) implements OAuthDTO {

	@Override
	public SocialType socialType() {
		return SocialType.KAKAO;
	}
}
