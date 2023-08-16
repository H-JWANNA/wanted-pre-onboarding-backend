package com.wanted.preonboarding.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.security.MemberDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity write(@RequestBody PostRequest request,
		@AuthenticationPrincipal MemberDetails member) {

		PostResponse response = postService.write(request, member);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
