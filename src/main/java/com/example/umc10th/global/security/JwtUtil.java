package com.example.umc10th.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private final SecretKey secretKey;
	private final Duration accessExpiration;

	public JwtUtil(
		@Value("${jwt.token.secretKey}") String secret,
		@Value("${jwt.token.expiration.access}") Long accessExpiration
	) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessExpiration = Duration.ofMillis(accessExpiration);
	}

	// AccessToken 생성
	public String createAccessToken(AuthMember member) {
		return createToken(member, accessExpiration);
	}

	/**
	 * 토큰에서 회원 ID 가져오기
	 *
	 * @param token 유저 정보를 추출할 토큰
	 * @return 유저 ID를 토큰에서 추출합니다
	 */
	public Long getMemberId(String token) {
		try {
			String subject = getClaims(token).getPayload().getSubject();
			return Long.valueOf(subject);
		} catch (JwtException | NumberFormatException e) {
			return null;
		}
	}

	// 토큰 생성
	private String createToken(AuthMember member, Duration expiration) {
		Instant now = Instant.now();

		return Jwts.builder()
			.subject(String.valueOf(member.getMemberId()))
			.issuedAt(Date.from(now)) // 언제 발급한지
			.expiration(Date.from(now.plus(expiration))) // 언제까지 유효한지
			.signWith(secretKey) // sign할 Key
			.compact();
	}

	// 토큰 정보 가져오기
	private Jws<Claims> getClaims(String token) throws JwtException {
		return Jwts.parser()
			.verifyWith(secretKey)
			.clockSkewSeconds(60)
			.build()
			.parseSignedClaims(token);
	}
}
