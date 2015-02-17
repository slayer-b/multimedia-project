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

import common.bind.ABindValidator;
import common.services.IUpdateService;
import common.web.controller.CommonActions;
import gallery.model.beans.Wallpaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperEditType extends ASingleContentType {
    /** string constant that represents type for this page */
    public static final String TYPE="system_edit_wallpaper";
	/** rus type */
	public static final String TYPE_RU="---Редактирование фото---";

	@Override
	public String getType() {return TYPE;}
	@Override
	public String getTypeRu() {return TYPE_RU;}

	private ABindValidator updateValidator;
	@Autowired
	private IUpdateService<Wallpaper, Long> wallpaperServiceView;

	@Override
	public UrlBean execute(HttpServletRequest request,HttpServletResponse response) {
		//req.setAttribute("editForm_topHeader", "Редактирование");

		UrlBean url = new UrlBean();
		if (CommonActions.doUpdate(wallpaperServiceView, config, updateValidator, request)){
			url.setContent(contentUrl);
		} else {
			request.setAttribute(config.getContentDataAttribute(), new Wallpaper());
		}

        return url;
	}

	@Required
	public void setUpdateValidator(ABindValidator value) {this.updateValidator = value;}

}
