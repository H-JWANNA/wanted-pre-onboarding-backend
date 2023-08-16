package com.wanted.preonboarding.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Transactional(readOnly = true)
	public PostMultiResponse<PostResponse> getPosts(int page, int size) {
		Page<Post> pagedPost = postRepository.findAll(PageRequest.of(page, size));

		List<PostResponse> responses = pagedPost.getContent().stream()
			.map(postMapper::toResponse)
			.toList();

		return new PostMultiResponse<>(responses,
			new PostMultiResponse.PageInfo(
				pagedPost.getNumber() + 1,
				pagedPost.getSize(),
				pagedPost.getTotalPages(),
				pagedPost.getTotalElements()));
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long postId) {
		Post post = verifyExistPost(postId);

		return postMapper.toResponse(post);
	}

	private Post verifyExistPost(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
	}
}
