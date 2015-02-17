/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.multimedia.config;

import com.multimedia.config.context.DaoConfig;
import com.multimedia.config.context.DataSourceConfig;
import com.netstorm.localization.MyLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import test.annotations.formatter.DateFormatter;
import test.annotations.formatter.HtmlSpecialCharsFormatterFactory;

@Configuration
@EnableWebMvc
@ComponentScan({
        "com.multimedia.config.web",
        "gallery.web.controller.ajax", "gallery.web.controller.wallpaper",
        "gallery.web.controller.misc"})
@PropertySource("classpath:gallery/misc/wallpaper.properties")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Environment env;
    @Autowired
    private DaoConfig daoConfig;
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(entityManagerInViewInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico", "favicon.png", "/robots.txt", "/*.xml")
                .addResourceLocations("/");
        registry.addResourceHandler("/login.jsp").addResourceLocations("/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/");
        registry.addResourceHandler("/styles/**").addResourceLocations("/styles/");
        //TODO: create a separate controller for this (not to show inactive images)
        registry.addResourceHandler("/images/**")
                .addResourceLocations(env.resolvePlaceholders("${wallpaper.store.dir}"));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new HtmlSpecialCharsFormatterFactory());
        registry.addFormatterForFieldType(
                java.util.Date.class, new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @Bean
    public OpenEntityManagerInViewInterceptor entityManagerInViewInterceptor() {
        OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(dataSourceConfig.entityManagerFactory().getObject());
        return interceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver("/cms/");
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/common");
        return messageSource;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        return multipartResolver;
    }

}
