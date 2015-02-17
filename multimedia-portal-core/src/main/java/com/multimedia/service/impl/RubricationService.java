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
import com.multimedia.service.IRubricationService;
import common.services.generic.GenericCacheService;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.types.WallpaperGalleryType;
import org.springframework.core.io.Resource;

import java.util.*;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class RubricationService extends GenericCacheService<List<Pages>> implements IRubricationService {
    private static final int MAX_LAYER = 3;
    
    private static final String[] PAGES_TYPES = {WallpaperGalleryType.TYPE};
    private final IPagesService pagesService;

    public RubricationService(Resource path, IPagesService pagesService) {
        super(path);
        this.pagesService = pagesService;
    }

    private static final String[] RUBRIC_WHERE = {"active","type"};
    private static final String[] RUBRIC_PSEUDONYMES = {"id","id_pages","name","type","last"};
    @Override
    public List<Pages> getFromDB() {
		return pagesService.getPagesChildrenRecurciveOrderedWhere(
                RUBRIC_PSEUDONYMES, RUBRIC_WHERE,
                new Object[][]{new Object[]{Boolean.TRUE}, PAGES_TYPES});
    }

	@Override
	public List<Pages> getObjectClone() {
		List<Pages> tmp =  getObject();
		List<Pages> rez = new ArrayList<Pages>(tmp.size());
		for (Pages p : tmp) {
			rez.add((Pages)p.clonePage());
		}
		return rez;
	}

	@Override
	public List<Pages> getCurrentBranch(Long id_pages) {
		List<Pages> tmp =  getObject();
		List<Pages> rez = getBranchUnsorted(tmp, id_pages);
		if (rez.isEmpty()) {
			boolean selected = true;
			for (Pages p : tmp) {
				if (p.getLayer() < MAX_LAYER) {
				    Pages curPage = (Pages)p.clonePage();
					curPage.setSelected(selected);
					rez.add(curPage);
					selected = false;
				}
			}
		}
		return rez;
	}

	/**
	 * the order is natural
	 * @param id_pages current page's id
	 * @return list of pages in an appropriate order, with selected flag
	 */
	protected List<Pages> getBranchUnsorted(List<Pages> object, Long id_pages) {
		LinkedList<Pages> rez = new LinkedList<Pages>();
		int i = object.size();
		Long curId = id_pages;
		//1-st collecting all parents
		Set<Long> parentsId = new HashSet<Long>();
		parentsId.add(id_pages);
		while (i > 0) {
			i--;
			if (curId.equals(object.get(i).getId())) {
				curId = object.get(i).getId_pages();
				if (curId == null) {
					break;
				} else {
					parentsId.add(curId);
				}
			}
		}
		//2-nd adding required
		i = object.size();
		while (i > 0) {
			i--;
			Pages curPage = object.get(i);
			if (parentsId.contains(curPage.getId())) {
				curPage = (Pages)curPage.clonePage();
				rez.addFirst(curPage);
				curPage.setSelected(Boolean.TRUE);
			} else if (parentsId.contains(curPage.getId_pages())) {
				rez.addFirst((Pages)curPage.clonePage());
			} else if (curPage.getId_pages() == null){
				rez.addLast((Pages)curPage.clonePage());
			}
		}
		return rez;
	}

	/**
	 * sorts sets selected pages to the top of list
	 * @param idPages current page's id
	 * @return list of pages in an appropriate order, with selected flag
	 */
	protected List<Pages> getBranchSortedTop(List<Pages> object, Long idPages) {
		LinkedList<Pages> rez = new LinkedList<Pages>();
		//marking current navigation branch
		Long curId = idPages;
		int i=object.size();
		int last = -1;
		int pos = 0;
		//marking selected branch
		while (i>0) {
			i--;
			Pages curPage = object.get(i);
			if (curId.equals(curPage.getId_pages())&&last!=i) {
				rez.add(pos, (Pages)curPage.clonePage());
			} else if (curId.equals(curPage.getId())) {
				curPage = (Pages)curPage.clonePage();
				curPage.setSelected(Boolean.TRUE);
				rez.addFirst(curPage);

				curId = curPage.getId_pages();
				last = i;
				pos = rez.size();
				i=object.size();
				if (curId==null) {
					break;
				}
			}
		}
		return rez;
	}

}
