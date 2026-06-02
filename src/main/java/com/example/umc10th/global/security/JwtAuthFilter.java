package com.example.umc10th.global.security;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		try {
			// 토큰 가져오기
			String token = request.getHeader("Authorization");
			// token이 없거나 Bearer가 아니면 넘기기
			if (token == null || !token.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			// Bearer이면 추출
			token = token.replace("Bearer ", "");
			Long memberId = jwtUtil.getMemberId(token);
			if (memberId == null) {
				throw new IllegalArgumentException("Invalid JWT subject.");
			}
			UserDetails user = customUserDetailsService.loadUserByMemberId(memberId);
			Authentication auth = new UsernamePasswordAuthenticationToken(
				user,
				null,
				user.getAuthorities()
			);
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			ObjectMapper mapper = new ObjectMapper();
			BaseErrorCode code = GeneralErrorCode.UNAUTHORIZED;

			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(code.getStatus().value());

			ApiResponse<Void> errorResponse = ApiResponse.onFailure(code, null);

			mapper.writeValue(response.getOutputStream(), errorResponse);
		}
	}
}
