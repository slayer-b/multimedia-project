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
 *
 * @author demchuck.dima@gmail.com
 */
public interface IWallpaperCmsConfig extends ICmsConfig {
	/** an jsp page where choose type of action */
	String getPrepareUrl();
	/** draws just a collection with , delimiter */
	String getAjaxListUrl();
	/** jsp for rendering one wallpaper template for ajax response */
	String getAjaxWallpaperUrl();
	/** draws an alternative view, only small images are shown */
	String getViewOnlyImgUrl();
	/** form with ajax for updating wallpapers */
	String getUpdateAjaxForm();
	/** response that is send by the server on an ajax update/insert action (when form is submitted asynchronously) */
	String getAjaxRespUrl();
	/** view for insert multi where many wallpapers may be provided for insert */
	String getInsertMultiFormUrl();
	/** view for preparation of uploading */
	String getUploadPrepareUrl();
	/** view for processing(when some wallpapers are already uploading) of uploading */
	String getUploadProcessUrl();
	/** view for result after finding duplicates */
	String getDuplicatesShowUrl();
	/** view for preparation of optimization (selects id_pages, where to optimize wallpapers) */
	String getOptimizationPrepareUrl();
	/** upload attribute if exists then upload is already in progress */
	String getUploadAttributeName();
	/** renew resolution attribute if exists then renewing resolution operation is already in progress */
	String getRenewResolutionAttributeName();
}
