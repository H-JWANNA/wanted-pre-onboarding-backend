package com.wanted.preonboarding.member;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
	@Pattern(regexp = ".*@.*", message = "@를 사용한 올바른 이메일 형식을 작성해주세요.")
	String email,

	@Size(min = 8, message = "비밀번호는 8자리 이상으로 설정해주세요.")
	String password
) {
}
