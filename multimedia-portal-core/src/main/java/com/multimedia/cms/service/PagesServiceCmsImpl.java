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

import com.multimedia.service.IPagesService;
import com.multimedia.service.IWallpaperService;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.types.WallpaperGalleryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("pagesServiceCms")
public class PagesServiceCmsImpl implements IPagesServiceCms {
    @Autowired
	private IPagesService pagesService;
    @Autowired
    private IWallpaperService wallpaperService;
    @Autowired
    private ICmsWallpaperService wallpaperServiceCms;

    private static final String[] REACTIVATE_NAMES = {"id","id_pages","active","last"};
    private static final String[] REACTIVATE_TYPE = {"type"};
    private static final String[] REACTIVATE_PROPS = {"active"};
    private static final Object[] REACTIVATE_VALS_FALSE = {Boolean.FALSE};
    private static final Object[] REACTIVATE_VALS_TRUE = {Boolean.TRUE};
    @Override
    public void reactivate() {
        //now it reactivates all pages
        //TODO: remake
        List<Pages> rubrication = pagesService.getPagesChildrenRecurciveOrderedWhere(REACTIVATE_NAMES, REACTIVATE_TYPE,
                new Object[][]{new Object[]{WallpaperGalleryType.TYPE}});
        //List<Pages> rubrication = pages_service.getRubrication(gallery.web.controller.pages.types.WallpaperGalleryType.TYPE);
        //marking current navigation branch
        Pages curPage;
        Pages prevPage = null;
        int i=rubrication.size();
        ArrayList<Long> emptyPages = new ArrayList<Long>(rubrication.size());
        ArrayList<Long> filledPages = new ArrayList<Long>(rubrication.size());
        Long count;
        //marking selected branch
        while (i>0){
            i--;
            curPage = rubrication.get(i);
            if (curPage.getLast()){
                count = wallpaperService.getRowCount("id_pages", curPage.getId());
                if (count<1L){
                    if (curPage.getActive()) {
                        emptyPages.add(curPage.getId());
                    }
                    rubrication.remove(i);
                } else {
                    if (!curPage.getActive()) {
                        filledPages.add(curPage.getId());
                    }
                    prevPage = curPage;
                }
            } else {
                if (prevPage!=null&&prevPage.getId_pages().equals(curPage.getId())){
                    if (!curPage.getActive()) {
                        filledPages.add(curPage.getId());
                    }
                    prevPage = curPage;
                } else {
                    if (curPage.getActive()) {
                        emptyPages.add(curPage.getId());
                    }
                    rubrication.remove(i);
                }
            }
        }
        //set empty pages not active
        Long[] ids = new Long[0];
        ids = emptyPages.toArray(ids);
        pagesService.updateObjectArrayShortByProperty(REACTIVATE_PROPS, REACTIVATE_VALS_FALSE, "id", ids);
        //set filled pages to active
        ids = new Long[0];
        ids = filledPages.toArray(ids);
        pagesService.updateObjectArrayShortByProperty(REACTIVATE_PROPS, REACTIVATE_VALS_TRUE, "id", ids);
    }


	private static final String[] FULL_WHERE = {"type"};
	private static final String[] FULL_NAMES = {"id","id_pages","name","type","last","active","pseudonyms.size"};
	private static final String[] FULL_PSEUDONYMES = {"id","id_pages","name","type","last","active","pseudonymsCount"};
	private static final String[] FULL_WALLPAPER = {"optimized","id_pages"};
	@Override
	public List<Pages> getCategoriesFull(){
		List<Pages> pages = pagesService.getPagesChildrenRecurciveOrderedWhere(FULL_NAMES, FULL_PSEUDONYMES, FULL_WHERE,
				new Object[][]{new String[]{WallpaperGalleryType.TYPE}});
		for (Pages p:pages){
			if (p.getLast()){
				//because its only one page type now, we do not check it
				//get pictures quantity
				p.setWallpaperCount(wallpaperService.getRowCount("id_pages", p.getId()).intValue());
				p.setOptimized((Boolean)wallpaperService.getSinglePropertyU("optimized", FULL_WALLPAPER, new Object[]{Boolean.FALSE,p.getId()}, 0));
			}
		}
        return pages;
	}

	private static final String[] OPTIMIZE_NAMES = {"id","id_pages","type","last","pseudonyms.size"};
	private static final String[] OPTIMIZE_PSEUD = {"id","id_pages","type","last","pseudonymsCount"};
    @Override
    public void optimizeCategory(Long id){
        //1-st checking if current category has any optimization
        //Integer count = (Integer)pages_service.getSinglePropertyU("pseudonyms.size","id",id);
		List<Pages> children = pagesService.getByPropertyValueOrdered(OPTIMIZE_NAMES, OPTIMIZE_PSEUD, "id", id, null, null);
        if (children.size()<1||children.get(0).getPseudonymsCount()<1) {
            return;
        }
        LinkedList<Long> idsTmp = new LinkedList<Long>();
        int k = 0;
        //2-nd getting all subpages
        while (children.size()>k){
            Pages p = children.get(k);
            //TODO mb check type here ...
            if (p.getPseudonymsCount()>0){
                if (p.getLast()){
                    idsTmp.add(p.getId());
                } else {
                    children.addAll(pagesService.getByPropertyValueOrdered(OPTIMIZE_NAMES, OPTIMIZE_PSEUD, "id_pages", p.getId(), null, null));
                }
            }
            k++;
        }
        Long[] ids = new Long[0];
        ids = idsTmp.toArray(ids);
        wallpaperServiceCms.optimizeWallpaperCategories(ids);
    }

    @Override
    public void resetOptimizationCategory(Long id, Boolean optimized) {
        //1-st getting all last subpages
        List<Pages> children = pagesService.getPagesChildrenRecurciveOrderedWhere(OPTIMIZE_NAMES, OPTIMIZE_PSEUD, null, null, id);
        //2-nd callecting only last pages ids
        Long[] ids = new Long[children.size()];
        int k = 0;
        for (Pages p : children) {
            if (p.getLast()) {//TODO mb check type here ...
                ids[k] = p.getId();
                k++;
            }
        }
        ids = Arrays.copyOf(ids, k);
        wallpaperServiceCms.setWallpaperOptimizationCategories(ids, optimized);
    }

}
