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

package com.multimedia.cms.service;

import common.cms.services2.AGenericCmsService;
import common.services.generic.IGenericService;
import gallery.model.beans.WallpaperComment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Collection;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("cmsWallpaperCommentService")
public class CmsWallpaperCommentService extends AGenericCmsService<WallpaperComment, Long>{

	@Override
	public int saveOrUpdateCollection(Collection<WallpaperComment> c) {
		throw new UnsupportedOperationException("this entity does not support mass update");
	}

	@Override
	public WallpaperComment getInsertBean(WallpaperComment obj) {
	    WallpaperComment wc;
		if (obj == null) {
			wc = new WallpaperComment();
		} else {
		    wc = obj;
		}
		wc.setCreationTime(new Timestamp(System.currentTimeMillis()));
		return wc;
	}

	//-------------------------------------------- initialization -------------------------------
	@Resource(name="wallpaperCommentService")
	public void setService(IGenericService<WallpaperComment, Long> service){this.service = service;}
}
