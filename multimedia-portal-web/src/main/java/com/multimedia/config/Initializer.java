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

import com.multimedia.security.filter.SecureAntispam;
import common.web.servlets.RandomImageServ;
import net.sf.ehcache.constructs.web.filter.SimpleCachingHeadersPageCachingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

//TODO: I switched to web.xml because this is not working with embedded servers
//public class Initializer implements WebApplicationInitializer {
public class Initializer {
    private final Logger logger = LoggerFactory.getLogger(Initializer.class);

//    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("Initializing servlets, listeners, filters started.");

        registerRootContext(servletContext);
        registerMvcContext(servletContext);
        registerEncodingFilter(servletContext);
        registerSecurityFilter(servletContext);
        registerAntispamImage(servletContext);
        registerAntispam(servletContext);
        registerCacheFilter(servletContext);

        logger.info("Initializing servlets, listeners, filters finished.");
    }

    private void registerEncodingFilter(ServletContext servletContext) {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic filterReg = servletContext.addFilter(
                "encodingFilter", encodingFilter);
        filterReg.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                true, "/*");
    }

    private static void registerCacheFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic cacheFilter = servletContext.addFilter(
                "cacheFilter", new SimpleCachingHeadersPageCachingFilter());
        cacheFilter.setInitParameter("cacheName", "ajax_rubrication");
        cacheFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                true, "/ajax.json");
    }

    private static void registerAntispamImage(ServletContext servletContext) {
        ServletRegistration.Dynamic randomImageServ = servletContext.addServlet(
                "antiSpamCode", new RandomImageServ());
        randomImageServ.addMapping("/code.jpg");
    }

    private static void registerRootContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class, SecurityProdConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }

    private void registerMvcContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext mvcContext =
                new AnnotationConfigWebApplicationContext();
        mvcContext.register(WebConfig.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "dispatcher", new DispatcherServlet(mvcContext));
        dispatcher.setLoadOnStartup(0);
        dispatcher.setInitParameter("cleanupAfterInclude", "false");
        dispatcher.addMapping("/");
    }

    private static void registerAntispam(ServletContext servletContext) {
        FilterRegistration.Dynamic antispam =
                servletContext.addFilter("antispam", new SecureAntispam());
        antispam.setInitParameter("roles", "admin");
        antispam.setInitParameter("view_url", "/WEB-INF/jsp/view/antispamCode.jsp");
        antispam.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                true, "/images/wallpaper/full/*");
        antispam.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                true, "/images/wallpaper/resolution/*");
    }

    private void registerSecurityFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic securityFilter =
                servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        securityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                true, "/*");
    }
}
