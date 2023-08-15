package com.wanted.preonboarding.member;

public record RegisterRequest(
	String email,
	String password
) {
	public RegisterRequest {
		if (email == null || !email.matches(".*@.*")) {
			throw new IllegalArgumentException("@를 사용한 올바른 이메일 형식을 작성해주세요.");
		}

		if (password == null || password.length() < 8) {
			throw new IllegalArgumentException("비밀번호는 8자리 이상으로 설정해주세요.");
		}
	}
}
