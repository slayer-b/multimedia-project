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

package gallery.web.controller.pages.types;

import com.multimedia.service.IWallpaperService;
import common.beans.KeepParameters;
import common.services.IDeleteService;
import common.utils.CommonAttributes;
import common.utils.RequestUtils;
import common.web.controller.CommonActions;
import gallery.web.validator.wallpaper.WallpaperUpdateBindValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperDeleteType extends ASingleContentType {
    /** string constant that represents type for this page */
    public static final String TYPE="system_delete_wallpaper";
	/** rus type */
	public static final String TYPE_RU="---Удаление фото---";

	public static final String KEEP_PARAMETERS_ATTR = "query_string";
	/** service for validating */
	@Autowired
	private IWallpaperService wallpaperService;
	/** service for deleting */
	@Autowired
	private IDeleteService<Long> wallpaperServiceView;
	/** for keeping parameters */
	private KeepParameters keepParameters;

	@Override
	public String getType() {return TYPE;}
	@Override
	public String getTypeRu() {return TYPE_RU;}

	@Override
	public UrlBean execute(HttpServletRequest request,HttpServletResponse response) {
		Long id = RequestUtils.getLongParam(request, "id");
		if (WallpaperUpdateBindValidator.validate(id, null, request, wallpaperService)){
			CommonActions.doDelete(wallpaperServiceView, request);
		}else{
			CommonAttributes.addErrorMessage("operation_fail", request);
		}

		UrlBean url = new UrlBean();
		url.setContent(contentUrl);
		request.setAttribute(KEEP_PARAMETERS_ATTR, keepParameters.getKeepParameters(request));

        return url;
	}

	@Required
	public void setKeepParameters(KeepParameters keepParameters) {this.keepParameters = keepParameters;}

}
