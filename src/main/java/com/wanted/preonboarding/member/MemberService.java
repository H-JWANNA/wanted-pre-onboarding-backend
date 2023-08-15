package com.wanted.preonboarding.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;

	@Transactional
	public Long register(RegisterRequest request) {
		String encryptedPassword = passwordEncoder.encode(request.password());
		Member member = memberMapper.toEntity(request, encryptedPassword);
		Member savedMember = memberRepository.save(member);

		return savedMember.getMemberId();
	}
}
