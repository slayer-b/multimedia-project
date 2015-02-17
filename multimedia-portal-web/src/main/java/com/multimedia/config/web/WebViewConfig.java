package com.multimedia.config.web;

import gallery.web.controller.pages.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebViewConfig {

    @Bean
    public Config commonConf() {
        Config config = new Config();
        config.setTemplateUrl("/WEB-INF/jsp/view/template.jsp");
        return config;
    }

}
