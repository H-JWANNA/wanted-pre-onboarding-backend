package com.wanted.preonboarding.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	private final MemberDetailsService detailsService;
	private final JwtTokenizer jwtTokenizer;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			Map<String, Object> claims = verifyJws(request);
			setAuthenticationToContext(claims, request);
		} catch (SignatureException e) {
			request.setAttribute("SignatureException", e);
		} catch (ExpiredJwtException e) {
			request.setAttribute("ExpiredJwtException", e);
		} catch (Exception e) {
			request.setAttribute("Exception", e);
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthenticationToContext(Map<String, Object> claims, HttpServletRequest request) {
		String username = (String)claims.get("username");

		MemberDetails userDetails = (MemberDetails)detailsService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, null);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Map<String, Object> verifyJws(HttpServletRequest request) {
		String jws = request.getHeader("Authorization").replace("Bearer", "");
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		return jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
	}
}
