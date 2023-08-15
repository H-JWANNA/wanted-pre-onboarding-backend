package com.wanted.preonboarding.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity register(@RequestBody @Valid RegisterRequest request) {
		Long savedMemberId = memberService.register(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedMemberId);
	}
}
