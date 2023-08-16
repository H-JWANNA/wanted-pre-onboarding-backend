package com.wanted.preonboarding.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.security.MemberDetails;

import jakarta.validation.constraints.Positive;
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
  
	@GetMapping
	public ResponseEntity getPosts(
		@RequestParam @Positive int page,
		@RequestParam @Positive int size) {

		PostMultiResponse<PostResponse> responses = postService.getPosts(page - 1, size);

		return ResponseEntity.status(HttpStatus.OK).body(responses);
	}

	@GetMapping("/{postId}")
	public ResponseEntity getPost(@PathVariable @Positive Long postId) {

		PostResponse response = postService.getPost(postId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/{postId}")
	public ResponseEntity update(
		@PathVariable @Positive Long postId,
		@RequestBody PostUpdateRequest request,
		@AuthenticationPrincipal MemberDetails member) {

		PostResponse response = postService.update(postId, request, member);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity delete(
		@PathVariable @Positive Long postId,
		@AuthenticationPrincipal MemberDetails member) {

		postService.delete(postId, member);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
