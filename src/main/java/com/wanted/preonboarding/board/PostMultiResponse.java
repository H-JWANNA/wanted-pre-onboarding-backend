package com.wanted.preonboarding.board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record PostMultiResponse<T> (
	List<T> posts,
	PageInfo pageInfo
	) {

	@Getter
	@AllArgsConstructor
	public static class PageInfo {
		private int page;
		private int size;
		private int totalPages;
		private long totalElements;
	}
}
