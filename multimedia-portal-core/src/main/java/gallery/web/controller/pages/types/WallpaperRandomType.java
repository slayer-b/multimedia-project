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

package gallery.web.controller.pages.types;

import gallery.web.controller.pages.submodules.ASubmodule;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperRandomType extends AWallpaperType{
    /** string constant that represents type for this page */
    public static final String TYPE="general_wallpaper_random";
	/** rus type */
	public static final String TYPE_RU="Wallpapers(случайная)";

	@Override
	public String getType() {return TYPE;}
	@Override
	public String getTypeRu() {return TYPE_RU;}

	private String contentUrl;
	private static final int RANDOM_WALLPAPER_QUANTITY = 15; 

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, UrlBean url)
	{
		request.setAttribute(
		        config.getContentDataAttribute(),
		        wallpaperService.getRandomWallpapers(RANDOM_WALLPAPER_QUANTITY));

		url.setContent(contentUrl);

		Map<String, ASubmodule> m = url.getSubmodules();
		if (m!=null) {
		    m.remove(TYPE);
		}
	}

	@Required
	public void setContentUrl(String value){this.contentUrl = value;}

}
