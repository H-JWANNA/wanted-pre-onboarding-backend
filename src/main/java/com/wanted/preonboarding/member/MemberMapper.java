package com.wanted.preonboarding.member;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

	@Mapping(source = "request.email", target = "email")
	@Mapping(source = "encryptedPassword", target = "password")
	Member toEntity(RegisterRequest request, String encryptedPassword);

	LoginResponse toLoginResponse(Member member);
}
