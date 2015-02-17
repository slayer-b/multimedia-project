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

import com.multimedia.service.IPagesPseudonymService;
import com.multimedia.service.IPagesService;
import common.cms.services2.AGenericCmsService;
import gallery.model.beans.PagesPseudonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("pagesPseudonymServiceCms")
public class PagesPseudonymServiceCmsImpl extends AGenericCmsService<PagesPseudonym, Long> {

	private IPagesPseudonymService pagesPseudonymService;
    @Autowired
	private IPagesService pagesService;

    @Autowired
    public void setPagesPseudonymService(IPagesPseudonymService pagesPseudonymService) {
        this.pagesPseudonymService = pagesPseudonymService;
        this.service = pagesPseudonymService;
    }

	@Override
	public PagesPseudonym getInsertBean(PagesPseudonym obj) {
		PagesPseudonym item = (obj == null)? new PagesPseudonym(): obj;
		Long sort = (Long) pagesPseudonymService.getSinglePropertyU("max(sort)");
		if (sort == null) {
		    sort = 0L;
		} else {
		    sort++;
		}
		item.setSort(sort);
        item.setUseInItems(Boolean.TRUE);
        item.setUseInPages(Boolean.TRUE);
		return item;
    }

	@Override
	public boolean insert(PagesPseudonym obj) {
	    pagesPseudonymService.save(obj);
		return true;
	}

	@Override
	public int deleteById(Long id) {
		return pagesPseudonymService.deleteById(id);
	}

	@Override
	public PagesPseudonym getUpdateBean(Long id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int update(PagesPseudonym command) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int saveOrUpdateCollection(Collection<PagesPseudonym> c) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    @Override
    public void initInsert(Map<String, Object> model) {
        model.put("categories", pagesService.getAllCombobox(null, null, null));
    }

    @Override
    public void initUpdate(Map<String, Object> model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
