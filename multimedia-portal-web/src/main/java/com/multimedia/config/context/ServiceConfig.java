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

package com.multimedia.config.context;

import com.multimedia.dao.WallpaperRepository;
import com.multimedia.service.IPagesService;
import com.multimedia.service.impl.AdvertisementServiceImpl;
import com.multimedia.service.impl.ResolutionServiceImpl;
import com.multimedia.service.impl.RssService;
import com.multimedia.service.impl.RubricationService;
import com.multimedia.service.impl.SitemapService;
import com.multimedia.service.impl.WallpaperServiceImpl;
import common.email.MailServiceImpl;
import common.types.TypesCheckAndCorrect;
import gallery.web.support.wallpaper.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan({
        "com.multimedia.service", "gallery.service",
        "com.multimedia.cms.service", "core.cms.services2"
})
@PropertySource({"classpath:/gallery/misc/wallpaper.properties"})
public class ServiceConfig {
    @Autowired
    private Environment env;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private DaoConfig daoConfig;
    @Autowired
    private WallpaperRepository wallpaperRepository;
    @Autowired
    private IPagesService pagesService;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("email.host"));
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setUsername(env.getProperty("email.login"));
        mailSender.setPassword(env.getProperty("email.password"));
        mailSender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("mail.smtps.starttls.enable", "true");
        properties.setProperty("mail.smtps.debug", "true");
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    @Bean
    public MailServiceImpl emailService() {
        return new MailServiceImpl(
                mailSender(),
                ctx.getResource("WEB-INF/EmailTemplates/")
        );
    }

    @Bean
    public TypesCheckAndCorrect typesCheckAndCorrect() {
        return new TypesCheckAndCorrect();
    }

    @Bean
    public RubricationService rubricationService() {
        return
            new RubricationService(
                new FileSystemResource(env.resolvePlaceholders("${catalina.base}/temp/gallery/cache_rubrication/")),
                pagesService
            );
    }

    @Bean
    public WallpaperServiceImpl wallpaperService() {
        HashMap<String, Integer> dimensions = new HashMap<String, Integer>();
        dimensions.put("tiny", 50);
        dimensions.put("smaller", 90);
        dimensions.put("small", 150);
        dimensions.put("medium", 400);
        dimensions.put(Utils.FULL_DIMENSION_NAME, 5000);

        return new WallpaperServiceImpl(
                wallpaperRepository,
                resolutionService(),
                ctx.getResource(env.resolvePlaceholders("${wallpaper.images.dir}")),
                ctx.getResource(env.resolvePlaceholders("${wallpaper.resized.dir}")),
                ctx.getResource(env.resolvePlaceholders("${wallpaper.backup.dir}")),
                ctx.getResource(env.resolvePlaceholders("${wallpaper.upload.dir}")),
                new HashSet<String>(Arrays.asList("Другой", "Другие", "Другое", "Эротика", "Девушки")),
                dimensions
        );
    }

    @Bean
    public ResolutionServiceImpl resolutionService() {
        return new ResolutionServiceImpl(daoConfig.resolutionDAO());
    }

    @Bean
    public AdvertisementServiceImpl advertisementService() {
        return new AdvertisementServiceImpl(daoConfig.advertisementDAO(), advertisementPositions());
    }

    @Bean
    public List<String> advertisementPositions() {
        return Arrays.asList(
                "top", "content_top", "content_top_post", "content_bottom_pre",
                "content_bottom", "right_top", "right_bottom", "bottom");
    }

    @Bean
    public RssService rssCreatorJob() {
        return new RssService();
    }

    @Bean
    public SitemapService sitemapCreatorJob() {
        return new SitemapService();
    }
}
