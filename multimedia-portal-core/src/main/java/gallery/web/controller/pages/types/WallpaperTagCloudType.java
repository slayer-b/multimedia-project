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

import com.multimedia.service.IPagesPseudonymService;
import com.multimedia.web.support.PaginatedListUtils;
import common.beans.KeepParameters;
import common.beans.PagerBean;
import common.bind.ABindValidator;
import gallery.model.beans.Pages;
import gallery.model.beans.Wallpaper;
import gallery.model.command.TagCloudData;
import gallery.model.command.TagCloudView;
import gallery.web.support.pages.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperTagCloudType extends AWallpaperType {
    /** string constant that represents type for this page */
    public static final String TYPE="general_wallpaper_tagcloud";
	/** rus type */
	public static final String TYPE_RU="Wallpapers(облако тегов)";

	@Override
	public String getType() {return TYPE;}
	@Override
	public String getTypeRu() {return TYPE_RU;}

	public static final String OPTIMIZATION_STRING = "wallpaper_opt_phrase";

	private String contentUrl;
	private String searchUrl;
	private String optimizationUrl;
	private String infoTopUrl;
	private String infoBottomUrl;

	@Autowired
	private IPagesPseudonymService optimizationService;
	/** pagination config */
	private static final PaginatedListUtils PAGINATION_UTILS = new PaginatedListUtils(15 ,2);
	private KeepParameters keepParameters;
	private ABindValidator bindValidator;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, UrlBean url)
	{
		TagCloudView tagCloud = new TagCloudView();
		BindingResult res = bindValidator.bindAndValidate(tagCloud, request);
		if (res.hasErrors()||tagCloud.isEmpty()){
			//common.CommonAttributes.addErrorMessage("form_errors", request);

			request.setAttribute(config.getContentDataAttribute(), new TagCloudData(wallpaperService));
			url.setContent(searchUrl);
		}else{
			String[] props = {"active", "tags"};
			String[][] relations = {new String[]{"="}, new String[]{"like", "like", "like", "like"}};
			Object[][] values = {new Object[]{Boolean.TRUE}, tagCloud.getTagsLike()};
			int totalCount =((Long)
					wallpaperService.getSinglePropertyU("count(*)", props, relations, values, 0, null, null)).intValue();
			int curPageNum = PaginatedListUtils.getPageNumber(request);
			PagerBean count = PAGINATION_UTILS.getPagerBean2(curPageNum, totalCount,
					keepParameters.getKeepParameters(request));

			int firstItem = count.getFirstItemNumber();
			List<Wallpaper> wallpapers = wallpaperService.getByPropertiesValuesPortionOrdered(null, null,
					props, relations, values, firstItem, PAGINATION_UTILS.getItemsPerPage(), null, null);

			tagCloud.setData(wallpapers);
			tagCloud.setPager(count);

			request.setAttribute(config.getContentDataAttribute(), tagCloud);

			request.setAttribute(OPTIMIZATION_STRING,
				getOptimizationPhraze(curPageNum, Utils.getNavigation(request, super.config)));
			url.setContent(contentUrl);
			url.setPage_top(infoTopUrl);
			url.setPage_bottom(infoBottomUrl);
			url.setOptimization(optimizationUrl);
		}
	}

    private static final String[] OPTIMIZATION_WHERE = {"id_pages", "useInPages"};
	public String getOptimizationPhraze(int cur_page, List<Pages> navigation){
		if (navigation!=null&&navigation.size()>1){
			//getting pre last item
			Long id = navigation.get(navigation.size()-2).getId();
			Long count = optimizationService.getRowCount(OPTIMIZATION_WHERE, new Object[]{id, Boolean.TRUE});
			if (count>0L){
				int pageNum = cur_page % count.intValue();
				return (String)optimizationService.getSinglePropertyUOrdered("text", OPTIMIZATION_WHERE, new Object[]{id, Boolean.TRUE}, pageNum);
			}
		}
		return null;
	}

    @Required
	public void setKeepParameters(KeepParameters keepParameters) {this.keepParameters = keepParameters;}
    @Required
	public void setBindValidator(ABindValidator bindValidator) {this.bindValidator = bindValidator;}
    @Required
	public void setContentUrl(String contentUrl) {this.contentUrl = contentUrl;}
    @Required
	public void setSearchUrl(String value) {this.searchUrl = value;}
    @Required
	public void setOptimizationUrl(String optimizationUrl) {this.optimizationUrl = optimizationUrl;}
    @Required
	public void setInfoTopUrl(String infoTopUrl) {this.infoTopUrl = infoTopUrl;}
    @Required
	public void setInfoBottomUrl(String infoBottomUrl) {this.infoBottomUrl = infoBottomUrl;}

}
