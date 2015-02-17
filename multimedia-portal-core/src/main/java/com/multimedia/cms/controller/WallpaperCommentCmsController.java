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

package com.multimedia.cms.controller;

import common.cms.ICmsConfig;
import common.cms.controller2.FilterGenericCmsController;
import common.cms.services2.ICmsService;
import gallery.model.beans.WallpaperComment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("wallpaperCommentCmsController")
@RequestMapping("/cms/wallpaperComment/index.htm")
public class WallpaperCommentCmsController extends FilterGenericCmsController<WallpaperComment>{
	private static final List<String> FILTER_PROPERTIES = Arrays.asList("id_photo_nav");
	private static final List<String> FILTER_ALIASES = Arrays.asList("this.id_photo");

	@ModelAttribute
    public void populateWallpaperCommentParams(@RequestParam(value="id_photo_nav", required=false) Long id_photo_nav, Map<String, Object> model) {
		model.put("id_photo_nav", id_photo_nav);
    }

	@Override
	public List<String> getFilterProperties() {return FILTER_PROPERTIES;}
	@Override
	public List<String> getFilterAliases() {return FILTER_ALIASES;}

	//-------------------------------------- initialization ---------------------------------------

	@Resource(name="wallpaperCommentConfig")
	public void setConfig(ICmsConfig config) {this.config = config;}

	@Resource(name="cmsWallpaperCommentService")
	public void setCmsService(ICmsService<WallpaperComment, Long> cmsService) {
		this.cmsService = cmsService;
	}

}
