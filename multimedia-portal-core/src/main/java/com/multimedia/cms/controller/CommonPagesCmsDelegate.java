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

import com.multimedia.service.IPagesService;
import common.cms.controller.FilteredCmsDelegate;
import common.utils.RequestUtils;
import gallery.web.controller.pages.Config;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class CommonPagesCmsDelegate<T> extends FilteredCmsDelegate<T> {
    public static final String ID_PAGES_PARAM_NAME = "id_pages_nav";

    @Autowired
	protected IPagesService pagesService;

	@Override
	public Map<String, Object> getCommonModel(HttpServletRequest req) {
		Map<String, Object> map = super.getCommonModel(req);

		Long id_pages_nav = RequestUtils.getLongParam(req, ID_PAGES_PARAM_NAME);
		map.put(config.getNavigationDataAttribute(), pagesService.getAllPagesParents(id_pages_nav, Config.NAVIGATION_PSEUDONYMES));
		return map;
	}

}
