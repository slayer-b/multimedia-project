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

import com.multimedia.cms.config.UserCmsConfig;
import com.multimedia.security.model.User;
import com.multimedia.service.IPagesService;
import com.multimedia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("usersCmsController")
@RequestMapping("/cms/users/index.htm")
public class UserCmsController {

	@Autowired
	private IPagesService pagesService;
	@Autowired
	private IUserService<User, Long> userService;
	@Autowired
	private UserCmsConfig usersConfig;

	private static final String NAVIGATION_URL = "/WEB-INF/jspf/cms/users/navigation.jsp";
	private static final String SHOW_URL = "/WEB-INF/jspf/cms/users/show.jsp";

	@RequestMapping(params = "do=view")
	public ModelAndView doView(HttpServletRequest req, HttpServletResponse resp) {
		//logger.fine("do=view");
		Map<String, Object> m = getCommonModel(req);
		m.put(usersConfig.getContentUrlAttribute(),SHOW_URL);

		Long id_pages = common.utils.RequestUtils.getLongParam(req, usersConfig.getIdPagesParamName());
		m.put("users", userService.getShortOrderedByPropertyValueCms("id_pages", id_pages));
		//m.put("users", userService.getAll());
        return new ModelAndView(usersConfig.getTemplateUrl(), m);
    }

    //TODO: make update only for roles.

	public Map<String, Object> getCommonModel(HttpServletRequest req) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title","Пользователи");
		m.put("top_header","Пользователи");

		m.put(usersConfig.getNavigationUrlAttribute(), NAVIGATION_URL);
		Long id_pages_nav = common.utils.RequestUtils.getLongParam(req, usersConfig.getIdPagesParamName());
		m.put(usersConfig.getNavigationDataAttribute(), pagesService.getAllPagesParents(id_pages_nav, gallery.web.controller.pages.Config.NAVIGATION_PSEUDONYMES));
		return m;
	}

}
