package com.wanted.preonboarding.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.member.LoginResponse;
import com.wanted.preonboarding.member.Member;
import com.wanted.preonboarding.member.MemberMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
	private final MemberMapper mapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		Member member = (Member) authentication.getPrincipal();
		LoginResponse loginResponse = mapper.toLoginResponse(member);
		String body = new ObjectMapper().writeValueAsString(loginResponse);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(body);

		log.info("# Authenticated Successfully, email: {}", loginResponse.email());
	}
}
