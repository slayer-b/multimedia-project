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
import com.multimedia.service.IWallpaperService;
import common.cms.ICmsConfig;
import common.services.IStaticsService;
import common.web.controller.StatisticController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("statsController")
@RequestMapping("/cms/stats.htm")
public class GalleryStatisticController extends StatisticController implements IStaticsService {
	@Autowired
    private IWallpaperService wallpaperService;
	@Autowired
	private IPagesService pagesService;

	private static final String MODEL_NAME = "gallery_stats";

    @Autowired
	public GalleryStatisticController(ICmsConfig commonCmsConf) {
        super(commonCmsConf, "/WEB-INF/jspf/cms/stats.jsp", "/WEB-INF/jspf/cms/navigation.jsp");
	}

	private static final SortedMap<String, Long> STATISTICS = Collections.synchronizedSortedMap(new TreeMap<String, Long>(Collections.reverseOrder()));
//-------------------------------------------------------------------------
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)	{
		ModelAndView rez = super.handleRequest(request, response);
		STATISTICS.put("wallpaper_count", (Long)wallpaperService.getSinglePropertyU("count(*)"));
		STATISTICS.put("pages_count", (Long)pagesService.getSinglePropertyU("count(*)"));
		request.setAttribute(MODEL_NAME, STATISTICS);
		return rez;
	}


	@Override
	public void increaseStat(String statName){
		Long l = STATISTICS.get(statName);
		if (l==null) {
			STATISTICS.put(statName, 1L);
		} else {
			STATISTICS.put(statName, ++l);
		}
	}
}
