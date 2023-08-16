package com.wanted.preonboarding.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.member.LoginRequest;
import com.wanted.preonboarding.member.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenizer jwtTokenizer;

	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) {
		ObjectMapper objectMapper = new ObjectMapper();

		LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
			LoginRequest.class);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			loginRequest.username(),
			loginRequest.password());

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult) throws ServletException, IOException {

		Member member = (Member)authResult.getPrincipal();

		String accessToken = delegateAccessToken(member);
		Map<String, Object> map = delegateRefreshToken(member);
		String refreshToken = (String)map.get("refresh");

		response.setHeader("Authorization", "Bearer " + accessToken);
		response.setHeader("Refresh", refreshToken);

		this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

	}

	private String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", member.getMemberId());
		claims.put("username", member.getEmail());

		String subject = member.getEmail();

		Date expiration =
			jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

		String base64EncodedSecretKey =
			jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String accessToken =
			jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		return
			accessToken;
	}

	private Map<String, Object> delegateRefreshToken(Member member) {
		String subject = member.getEmail();

		Date expiration = jwtTokenizer.
			getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());

		String base64EncodedSecretKey =
			jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String refreshToken =
			jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		Map<String, Object> map = new HashMap<>();
		map.put("refresh", refreshToken);
		map.put("expiration", expiration);

		return map;
	}
}
