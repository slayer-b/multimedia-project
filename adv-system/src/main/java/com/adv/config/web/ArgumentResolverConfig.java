package com.adv.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adv.web.support.BlockContentArgumentResolver;

@Configuration
public class ArgumentResolverConfig {

    @Bean
    public BlockContentArgumentResolver blockContentArgumentResolver() {
        return new BlockContentArgumentResolver();
    }

}
