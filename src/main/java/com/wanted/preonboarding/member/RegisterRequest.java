package com.wanted.preonboarding.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
	@NotBlank(message = "사용자 이름을 입력해주세요.")
	@Pattern(regexp = ".*@.*", message = "@를 사용한 올바른 이메일 형식을 작성해주세요.")
	String email,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(min = 8, message = "비밀번호는 8자리 이상으로 설정해주세요.")
	String password
) {
}
