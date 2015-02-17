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

package com.multimedia.service.impl;

import com.multimedia.service.IPagesService;
import com.multimedia.service.IWallpaperService;
import common.services.IDeleteService;
import common.services.IUpdateService;
import gallery.model.beans.Wallpaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("wallpaperServiceView")
public class WallpaperServiceViewImpl implements IUpdateService<Wallpaper, Long>, IDeleteService<Long>{
    @Autowired
	private IWallpaperService wallpaperService;
    @Autowired
    private IPagesService pagesService;

	@Override
	public Wallpaper getUpdateBean(Long id) {return wallpaperService.getById(id);}

	@Override
	public Map<String, Object> initUpdate() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("categories_wallpaper_select", pagesService.getAllCombobox(Boolean.TRUE, Boolean.TRUE, gallery.web.controller.pages.types.WallpaperGalleryType.TYPE));
		return m;
	}

	@Override
	public int update(Wallpaper command) {
		if (command.getContent()==null){
			//we just need to update rows in database
			wallpaperService.save(command);
		}else{
			//1-st delete old wallpapers
			if (wallpaperService.deleteFiles(command) && wallpaperService.getImage(command)){
				//2-nd update rows in database, create new, and count new resolution
				wallpaperService.save(command);
			}else{
				return -1;
			}
		}
		return 1;
	}

	@Override
	public int deleteById(Long id) {return wallpaperService.deleteById(id);}
}
