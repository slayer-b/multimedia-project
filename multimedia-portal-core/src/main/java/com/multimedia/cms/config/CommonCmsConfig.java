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

package com.multimedia.cms.config;

import common.cms.ICmsConfig;

/**
 * Presents an abstract superclass for all cms configurations.
 * Defines attributes that may be common across all cms configurations.
 * Attributes may be overridden in children.
 * @author demchuck.dima@gmail.com
 */
public abstract class CommonCmsConfig implements ICmsConfig{
    private static final String CONTENT_URL_ATTRIBUTE = "content_url";
    private static final String NAVIGATION_URL_ATTRIBUTE = "navigation_url";

    private static final String TEMPLATE_URL = "/WEB-INF/jsp/cms/main.jsp";

    private static final String CONTENT_DATA_ATTRIBUTE = "content_data";
    private static final String NAVIGATION_DATA_ATTRIBUTE = "pages_navigation";

    private static final String URL_ATTRIBUTE = "url";

	@Override
	public String getContentUrlAttribute() {return CONTENT_URL_ATTRIBUTE;}

	@Override
	public String getNavigationUrlAttribute() {return NAVIGATION_URL_ATTRIBUTE;}

	@Override
	public String getTemplateUrl() {return TEMPLATE_URL;}

	@Override
	public String getContentDataAttribute() {return CONTENT_DATA_ATTRIBUTE;}

	@Override
	public String getNavigationDataAttribute() {return NAVIGATION_DATA_ATTRIBUTE;}

	@Override
	public String getUrlAttribute() {return URL_ATTRIBUTE;}

}
