package com.prolancer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
	
	@Value("${spring.profiles.active}")
	private String activeProfile;

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.properties("spring.config.location=classpath:/application.yml").run(args);
	}

}
