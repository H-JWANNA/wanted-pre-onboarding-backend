package com.wanted.preonboarding.jwt;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.wanted.preonboarding.security.JwtTokenizer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
	private static JwtTokenizer jwtTokenizer;
	private String secretKey;
	private String base64EncodedSecretKey;

	@BeforeAll
	public void init() {
		jwtTokenizer = new JwtTokenizer();
		secretKey = "TestJwtSecretKeyTestJwtSecretKey12451927839721";
		base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
	}

	@Test
	public void generateAccessTokenTest() {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", 1);

		String subject = "test access token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 60);
		Date expiration = calendar.getTime();

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		System.out.println("accessToken = " + accessToken);

		assertThat(accessToken, notNullValue());
	}

	@Test
	public void generateRefreshTokenTest() {
		String subject = "test refresh token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 24);
		Date expiration = calendar.getTime();

		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		System.out.println("refreshToken = " + refreshToken);

		assertThat(refreshToken, notNullValue());
	}
}
