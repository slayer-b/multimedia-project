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

package core.cms.services2;

import com.multimedia.service.IPagesService;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("pagesCmsNavigationService")
public class PagesCmsNavigationServiceImpl implements IPagesCmsNavigationService{
	@Autowired
    private IPagesService pagesService;

	@Override
	public List<Pages> getNavigationData(Long id_pages) {
		return pagesService.getAllPagesParents(id_pages, Config.NAVIGATION_PSEUDONYMES);
	}

}
