/*
 *  Copyright 2010 demchuck.dima@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.multimedia.cms.controller;

import com.multimedia.cms.service.ICmsWallpaperService;
import common.cms.ICmsConfig;
import common.utils.CommonAttributes;
import gallery.web.controller.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("wallpaperDimmensionCmsController")
@RequestMapping("/cms/wallpaper/wallpaper_resize.htm")
public class WallpaperDimmensionsControllerCms {
    @Autowired
	private ICmsWallpaperService cmsWallpaperService;
	/** config class is used to store some constants */
    @Autowired
	private ICmsConfig commonCmsConf;

	private static final String BEFORE_URL = "/WEB-INF/jspf/cms/wallpaper/prepare_resize.jsp";
	private static final String NAVIGATION_URL = "/WEB-INF/jspf/cms/wallpaper/backup_navigation.jsp";

	private static final String RESIZE_VALUE = "resize";

	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest req)	{
		String actionParam = req.getParameter(Config.ACTION_PARAM_NAME);

		req.setAttribute("title","Локальная система управления --> Resize");
		req.setAttribute(commonCmsConf.getNavigationUrlAttribute(),NAVIGATION_URL);
		boolean handled = false;

		long l = System.currentTimeMillis();
		if (RESIZE_VALUE.equals(actionParam)) {
			req.setAttribute("top_header","Resize");
			req.setAttribute(commonCmsConf.getContentUrlAttribute(), BEFORE_URL);
			cmsWallpaperService.reResizeWallpapers(Boolean.valueOf(req.getParameter("append_all")));
			handled = true;
			CommonAttributes.addHelpMessage("operation_succeed", req);
		}
		if (!handled) {
			req.setAttribute("top_header","Подготовка Resize");
			req.setAttribute(commonCmsConf.getContentUrlAttribute(), BEFORE_URL);
		}
		l = System.currentTimeMillis() - l;

		//DateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss.S");
		req.setAttribute("time", l);

		return new ModelAndView(commonCmsConf.getTemplateUrl());
	}
}
