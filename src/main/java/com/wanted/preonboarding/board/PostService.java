package com.wanted.preonboarding.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.member.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostMapper postMapper;
	private final PostRepository postRepository;

	@Transactional
	public PostResponse write(PostRequest dto, Member member) {
		Post post = postMapper.toEntity(dto, member);
		Post savedPost = postRepository.save(post);

		return postMapper.toResponse(savedPost);
	}
}
