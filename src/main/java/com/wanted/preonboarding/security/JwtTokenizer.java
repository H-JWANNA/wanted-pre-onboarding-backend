package com.wanted.preonboarding.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Getter
@Component
public class JwtTokenizer {
	private final String secretKey = "TestJwtSecretKeyTestJwtSecretKey12451927839721";
	private final int accessTokenExpirationMinutes = 60 * 60 * 1000;
	private final int refreshTokenExpirationMinutes = 24 * 60 * 60 * 1000;

	public String encodeBase64SecretKey(String secretKey) {
		return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(
		Map<String, Object> claims,
		String subject,
		Date expiration,
		String base64EncodedSecretKey) {

		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		JwtBuilder builder = Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setExpiration(expiration)
			.signWith(key);

		return builder.compact();
	}

	public String generateRefreshToken(
		String subject,
		Date expiration,
		String base64EncodedSecretKey) {

		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		JwtBuilder builder = Jwts.builder()
			.setSubject(subject)
			.setIssuedAt(Calendar.getInstance().getTime())
			.setExpiration(expiration)
			.signWith(key);

		return builder.compact();
	}

	public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
		Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws);
	}

	public Date getTokenExpiration(int expirationMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, expirationMinutes);

		return calendar.getTime();
	}

	private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}
}
