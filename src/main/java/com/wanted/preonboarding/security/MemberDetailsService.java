package com.wanted.preonboarding.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wanted.preonboarding.member.Member;
import com.wanted.preonboarding.member.MemberRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findByEmail(username);
		Member member = optionalMember.orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		return new MemberDetails(member);
	}
}
