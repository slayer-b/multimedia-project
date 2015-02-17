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

package gallery.web.controller.pages.types;

import com.multimedia.exceptions.WallpaperNotFound;
import com.multimedia.service.IPagesPseudonymService;
import com.multimedia.service.IPagesService;
import com.multimedia.service.IRubricImageService;
import com.multimedia.web.support.PaginatedListUtils;
import common.beans.KeepParameters;
import common.beans.PagerBean;
import common.beans.PagerBeanId;
import common.utils.CommonAttributes;
import common.utils.RequestUtils;
import gallery.model.beans.Pages;
import gallery.model.beans.Resolution;
import gallery.model.beans.Wallpaper;
import gallery.web.controller.pages.submodules.ASubmodule;
import gallery.web.controller.pages.submodules.EmptySubmodule;
import gallery.web.support.pages.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperGalleryType extends AWallpaperType {
    /** string constant that represents type for this page */
    public static final String TYPE="general_wallpaper_gallery";
	/** rus type */
	public static final String TYPE_RU="Wallpapers(галерея)";

	@Override
	public String getType() {return TYPE;}
	@Override
	public String getTypeRu() {return TYPE_RU;}

	/** attribute for storing categories vector for view */
	public static final String CATEGORIES_ATTRIBUTE = "categories";
	/** attribute for storing categories vector for view */
	public static final String WALLPAPERS_ATTRIBUTE = "wallpapers";
	/** attribute for storing categories vector for view */
	public static final String PAGENATION_ATTRIBUTE = "count";
	public static final String CUR_WALLPAPER_ATTRIBUTE = "big_wallpaper";
	public static final String CUR_PAGE_NUMBER_ATTRIBUTE = "cur_page_number";
	public static final String OPTIMIZATION_STRINGS_FULL = "wallpaper_optmization_full";
	public static final String OPTIMIZATION_STRINGS_SHORT = "wallpaper_optmization_short";
	public static final String OPTIMIZATION_STRINGS_TAGS = "wallpaper_optmization_tags";
	/** details (after clicking on a wallpaper of a category) */
	public static final int DETAILS_NAV_BUTTONS = 2;
    /** quantity of wallpapers(items) that will be shown in category */
    public static final int CATEGORY_WALLPAPERS = 15;
	/** main view of a category */
	public static final PaginatedListUtils PAGINATED_LIST_UTILS_CATEGORY = new PaginatedListUtils(CATEGORY_WALLPAPERS ,2);
	//first and last wallpaper
	private static final Long FIRST_PARAM = -1L;
	private static final Long LAST_PARAM = -2L;

	/** for keeping parameters */
	private KeepParameters detailsKeepParameters;
	private KeepParameters categoryKeepParameters;
	@Autowired
	private IPagesService pagesService;
    @Autowired
	private IPagesPseudonymService optimizationService;
    @Autowired
    private IRubricImageService rubricImageService;

	/** if pages contatains a child page */
    private String contentMainUrl;
    private String optimizationMainUrl;
    private String infoTopMainUrl;
    private String infoBottomMainUrl;

	/** if has no child pages and an wallpaper param is passed */
    private String contentDetailsUrl;
    private String optimizationDetailsUrl;

	/** if has no child pages and no wallpaper param is passed */
    private String contentCategoryUrl;
    private String optimizationCategoryUrl;
    private String infoTopCategoryUrl;
    private String infoBottomCategoryUrl;

	private static final String[] DETAILS_WHERE = {"id_pages","active"};
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, UrlBean url)
	{
		Pages p = Utils.getCurrentPage(request);

		List<Pages> categories = pagesService.getCategories(p.getId(), TYPE);
        List<Pages> navigation = Utils.getNavigation(request, super.config);

		if (categories!=null&& !categories.isEmpty()){
            setOptimization(0, navigation, request);
			main(request, categories, url);
		} else {
			//determining if we need details ...
			Long id_wallpaper = RequestUtils.getLongParam(request, "id_photo_nav");
			int totalCount = wallpaperService.getWallpapersRowCount(p.getId()).intValue();
			if (id_wallpaper==null){
				//logger.info("Pages --> category; id="+p.getId()+"; count = "+totalCount);
				category(request, p, navigation, totalCount, url);
			}else{
				//logger.info("Pages --> details; id="+p.getId()+"; count = "+totalCount);
				details(request, id_wallpaper, p, navigation, totalCount, url);
				Map<String, ASubmodule> hs = url.getSubmodules();
				hs = (hs==null?new HashMap<String, ASubmodule>():hs);
				hs.put(WallpaperRateType.TYPE, new EmptySubmodule());
				hs.put(WallpaperCommentAddType.TYPE, new EmptySubmodule());
				url.setSubmodules(hs);
			}
		}
	}

	public void main(HttpServletRequest request, List<Pages> categories, UrlBean url)	{
		request.setAttribute(CATEGORIES_ATTRIBUTE, categories);
		request.setAttribute(WALLPAPERS_ATTRIBUTE, rubricImageService.getImageUrls(categories));

		url.setContent(contentMainUrl);
		url.setOptimization(optimizationMainUrl);
        url.setPage_top(infoTopMainUrl);
        url.setPage_bottom(infoBottomMainUrl);
	}

	private static final String[] DET_RES_WHERE = {"width", "height"};
	private static final String[] DET_RES_RELAT = {"<=", "<="};
	private void details(HttpServletRequest request,
			Long id_wallpaper, Pages p, List<Pages> navigation, int totalCount, UrlBean url)
	{
		//creating bean for pagination
		PagerBeanId count = new PagerBeanId();
		count.setCurPageParam("id_photo_nav");
		count.setItemsCount(totalCount);
		count.setQueryString(detailsKeepParameters.getKeepParameters(request));
		//finding an id_wallpaper if it is first or last page
		Long id_wallpaper2;
		if (id_wallpaper.equals(FIRST_PARAM)){
			id_wallpaper2 = (Long)wallpaperService.getSinglePropertyU("min(id)", DETAILS_WHERE, new Object[]{p.getId(),Boolean.TRUE}, 0);
		} else if (id_wallpaper.equals(LAST_PARAM)){
			id_wallpaper2 = (Long)wallpaperService.getSinglePropertyU("max(id)", DETAILS_WHERE, new Object[]{p.getId(),Boolean.TRUE}, 0);
		} else {
			id_wallpaper2 = id_wallpaper;
		}
		Wallpaper curWallpaper = wallpaperService.getById(id_wallpaper2==null?id_wallpaper:id_wallpaper2);
		if ((curWallpaper==null)||(!curWallpaper.getId_pages().equals(p.getId()))){
			//and set an error if this wallpaper not exists or is not from this pages
			CommonAttributes.addErrorMessage("not_exists.wallpaper", request);
			category(request, p, navigation, totalCount, url);
			return;
		}
        if (!curWallpaper.getActive()) {
            throw new WallpaperNotFound(curWallpaper.getName(), curWallpaper.getFolder());
        }
		//setting resolutions to be available with current wallpaper
		List<Resolution> res = resolutionService.getByPropertiesValuePortionOrdered(null, null,
				DET_RES_WHERE, DET_RES_RELAT, new Object[]{curWallpaper.getWidth(), curWallpaper.getHeight()},
				0, 0, gallery.web.controller.resolution.Config.ORDER_BY, gallery.web.controller.resolution.Config.ORDER_HOW);
		curWallpaper.setResolutions(res);
		wallpaperService.updatePropertyById("views", 1L, id_wallpaper2);
		//get page of current wallpaper
		Long curNumber = wallpaperService.getWallpaperNumber(curWallpaper);
		request.setAttribute(CUR_PAGE_NUMBER_ATTRIBUTE, (curNumber - 1L) / PAGINATED_LIST_UTILS_CATEGORY.getItemsPerPage());

		//selecting left and right wallpapers for pagination
		List<Wallpaper> wallpapersLeft = wallpaperService.getWallpapersPaginatedId(curWallpaper.getId(), -DETAILS_NAV_BUTTONS, p.getId());
		List<Wallpaper> wallpapersRight = wallpaperService.getWallpapersPaginatedId(curWallpaper.getId(), DETAILS_NAV_BUTTONS, p.getId());

		//setting wallpapers collection
        List<Wallpaper> wallpapers = new ArrayList<Wallpaper>(2*DETAILS_NAV_BUTTONS+1);
		for (int i=0, size=DETAILS_NAV_BUTTONS-wallpapersLeft.size();i<size;i++) {
		    wallpapers.add(null);
		}
		//reverting collection ...
		for (int k=wallpapersLeft.size();k>0;) {
		    wallpapers.add(wallpapersLeft.get(--k));
		}
		wallpapers.add(curWallpaper);
		wallpapers.addAll(wallpapersRight);
		for (int i=0, size=DETAILS_NAV_BUTTONS-wallpapersRight.size();i<size;i++) {
            wallpapers.add(null);
        }
		//setting navigation buttons
		if (!wallpapersLeft.isEmpty()) {
		    count.setPrevPage(wallpapersLeft.get(0).getId());
		} else {
		    count.setPrevPage(LAST_PARAM);
		}
		if (!wallpapersRight.isEmpty()) {
		    count.setNextPage(wallpapersRight.get(0).getId());
		} else {
		    count.setNextPage(FIRST_PARAM);
		}
		count.setCurrentPage(curWallpaper.getId());
		count.setLastPage(LAST_PARAM);
		count.setFirstPage(FIRST_PARAM);
		//saving wallpapers in request attributes
		request.setAttribute(CUR_WALLPAPER_ATTRIBUTE, curWallpaper);
		request.setAttribute(WALLPAPERS_ATTRIBUTE, wallpapers);
		request.setAttribute(PAGENATION_ATTRIBUTE, count);
		//creating url bean
		url.setContent(contentDetailsUrl);
		url.setOptimization(optimizationDetailsUrl);
	}

	public void category(HttpServletRequest request,
			Pages p, List<Pages> navigation, int totalCount, UrlBean url)
	{
		int curPageNum = PaginatedListUtils.getPageNumber(request);
		PagerBean count = PAGINATED_LIST_UTILS_CATEGORY.getPagerBean2(curPageNum, totalCount,
				categoryKeepParameters.getKeepParameters(request));
		List<Wallpaper> wallpapers = wallpaperService.getWallpapersPaginated(
		        count.getFirstItemNumber(),
				PAGINATED_LIST_UTILS_CATEGORY.getItemsPerPage(), p.getId());

		request.setAttribute(WALLPAPERS_ATTRIBUTE, wallpapers);
		request.setAttribute(PAGENATION_ATTRIBUTE, count);
        setOptimization(curPageNum, navigation, request);
		url.setContent(contentCategoryUrl);
		if (curPageNum==0) {
			url.setOptimization(optimizationMainUrl);
		} else {
			url.setOptimization(optimizationCategoryUrl);
		}
        url.setPage_top(infoTopCategoryUrl);
        url.setPage_bottom(infoBottomCategoryUrl);
	}

    private static final String[] OPTIMIZATION_WHERE = {"id_pages", "useInPages"};
	/**
	 * get optimization phrases
	 * @param cur_page cur page in pagination (0, if no pagination)
	 * @param navigation navigation chain for current wallpapers page
	 */
	protected void setOptimization(int cur_page, List<Pages> navigation, HttpServletRequest request){
		if (navigation==null) {
		    return;
		}
		//1-st get count of optimization for each parent page
        ArrayList<String> rez = new ArrayList<String>(navigation.size());
		for (Pages p: navigation){
			Long count = optimizationService.getRowCount(OPTIMIZATION_WHERE, new Object[]{p.getId(),Boolean.TRUE});
			if (count>0L){
                String text = (String)optimizationService.getSinglePropertyUOrdered("text",
                        OPTIMIZATION_WHERE, new Object[]{p.getId(), Boolean.TRUE},
                        cur_page % count.intValue());
				if (text!=null&&!text.isEmpty()){
					rez.add(text);
				}
			}
		}
		if (!rez.isEmpty()){
            StringBuilder full = new StringBuilder(rez.get(0));
            StringBuilder tags = new StringBuilder(rez.get(0));
            for (int i=1;i<rez.size();i++){
                full.append(" - ");
                full.append(rez.get(i));
				tags.append(", ");
                tags.append(rez.get(i));
            }
            request.setAttribute(OPTIMIZATION_STRINGS_FULL, full.toString());
            request.setAttribute(OPTIMIZATION_STRINGS_TAGS, tags.toString());
            request.setAttribute(OPTIMIZATION_STRINGS_SHORT, rez.get(rez.size() - 1));
		}
	}

    @Required
	public void setDetailsKeepParameters (KeepParameters value) {this.detailsKeepParameters = value;}
    @Required
	public void setCategoryKeepParameters(KeepParameters value) {this.categoryKeepParameters = value;}
    @Required
	public void setContentMainUrl(String contentMainUrl) {this.contentMainUrl = contentMainUrl;}
    @Required
	public void setContentCategoryUrl(String contentCategoryUrl) {this.contentCategoryUrl = contentCategoryUrl;}
    @Required
	public void setContentDetailsUrl(String contentDetailsUrl) {this.contentDetailsUrl = contentDetailsUrl;}
    @Required
	public void setOptimizationMainUrl(String optimizationMainUrl) {this.optimizationMainUrl = optimizationMainUrl;}
    @Required
	public void setInfoTopMainUrl(String infoTopMainUrl) {this.infoTopMainUrl = infoTopMainUrl;}
    @Required
	public void setInfoBottomMainUrl(String infoBottomMainUrl) {this.infoBottomMainUrl = infoBottomMainUrl;}
    @Required
	public void setOptimizationDetailsUrl(String optimizationDetailsUrl) {this.optimizationDetailsUrl = optimizationDetailsUrl;}
    @Required
	public void setOptimizationCategoryUrl(String optimizationCategoryUrl) {this.optimizationCategoryUrl = optimizationCategoryUrl;}
    @Required
	public void setInfoTopCategoryUrl(String infoTopCategoryUrl) {this.infoTopCategoryUrl = infoTopCategoryUrl;}
    @Required
	public void setInfoBottomCategoryUrl(String infoBottomCategoryUrl) {this.infoBottomCategoryUrl = infoBottomCategoryUrl;}
}
