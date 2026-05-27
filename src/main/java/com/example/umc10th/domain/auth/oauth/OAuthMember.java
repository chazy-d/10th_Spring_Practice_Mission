package com.example.umc10th.domain.auth.oauth;

import com.example.umc10th.domain.member.entity.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuthMember implements OAuth2User {

	private static final String DEFAULT_ROLE = "ROLE_USER";

	private final Member member;
	private final Map<String, Object> attributes;
	private final Collection<? extends GrantedAuthority> authorities;

	public OAuthMember(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
		this.authorities = List.of(new SimpleGrantedAuthority(DEFAULT_ROLE));
	}

	public Member getMember() {
		return member;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return member.getSocialUid();
	}
}
