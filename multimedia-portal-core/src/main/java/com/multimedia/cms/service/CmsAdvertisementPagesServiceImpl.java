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

import com.multimedia.model.beans.AdvertisementPages;
import com.multimedia.service.IAdvertisementService;
import com.multimedia.service.IPagesService;
import common.cms.services2.AGenericCmsService;
import common.services.generic.IGenericService;
import gallery.model.active.PagesCombobox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("cmsAdvertisementPagesService")
public class CmsAdvertisementPagesServiceImpl extends AGenericCmsService<AdvertisementPages, Long> {
    @Autowired
	private IAdvertisementService advertisementService;
	@Autowired
	private IPagesService pagesService;

	private static final String[] ORDER_BY = {"id_pages", "id_advertisement"};
	private static final String[] ORDER_HOW = {"asc", "asc"};

	private static final String[] ADV_PROPS = {"id", "name"};
	private static final String[] PROPS_ORDER_BY = {"name"};
	private static final String[] PROPS_ORDER_HOW = {"asc"};

	@Override
	public AdvertisementPages getInsertBean(AdvertisementPages obj) {
	    AdvertisementPages a;
		if (obj == null) {
			a = new AdvertisementPages();
		} else {
		    a = obj;
		}
		a.setUseInChildren(Boolean.TRUE);
		a.setUseInParent(Boolean.TRUE);
		return a;
	}

	@Override
	public void initInsert(Map<String, Object> model) {
		model.put("advertisements", advertisementService.getOrdered(ADV_PROPS, PROPS_ORDER_BY, PROPS_ORDER_HOW));
	}

	@Override
	public void initUpdate(Map<String, Object> model) {
		model.put("advertisements", advertisementService.getOrdered(ADV_PROPS, PROPS_ORDER_BY, PROPS_ORDER_HOW));
	}

	@Override
	public void initFilter(Map<String, Object> model) {
		model.put("advertisements", advertisementService.getOrdered(ADV_PROPS, PROPS_ORDER_BY, PROPS_ORDER_HOW));
		model.put("categories_wallpaper_select", new PagesCombobox(null, pagesService));
	}

	@Override
	public String[] getListOrderBy() {return ORDER_BY;}

	@Override
	public String[] getListOrderHow() {return ORDER_HOW;}

//------------------------------------ initialization ----------------------------------
	@Resource(name="advertisementPagesService")
	public void setService(IGenericService<AdvertisementPages, Long> value) {
		this.service = value;
	}

}