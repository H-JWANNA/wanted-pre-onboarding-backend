package com.wanted.preonboarding.board;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record PostRequest(
	@NonNull
	@NotBlank(message = "제목을 작성해주세요.")
	String title,

	@NonNull()
	@NotBlank(message = "내용을 작성해주세요.")
	String content
) {
}
