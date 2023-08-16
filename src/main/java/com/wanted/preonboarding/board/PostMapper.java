package com.wanted.preonboarding.board;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.wanted.preonboarding.member.Member;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

	@Mapping(source = "dto.title", target = "title")
	@Mapping(source = "dto.content", target = "content")
	@Mapping(source = "member.memberId", target = "author")
	Post toEntity(PostRequest dto, Member member);

	Post toEntity(PostRequest dto);

	PostResponse toResponse(Post entity);
}
