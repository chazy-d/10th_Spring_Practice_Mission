package com.example.umc10th.global.security;

import com.example.umc10th.domain.member.entity.Member;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthMember implements UserDetails {

	private static final String DEFAULT_ROLE = "ROLE_USER";

	private final Long memberId;
	private final String email;
	private final String passwordHash;
	private final Collection<? extends GrantedAuthority> authorities;

	private AuthMember(Long memberId, String email, String passwordHash) {
		this.memberId = memberId;
		this.email = email;
		this.passwordHash = passwordHash != null ? passwordHash : "";
		this.authorities = List.of(new SimpleGrantedAuthority(DEFAULT_ROLE));
	}

	public static AuthMember from(Member member) {
		return new AuthMember(member.getId(), member.getEmail(), member.getPasswordHash());
	}

	public Long getMemberId() {
		return memberId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
