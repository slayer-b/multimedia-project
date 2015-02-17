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

import com.multimedia.model.beans.Counter;
import common.cms.services2.AGenericCmsService;
import common.services.generic.IGenericService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("cmsCounterService")
public class CmsCounterServiceImpl extends AGenericCmsService<Counter, Long>{
	private static final String[] ORDER_BY = {"sort"};
	private static final String[] ORDER_HOW = {"asc"};
	private static final List<String> CMS_SHORT_ALIAS = Arrays.asList("id","name","sort");


	@Override
	public int saveOrUpdateCollection(Collection<Counter> c) {
		return service.updateCollection(c, "sort");
	}

	@Override
	public Counter getInsertBean(Counter obj) {
	    Counter c;
		if (obj == null) {
			c = new Counter();
		} else {
		    c = obj;
		}
		Long sort = (Long) service.getSinglePropertyU("max(sort)");
		if (sort == null) {
		    sort = 0L;
		} else {
		    sort++;
		}
		c.setSort(sort);
		return c;
	}

	@Override
	public String[] getListOrderBy() {return ORDER_BY;}

	@Override
	public String[] getListOrderHow() {return ORDER_HOW;}

	@Override
	public List<String> getListPropertyNames() {return CMS_SHORT_ALIAS;}

	@Override
	public List<String> getListPropertyAliases() {return CMS_SHORT_ALIAS;}

//------------------------------------ initialization ----------------------------------
	@Resource(name="counterService")
	public void setService(IGenericService<Counter, Long> service) {
		this.service = service;
	}
}
