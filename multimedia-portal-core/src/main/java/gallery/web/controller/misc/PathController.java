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

package gallery.web.controller.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("pathController")
@RequestMapping("/path/**")
public class PathController {
	private final Logger logger = LoggerFactory.getLogger(PathController.class);
    @Autowired
    AbstractHandlerMapping requestMappingHandlerMapping;

	@RequestMapping("/{test}")
	@ResponseBody
	public String helloWorld(@PathVariable("test") String[] test) {
		StringBuilder rez = new StringBuilder();
        for (String aTest : test) {
            rez.append(aTest);
        }
        logger.info("hello - " + rez);
		return "hello:" + rez;
	}

	@RequestMapping
	@ResponseBody
	public String hello(HttpServletRequest request){
        UrlPathHelper urlPathHelper = requestMappingHandlerMapping.getUrlPathHelper();

        StringBuilder sb = new StringBuilder();
        sb.append("encoding=").append(request.getCharacterEncoding()).append("<br>");
        sb.append("info=").append(request.getPathInfo()).append("<br>");
        sb.append("uri=").append(request.getRequestURI()).append("<br>");
        sb.append("url=").append(request.getRequestURL()).append("<br>");

        sb.append("servlet path=").append(request.getServletPath()).append("<br>");

        sb.append("lookup=").append(urlPathHelper.getLookupPathForRequest(request)).append("<br>");
        sb.append("within servlet=").append(urlPathHelper.getPathWithinServletMapping(request)).append("<br>");
        sb.append("query servlet=").append(request.getQueryString()).append("<br>");
        sb.append("query spring =").append(urlPathHelper.getOriginatingQueryString(request)).append("<br>");

		return sb.toString();
	}
}
