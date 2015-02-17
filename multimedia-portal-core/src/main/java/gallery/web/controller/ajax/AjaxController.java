/*
 *  Copyright 2010 demchuck.dima@gmail.com
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

package gallery.web.controller.ajax;

import com.multimedia.model.beans.PagesRubrication;
import com.multimedia.service.IPagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("ajaxController")
public class AjaxController {
    @Autowired
	private IPagesService pagesService;

	@RequestMapping("/ajax.json")
	@ResponseBody
	public List<PagesRubrication> handleRequest(@RequestParam("id_pages") Long id_pages) {
		return pagesService.getPagesRubricationByIdPages(id_pages);
	}

}
