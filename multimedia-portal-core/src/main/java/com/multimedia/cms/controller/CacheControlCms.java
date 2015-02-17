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

import com.multimedia.service.IRubricImageService;
import com.multimedia.service.IRubricationService;
import common.cms.ICmsConfig;
import common.utils.CommonAttributes;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("cacheController")
public class CacheControlCms {
    public static final String DO_PARAM = "do";
    public static final String PARAM_RUBRICATOR_CLEAR = "clear_rubricator";
    public static final String PARAM_RUBRICATOR_REFRESH = "refresh_rubricator";
    public static final String PARAM_RUBRICATOR_IMAGE_CLEAR = "clear_rub_img";
    public static final String PARAM_RUBRICATOR_IMAGE_REFRESH = "refresh_rub_img";
    public static final String PARAM_REGION_CLEAR =   "region_clear";

    public static final String PARAM_REGION_NAME = "name";

    @Autowired
    private IRubricationService rubricationService;
    @Autowired
    private IRubricImageService rubricImageService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ICmsConfig commonCmsConf;

    private static final String CONTENT_URL = "/WEB-INF/jspf/cms/cache_control.jsp";
    private static final String NAVIGATION_URL = "/WEB-INF/jspf/cms/navigation.jsp";

    @RequestMapping("/cms/cache_control.htm")
    public ModelAndView handleRequest(HttpServletRequest request) {
		String doParam = request.getParameter(DO_PARAM);
		if (PARAM_RUBRICATOR_CLEAR.equals(doParam)){
			rubricationService.clearCache();
			CommonAttributes.addHelpMessage("operation_succeed", request);
		}else if (PARAM_RUBRICATOR_REFRESH.equals(doParam)){
			rubricationService.refreshCache();
			CommonAttributes.addHelpMessage("operation_succeed", request);
		}else if (PARAM_RUBRICATOR_IMAGE_CLEAR.equals(doParam)){
			if (rubricImageService.clearImages(null)){
				CommonAttributes.addHelpMessage("operation_succeed", request);
			} else {
				CommonAttributes.addErrorMessage("operation_fail", request);
			}
		} else if (PARAM_RUBRICATOR_IMAGE_REFRESH.equals(doParam)){
			if (rubricImageService.refreshImages(null)){
				CommonAttributes.addHelpMessage("operation_succeed", request);
			} else {
				CommonAttributes.addErrorMessage("operation_fail", request);
			}
		} if (PARAM_REGION_CLEAR.equals(doParam)){
			String[] names = request.getParameterValues(PARAM_REGION_NAME);
			Map<String, Boolean> result = new HashMap<String, Boolean>();
			for (String name:names){
				if (name!=null){
					Ehcache cache = cacheManager.getEhcache(name);
					if (cache==null){
						result.put(name, Boolean.FALSE);
						CommonAttributes.addErrorMessage("operation_fail", request);
					} else {
						result.put(name, Boolean.TRUE);
						cache.removeAll();
						CommonAttributes.addHelpMessage("operation_succeed", request);
					}
				}
			}
			request.setAttribute(commonCmsConf.getContentDataAttribute() ,result);
		}
        request.setAttribute(commonCmsConf.getContentUrlAttribute() ,CONTENT_URL);
        request.setAttribute(commonCmsConf.getNavigationUrlAttribute() ,NAVIGATION_URL);

        request.setAttribute("title","Управление кешем");
        request.setAttribute("top_header","Управление кешем");

        return new ModelAndView(commonCmsConf.getTemplateUrl());
    }

}
