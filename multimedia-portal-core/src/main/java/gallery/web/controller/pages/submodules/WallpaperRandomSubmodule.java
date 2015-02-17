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

package gallery.web.controller.pages.submodules;

import com.multimedia.service.IWallpaperService;

import gallery.model.beans.Wallpaper;
import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperRandomSubmodule extends ASubmodule {
//	private final IWallpaperService wallpaperService;

	public WallpaperRandomSubmodule(IWallpaperService wallpaperService){
//		this.wallpaperService = wallpaperService;
	}

	public List<Wallpaper> getData(){
//		return wallpaperService.getRandomWallpapers(8);
        return null;
	}

	@Override
	public boolean getEmpty() {return false;}
}
