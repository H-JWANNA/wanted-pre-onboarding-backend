package com.wanted.preonboarding.security;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.wanted.preonboarding.member.MemberMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final MemberDetailsService detailsService;
	private final MemberMapper mapper;
	private final JwtTokenizer jwtTokenizer;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers(h -> h.frameOptions(withDefaults()))
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.apply(new CustomFilterConfigure())
			.and()
			.authorizeHttpRequests(auth -> {
				auth.requestMatchers("/members/**").permitAll();
				auth.anyRequest().permitAll();
			});

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public class CustomFilterConfigure extends AbstractHttpConfigurer<CustomFilterConfigure, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter =
				new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
			jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthSuccessHandler(mapper));
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(detailsService, jwtTokenizer);

			builder
				.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}
}
