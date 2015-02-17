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

import com.multimedia.cms.config.LocaleConfig;
import com.multimedia.model.beans.Locale;
import common.cms.services2.AGenericCmsService;
import common.services.generic.IGenericService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("cmsLocaleService")
public class CmsLocaleServiceImpl extends AGenericCmsService<Locale, Long>{

//----------------------------- methods ---------------------------------------------------

	@Override
	public Locale getInsertBean(Locale obj) {
	    Locale l;
		if (obj == null) {
			l = new Locale();
		} else {
		    l = obj;
		}
		Long sort = (Long) service.getSinglePropertyU("max(sort)");
		if (sort == null) {
		    sort = 0L;
		} else {
		    sort++;
		}
		l.setSort(sort);
		l.setActive(Boolean.TRUE);
		return l;
	}

	@Override
	public int saveOrUpdateCollection(Collection<Locale> c) {return service.updateCollection(c, "active", "sort");}

	@Override
	public String[] getListOrderBy() {return LocaleConfig.ORDER_BY;}

	@Override
	public String[] getListOrderHow() {return LocaleConfig.ORDER_HOW;}

//---------------------------  services ------------------------------------------------
	@Resource(name="localeService")
	public void setService(IGenericService<Locale, Long> service){this.service = service;}
}
