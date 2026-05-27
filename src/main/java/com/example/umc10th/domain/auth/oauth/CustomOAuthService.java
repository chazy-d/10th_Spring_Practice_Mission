package com.example.umc10th.domain.auth.oauth;

import com.example.umc10th.domain.auth.oauth.dto.KakaoDTO;
import com.example.umc10th.domain.auth.oauth.dto.OAuthDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {

	private static final String KAKAO_ACCOUNT = "kakao_account";
	private static final String PROFILE = "profile";
	private static final String DEFAULT_NICKNAME_PREFIX = "kakao_user_";

	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuthMember = super.loadUser(userRequest);

		SocialType providerId = getProviderId(userRequest);
		String socialUid = getRequiredString(oAuthMember.getAttributes(), "id", "Kakao id is required.");

		OAuthDTO dto = switch (providerId) {
			case KAKAO -> toKakaoDto(oAuthMember, socialUid);
			default -> throw unsupportedProvider();
		};

		Member member = memberRepository.findBySocialTypeAndSocialUid(dto.socialType(), dto.socialUid())
			.orElseGet(() -> memberRepository.save(Member.createSocial(
				dto.socialType(),
				dto.socialUid(),
				dto.email(),
				dto.nickname(),
				dto.profileImageUrl()
			)));

		return new OAuthMember(member, oAuthMember.getAttributes());
	}

	private SocialType getProviderId(OAuth2UserRequest userRequest) {
		try {
			return SocialType.valueOf(userRequest.getClientRegistration()
				.getRegistrationId()
				.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw unsupportedProvider();
		}
	}

	@SuppressWarnings("unchecked")
	private KakaoDTO toKakaoDto(OAuth2User oAuthMember, String socialUid) {
		Map<String, Object> attributes = oAuthMember.getAttributes();
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get(KAKAO_ACCOUNT);
		if (kakaoAccount == null) {
			throw invalidUserInfo("Kakao account is required.");
		}

		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get(PROFILE);
		String email = getRequiredString(kakaoAccount, "email", "Kakao email is required.");
		String nickname = getString(profile, "nickname");
		String profileImageUrl = getString(profile, "profile_image_url");

		if (nickname == null || nickname.isBlank()) {
			nickname = DEFAULT_NICKNAME_PREFIX + socialUid;
		}

		return new KakaoDTO(socialUid, email, nickname, profileImageUrl);
	}

	private String getRequiredString(Map<String, Object> attributes, String key, String message) {
		String value = getString(attributes, key);
		if (value == null || value.isBlank()) {
			throw invalidUserInfo(message);
		}
		return value;
	}

	private String getString(Map<String, Object> attributes, String key) {
		if (attributes == null) {
			return null;
		}
		Object value = attributes.get(key);
		return value != null ? String.valueOf(value) : null;
	}

	private OAuth2AuthenticationException unsupportedProvider() {
		OAuth2Error error = new OAuth2Error(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER.getCode());
		return new OAuth2AuthenticationException(error, MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER.getMessage());
	}

	private OAuth2AuthenticationException invalidUserInfo(String message) {
		OAuth2Error error = new OAuth2Error("OAUTH_INVALID_USER_INFO");
		return new OAuth2AuthenticationException(error, message);
	}
}
