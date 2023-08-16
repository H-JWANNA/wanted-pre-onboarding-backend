package com.wanted.preonboarding.board;

public record PostResponse(
	Long postId,
	String title,
	String content,
	Long author
) {
}
