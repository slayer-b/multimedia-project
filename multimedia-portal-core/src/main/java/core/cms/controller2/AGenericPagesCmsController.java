/*
 *  Copyright 2010 demchuck.dima@gmail.com.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package core.cms.controller2;

import common.cms.controller2.FilterGenericCmsController;
import common.cms.delegates.KeepParamsBuilder;
import core.cms.services2.IPagesCmsNavigationService;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * this class creates navigation data,
 * adds id_pages_nav parameter to keepParameterNames
 * @author demchuck.dima@gmail.com
 */
public abstract class AGenericPagesCmsController<T> extends FilterGenericCmsController<T>{
	protected IPagesCmsNavigationService pagesCmsNavigationService;

	@ModelAttribute
    public void populatePagesNavigation(@RequestParam(value="id_pages_nav", required=false) Long id_pages_nav, Map<String, Object> model) {
		model.put("id_pages_nav", id_pages_nav);
		model.put(config.getNavigationDataAttribute(), pagesCmsNavigationService.getNavigationData(id_pages_nav));
    }

	@Resource(name="pagesCmsNavigationService")
	public void setPagesCmsNavigationService(IPagesCmsNavigationService pagesCmsNavigationService) {
		this.pagesCmsNavigationService = pagesCmsNavigationService;
	}

	@Override
	public void initKeepParameters(KeepParamsBuilder builder) {
	    super.initKeepParameters(builder);
	    builder.addParameter("id_pages_nav");
	}

}