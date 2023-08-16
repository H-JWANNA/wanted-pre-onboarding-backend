package com.wanted.preonboarding.member;

public record LoginResponse(
	Long memberId,
	String email
) {
}
