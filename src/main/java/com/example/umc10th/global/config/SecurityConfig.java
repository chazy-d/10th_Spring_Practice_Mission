package com.example.umc10th.global.config;

import com.example.umc10th.global.security.CustomAccessDeniedHandler;
import com.example.umc10th.global.security.CustomAuthenticationEntryPoint;
import com.example.umc10th.global.security.CustomUserDetailsService;
import com.example.umc10th.global.security.JwtAuthFilter;
import com.example.umc10th.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	private static final String[] PUBLIC_URLS = {
		"/api/auth/**",
		"/swagger-ui/**",
		"/swagger-ui.html",
		"/v3/api-docs/**",
		"/error"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(PUBLIC_URLS).permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.permitAll()
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthFilter jwtAuthFilter() {
		return new JwtAuthFilter(jwtUtil, customUserDetailsService);
	}
}
