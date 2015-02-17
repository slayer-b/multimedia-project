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

import java.util.Map;

import common.cms.ICmsConfig;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("index123")
public class IndexCmsController {

	@Resource
	private ICmsConfig commonCmsConf;

	@RequestMapping({"/cms/index.htm", "/cms/"})
	public String handle(Map<String, Object> model) {
	    model.put("content_url", "/WEB-INF/jspf/cms/center.jsp");
	    model.put("title", "Локальная система управления");
	    model.put("top_header", "Локальная система управления");
		return commonCmsConf.getTemplateUrl();
	}

}
