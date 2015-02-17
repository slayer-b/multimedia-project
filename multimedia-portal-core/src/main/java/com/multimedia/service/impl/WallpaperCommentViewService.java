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

import common.services.IInsertService;
import common.services.generic.IGenericService;
import gallery.model.beans.WallpaperComment;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("wallpaperCommentViewServices")
public class WallpaperCommentViewService implements IInsertService<WallpaperComment>{
	private IGenericService<WallpaperComment, Long> service;

	@Override
	public WallpaperComment getInsertBean() {
		return new WallpaperComment();
	}

	@Override
	public boolean insert(WallpaperComment obj) {
		obj.setCreationTime(new Date());
		service.save(obj);
		return true;
	}

	@Override
	public Map<String, Object> initInsert() {
		return null;
	}

	//--------------------------------------------------------- initialization --------------------------------------
	@Required
	@Resource(name="wallpaperCommentService")
	public void setWallpaperCommentService(IGenericService<WallpaperComment, Long> value){
	    this.service = value;
	}

}
