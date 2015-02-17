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


import org.springframework.stereotype.Component;

/**
 * is unmodifiable at runtime
 * @author demchuck.dima@gmail.com
 */
@Component("wallpaperCmsConfig")
public class WallpaperCmsConfig extends CommonCmsConfig implements IWallpaperCmsConfig {
	private static final String CONTENT_VIEW_TEMPLATE = "/WEB-INF/jspf/cms/wallpaper/show.jsp";
	private static final String CONTENT_MULTIUPDATE_TEMPLATE = "/WEB-INF/jspf/cms/wallpaper/show.jsp";
	private static final String CONTENT_INSERT_TEMPLATE = "/WEB-INF/jspf/cms/wallpaper/insert.jsp";
	private static final String CONTENT_UPDATE_TEMPLATE = "/WEB-INF/jspf/cms/wallpaper/update.jsp";
	private static final String CONTENT_NAVIGATION_TEMPLATE = "/WEB-INF/jspf/cms/wallpaper/navigation.jsp";

	private static final String NAME_RU = "Wallpapers(обои)";

	private static final String PREPARE_URL = "/WEB-INF/jspf/cms/wallpaper/prepare.jsp";
	private static final String AJAX_LIST_URL = "/WEB-INF/jspf/cms/wallpaper/ajax_list.jsp";
	private static final String AJAX_WALLPAPER_URL = "/WEB-INF/jspf/cms/wallpaper/ajax_wallpaper.jsp";
	private static final String VIEW_ONLY_IMG_URL = "/WEB-INF/jspf/cms/wallpaper/ajax_view.jsp";
	private static final String AJAX_UPDATE_FORM_URL = "/WEB-INF/jspf/cms/wallpaper/update_ajax.jsp";
	private static final String INSERT_MULT_FORM_URL = "/WEB-INF/jspf/cms/wallpaper/multi_insert.jsp";
	private static final String AJAX_RESP_URL = "/WEB-INF/jspf/cms/wallpaper/ajax_resp.jsp";
	private static final String UPLOAD_PREPARE_URL = "/WEB-INF/jspf/cms/wallpaper/prepare_upload.jsp";
	private static final String UPLOAD_PROCESS_URL = "/WEB-INF/jspf/cms/wallpaper/process_upload.jsp";
	private static final String DUPLICATES_SHOW_URL = "/WEB-INF/jspf/cms/wallpaper/duplicates.jsp";
	private static final String PREPARE_OPTIMIZATION_URL = "/WEB-INF/jspf/cms/wallpaper/prepare_optimization.jsp";

	private static final String UPLOAD_ATTRIBUTE_URL = "wallpaper_upload_progress";
	private static final String RENEW_RESOLUTION_ATTRIBUTE_URL = "wallpaper_renewResolution_progress";

    @Override
	public String getContentViewTemplate() {return CONTENT_VIEW_TEMPLATE;}

	@Override
	public String getContentMultiupdateTemplate() {return CONTENT_MULTIUPDATE_TEMPLATE;}

	@Override
	public String getContentInsertTemplate() {return CONTENT_INSERT_TEMPLATE;}

	@Override
	public String getContentUpdateTemplate() {return CONTENT_UPDATE_TEMPLATE;}

	@Override
	public String getNavigationTemplate() {return CONTENT_NAVIGATION_TEMPLATE;}

	@Override
	public String getNameRu() {return NAME_RU;}

	@Override
	public String getPrepareUrl() {return PREPARE_URL;}

	@Override
	public String getAjaxListUrl() {return AJAX_LIST_URL;}

	@Override
	public String getAjaxWallpaperUrl() {return AJAX_WALLPAPER_URL;}

	@Override
	public String getViewOnlyImgUrl() {return VIEW_ONLY_IMG_URL;}

	@Override
	public String getUpdateAjaxForm() {return AJAX_UPDATE_FORM_URL;}

	@Override
	public String getAjaxRespUrl() {return AJAX_RESP_URL;};

	@Override
	public String getInsertMultiFormUrl() {return INSERT_MULT_FORM_URL;}

	@Override
	public String getUploadPrepareUrl() {return UPLOAD_PREPARE_URL;}

	@Override
	public String getUploadProcessUrl() {return UPLOAD_PROCESS_URL;}

	@Override
	public String getUploadAttributeName() {return UPLOAD_ATTRIBUTE_URL;}

	@Override
	public String getRenewResolutionAttributeName() {return RENEW_RESOLUTION_ATTRIBUTE_URL;}

	@Override
	public String getDuplicatesShowUrl() {return DUPLICATES_SHOW_URL;}

	@Override
	public String getOptimizationPrepareUrl() {return PREPARE_OPTIMIZATION_URL;}

}
