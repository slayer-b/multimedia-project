package com.multimedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource("classpath:gallery/spring-security-dev.xml")
@Profile("dev")
public class SecurityDevConfig {
}
