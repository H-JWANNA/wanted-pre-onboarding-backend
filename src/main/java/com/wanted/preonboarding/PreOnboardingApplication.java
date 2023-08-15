package com.wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/env.properties")
public class PreOnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreOnboardingApplication.class, args);
	}

}
