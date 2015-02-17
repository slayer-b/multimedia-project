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

package com.multimedia.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.multimedia.cms.config.PagesCmsConfig;

import common.cms.config.CmsConfig;

@Configuration
@ComponentScan({
    "com.multimedia.cms.config",
    "com.multimedia.cms.controller",
    "gallery.web.controller"})
public class CmsWebConfig {

    @Bean
    public CmsConfig commonCmsConf() {
        CmsConfig cmsConfig = new CmsConfig();
        cmsConfig.setTemplateUrl("/WEB-INF/jsp/cms/main.jsp");
        return cmsConfig;
    }

    @Bean
    public PagesCmsConfig pagesCmsConfig() {
        PagesCmsConfig pagesCmsConfig = new PagesCmsConfig();
        pagesCmsConfig.setConf(commonCmsConf());
        return pagesCmsConfig;
    }

    @Bean
    public com.multimedia.cms.config.UserCmsConfig usersConfig() {
        com.multimedia.cms.config.UserCmsConfig c = new com.multimedia.cms.config.UserCmsConfig();
        c.setConf(commonCmsConf());
        c.setAvaibleRoles(new String[]{"admin", "user"});
        return c;
    }
    
    @Bean
    public CmsConfig wallpaperCommentConfig() {
        CmsConfig config = new CmsConfig();
        config.setConfig(commonCmsConf());
        config.setNameRu("Wallpaper comment");
        config.setNavigationTemplate("/WEB-INF/jspf/cms/wallpaperComment/navigation.jsp");
        config.setContentViewTemplate("/WEB-INF/jspf/cms/wallpaperComment/show.jsp");
        config.setContentInsertTemplate("/WEB-INF/jspf/cms/wallpaperComment/insert.jsp");
        config.setContentUpdateTemplate("/WEB-INF/jspf/cms/wallpaperComment/update.jsp");
        config.setContentMultiupdateTemplate("/WEB-INF/jspf/cms/wallpaperComment/show.jsp");
        return config;
    }

    @Bean
    public CmsConfig advertisementConfig() {
        CmsConfig config = new CmsConfig();
        config.setConfig(commonCmsConf());
        config.setNameRu("Реклама");
        config.setNavigationTemplate("/WEB-INF/jspf/cms/advertisement/navigation.jsp");
        config.setContentViewTemplate("/WEB-INF/jspf/cms/advertisement/show.jsp");
        config.setContentInsertTemplate("/WEB-INF/jspf/cms/advertisement/insert.jsp");
        config.setContentUpdateTemplate("/WEB-INF/jspf/cms/advertisement/update.jsp");
        config.setContentMultiupdateTemplate("/WEB-INF/jspf/cms/advertisement/show.jsp");
        return config;
    }

    @Bean
    public CmsConfig advertisementPagesConfig() {
        CmsConfig config = new CmsConfig();
        config.setConfig(commonCmsConf());
        config.setNameRu("Реклама_страницы");
        config.setNavigationTemplate("/WEB-INF/jspf/cms/advertisementPages/navigation.jsp");
        config.setContentViewTemplate("/WEB-INF/jspf/cms/advertisementPages/show.jsp");
        config.setContentInsertTemplate("/WEB-INF/jspf/cms/advertisementPages/insert.jsp");
        config.setContentUpdateTemplate("/WEB-INF/jspf/cms/advertisementPages/update.jsp");
        config.setContentMultiupdateTemplate("/WEB-INF/jspf/cms/advertisementPages/show.jsp");
        return config;
    }

    @Bean
    public CmsConfig counterConfig() {
        CmsConfig config = new CmsConfig();
        config.setConfig(commonCmsConf());
        config.setNameRu("Счетчики");
        config.setNavigationTemplate("/WEB-INF/jspf/cms/counter/navigation.jsp");
        config.setContentViewTemplate("/WEB-INF/jspf/cms/counter/show.jsp");
        config.setContentInsertTemplate("/WEB-INF/jspf/cms/counter/insert.jsp");
        config.setContentUpdateTemplate("/WEB-INF/jspf/cms/counter/update.jsp");
        config.setContentMultiupdateTemplate("/WEB-INF/jspf/cms/counter/show.jsp");
        return config;
    }

    @Bean
    public CmsConfig localeConfig() {
        CmsConfig config = new CmsConfig();
        config.setConfig(commonCmsConf());
        config.setNameRu("Локализация");
        config.setNavigationTemplate("/WEB-INF/jspf/cms/locale/navigation.jsp");
        config.setContentViewTemplate("/WEB-INF/jspf/cms/locale/show.jsp");
        config.setContentInsertTemplate("/WEB-INF/jspf/cms/locale/insert.jsp");
        config.setContentUpdateTemplate("/WEB-INF/jspf/cms/locale/update.jsp");
        config.setContentMultiupdateTemplate("/WEB-INF/jspf/cms/locale/show.jsp");
        return config;
    }

    @Bean
    public CmsConfig pagesPseudonymCmsConfig() {
        CmsConfig config = new CmsConfig();
        config.setConfig(commonCmsConf());
        config.setNameRu("Оптимизация страниц");
        config.setNavigationTemplate("/WEB-INF/jspf/cms/pagesPseudonym/navigation.jsp");
        config.setContentViewTemplate("/WEB-INF/jspf/cms/pagesPseudonym/show.jsp");
        config.setContentInsertTemplate("/WEB-INF/jspf/cms/pagesPseudonym/insert.jsp");
        config.setContentMultiupdateTemplate("/WEB-INF/jspf/cms/pagesPseudonym/show.jsp");
        return config;
    }

}
