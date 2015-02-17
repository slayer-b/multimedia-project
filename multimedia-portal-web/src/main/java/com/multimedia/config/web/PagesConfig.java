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

import common.beans.KeepParameters;
import common.bind.CommonBindValidator;
import common.types.TypesCheckAndCorrect;
import gallery.web.controller.pages.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;

@Configuration
public class PagesConfig {
    @Autowired
    private TypesCheckAndCorrect typesCheckAndCorrect;

    @Bean
    public EMailSendType typeSendEmail() {
        EMailSendType eMailSendType = new EMailSendType();
        eMailSendType.setContentUrl("/WEB-INF/jspf/view/forms/connectMe.jsp");
        eMailSendType.setEmail_addresses("demchuck.dima@gmail.com");

        CommonBindValidator bindValidator = new CommonBindValidator(typesCheckAndCorrect);
        bindValidator.setAntiSpamCodeParam("code");
        bindValidator.setRequiredFields(new String[]{"text", "email_from"});
        HashMap<String, String> fieldTypes = new HashMap<String, String>();
        fieldTypes.put("text", "text");
        fieldTypes.put("email_from", "email");
        bindValidator.setFieldTypes(fieldTypes);
        eMailSendType.setValidator(bindValidator);
        return eMailSendType;
    }

    @Bean
    public WallpaperRandomType wallpaperRandomType() {
        WallpaperRandomType wallpaperRandomType = new WallpaperRandomType();
        wallpaperRandomType.setContentUrl("/WEB-INF/jspf/view/wallpaper/gallery_random_view.jsp");
        return wallpaperRandomType;
    }

    @Bean
    public WallpaperRateType wallpaperRateType() {
        return new WallpaperRateType();
    }

    @Bean
    public WallpaperCommentAddType wallpaperCommentAddType() {
        WallpaperCommentAddType wallpaperCommentAddType = new WallpaperCommentAddType();
        CommonBindValidator bindValidator = new CommonBindValidator(typesCheckAndCorrect);
        bindValidator.setAntiSpamCodeParam("code");
        bindValidator.setRequiredFields(new String[]{"text", "id_photo"});
        bindValidator.setFieldTypes(Collections.singletonMap("text", "text"));
        wallpaperCommentAddType.setValidator(bindValidator);
        return wallpaperCommentAddType;
    }

    @Bean
    public WallpaperAddType wallpaperAddType() {
        WallpaperAddType wallpaperAddType = new WallpaperAddType();
        CommonBindValidator bindValidator = new CommonBindValidator(typesCheckAndCorrect);
        bindValidator.setRequiredFields(new String[]{"text", "id_photo"});
        bindValidator.setFieldTypes(Collections.singletonMap("text", "text"));
        wallpaperAddType.setBindValidator(bindValidator);
        return wallpaperAddType;
    }

    @Bean
    public WallpaperTagCloudType wallpaperTagCloudType() {
        WallpaperTagCloudType wallpaperTagCloudType = new WallpaperTagCloudType();
        wallpaperTagCloudType.setContentUrl("/WEB-INF/jspf/view/wallpaper/tagcloud/content.jsp");
        wallpaperTagCloudType.setOptimizationUrl("/WEB-INF/jspf/view/wallpaper/tagcloud/optimization.jsp");
        wallpaperTagCloudType.setInfoTopUrl("/WEB-INF/jspf/view/wallpaper/tagcloud/top.jsp");
        wallpaperTagCloudType.setInfoBottomUrl("/WEB-INF/jspf/view/wallpaper/tagcloud/bottom.jsp");
        wallpaperTagCloudType.setSearchUrl("/WEB-INF/jspf/view/wallpaper/tagcloud/search.jsp");
        wallpaperTagCloudType.setBindValidator(new CommonBindValidator(typesCheckAndCorrect));
        wallpaperTagCloudType.setKeepParameters(
                new KeepParameters(new String[]{"id_pages_nav", "tag", "exact"}));
        return wallpaperTagCloudType;
    }

    @Bean
    public WallpaperGalleryType wallpaperGalleryType() {
        WallpaperGalleryType wallpaperGalleryType = new WallpaperGalleryType();
        wallpaperGalleryType.setContentMainUrl("/WEB-INF/jspf/view/wallpaper/gallery_main_view.jsp");
        wallpaperGalleryType.setOptimizationMainUrl("/WEB-INF/jsp/view/wallpaper_main.jsp");
        wallpaperGalleryType.setInfoTopMainUrl("/WEB-INF/jspf/view/wallpaper/parts/page_top_main.jsp");
        wallpaperGalleryType.setInfoBottomMainUrl("/WEB-INF/jspf/view/wallpaper/parts/page_bottom_main.jsp");

        wallpaperGalleryType.setContentCategoryUrl("/WEB-INF/jspf/view/wallpaper/gallery_category_view.jsp");
        wallpaperGalleryType.setOptimizationCategoryUrl("/WEB-INF/jsp/view/wallpaper_category.jsp");
        wallpaperGalleryType.setInfoTopCategoryUrl("/WEB-INF/jspf/view/wallpaper/parts/page_top_category.jsp");
        wallpaperGalleryType.setInfoBottomCategoryUrl("/WEB-INF/jspf/view/wallpaper/parts/page_bottom_category.jsp");

        wallpaperGalleryType.setContentDetailsUrl("/WEB-INF/jspf/view/wallpaper/gallery_details_view.jsp");
        wallpaperGalleryType.setOptimizationDetailsUrl("/WEB-INF/jsp/view/wallpaper_details.jsp");

        wallpaperGalleryType.setCategoryKeepParameters(
                new KeepParameters(new String[]{"id_pages_nav"}));
        wallpaperGalleryType.setDetailsKeepParameters(
                new KeepParameters(new String[]{"id_pages_nav"}));
        return wallpaperGalleryType;
    }

}
