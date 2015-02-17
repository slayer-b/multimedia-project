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

package com.netstorm.localization;

import com.multimedia.service.ILocaleService;
import gallery.model.active.LocalesServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class MyLocaleResolver implements LocaleResolver {

    @Autowired
	private ILocaleService localeService;
	private final String allLocalesPrefix;

	public MyLocaleResolver(String allLocalesPrefix) {
	    this.allLocalesPrefix = allLocalesPrefix;
	}
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		LocalesServiceBean tmp = Utils.getLocalesBean(request);
		if (tmp!=null){
		    return tmp.getLocale();
		}

		tmp = createLocaleBean(request);
		request.setAttribute(Utils.LOCALE_REQUEST_ATTRIBUTE_NAME, tmp);
		return tmp.getLocale();
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		if (locale==null) {
			request.setAttribute(Utils.LOCALE_REQUEST_ATTRIBUTE_NAME, createLocaleBean(request));
		}else{
			request.setAttribute(Utils.LOCALE_REQUEST_ATTRIBUTE_NAME, new LocalesServiceBean(localeService, getDefaultLocale(), locale));
		}
	}

	public LocalesServiceBean createLocaleBean(HttpServletRequest request){
		String subPath;
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		int index1 = 1+contextPath.length();
        int index2 = uri.indexOf('/', index1);

		if (index2>index1){
			String localeName = uri.substring(index1, index2);
			Boolean active = (Boolean)localeService.getSinglePropertyU("active", "name", localeName);
			subPath = uri.substring(index2);

			if (active!=null&&(active||subPath.startsWith(allLocalesPrefix))) {
				return new LocalesServiceBean(localeService, getDefaultLocale(), new Locale(localeName), subPath, !subPath.startsWith(allLocalesPrefix));
			}
		}

		Locale locale = getDefaultLocale();
		if (locale == null) {
			locale = Locale.getDefault();
		}
		subPath = uri.substring(contextPath.length());
		return new LocalesServiceBean(localeService, getDefaultLocale(), locale, subPath, !subPath.startsWith(allLocalesPrefix));
	}

	public Locale getDefaultLocale(){return localeService.getDefaultLocale();}

}
