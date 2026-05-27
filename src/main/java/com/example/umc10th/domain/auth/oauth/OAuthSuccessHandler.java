package com.example.umc10th.domain.auth.oauth;

import com.example.umc10th.domain.auth.dto.LoginResponseDto;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.AuthMember;
import com.example.umc10th.global.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {
		OAuthMember oAuthMember = (OAuthMember) authentication.getPrincipal();
		String accessToken = jwtUtil.createAccessToken(AuthMember.from(oAuthMember.getMember()));

		response.setStatus(MemberSuccessCode.MEMBER_LOGIN.getStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		ApiResponse<LoginResponseDto> responseBody = ApiResponse.onSuccess(
			MemberSuccessCode.MEMBER_LOGIN,
			new LoginResponseDto(accessToken)
		);
		objectMapper.writeValue(response.getWriter(), responseBody);
	}
}
