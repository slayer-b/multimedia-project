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

import com.multimedia.service.IWallpaperService;
import common.bind.ABindValidator;
import common.bind.CommonBindValidator;
import common.cms.ICmsConfig;
import common.types.TypesCheckAndCorrect;
import gallery.model.beans.Wallpaper;
import gallery.model.command.WallpaperBackupCms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("wallpaperBackupCmsController")
@RequestMapping("/cms/wallpaper/wallpaper_backup.htm")
public class WallpaperBackupControllerCms {
    @Autowired
	private IWallpaperService wallpaperService;
	@Autowired
	private ICmsConfig commonCmsConf;
    @Autowired
    private TypesCheckAndCorrect typesCheckAndCorrect;

	private ABindValidator validator;

	private static final String AFTER_URL = "/WEB-INF/jspf/cms/wallpaper/backup.jsp";
    private static final String BEFORE_URL = "/WEB-INF/jspf/cms/wallpaper/prepare_backup.jsp";
	private static final String NAVIGATION_URL = "/WEB-INF/jspf/cms/wallpaper/backup_navigation.jsp";

	private static final String[] PSEUDONYMES = {"id", "name", "folder"};

	public static final String RESTORE_VALUE = "restore";
	public static final String BACKUP_VALUE  = "backup";
	public static final String IMPORT_VALUE  = "import";
	public static final String EXPORT_VALUE  = "export";

    @PostConstruct
    public void init() {
        validator = new CommonBindValidator(typesCheckAndCorrect);
    }

	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse arg1)	{
		String doParam = req.getParameter("do");

		req.setAttribute("title","Локальная система управления --> Backup / Restore");
		req.setAttribute(commonCmsConf.getNavigationUrlAttribute(), NAVIGATION_URL);

		WallpaperBackupCms command = new WallpaperBackupCms();
		BindingResult res = validator.bindAndValidate(command, req);
		boolean handled = false;

		long l = System.currentTimeMillis();
		if (!res.hasErrors()) {
			if (BACKUP_VALUE.equals(doParam)) {
				req.setAttribute("top_header","Backup");
				req.setAttribute(commonCmsConf.getContentUrlAttribute(), AFTER_URL);
				List<Wallpaper> wallpapers = wallpaperService.getOrdered(PSEUDONYMES, null, null);
				req.setAttribute(commonCmsConf.getContentDataAttribute(), wallpapers);

				wallpaperService.backupWallpapers(wallpapers, command.getAppend_all(), Boolean.TRUE);
				handled = true;
			} else if (RESTORE_VALUE.equals(doParam)) {
				req.setAttribute("top_header","Restore");
				req.setAttribute(commonCmsConf.getContentUrlAttribute(), AFTER_URL);
				List<Wallpaper> wallpapers = wallpaperService.getOrdered(PSEUDONYMES, null, null);
				req.setAttribute(commonCmsConf.getContentDataAttribute(), wallpapers);

				wallpaperService.restoreWallpapers(wallpapers, command.getAppend_all(), Boolean.TRUE);
				handled = true;
			}
		}
		if (!handled) {
			req.setAttribute("top_header","Подготовка Backup/Restore");
			req.setAttribute(commonCmsConf.getContentUrlAttribute(), BEFORE_URL);
		}
		l = System.currentTimeMillis() - l;

		req.setAttribute("time", l);

		return new ModelAndView(commonCmsConf.getTemplateUrl());
	}
}
