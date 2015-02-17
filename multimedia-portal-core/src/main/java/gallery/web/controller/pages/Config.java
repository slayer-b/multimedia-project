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

package gallery.web.controller.pages;

import common.web.ControllerConfig;
import core.IPagesConfig;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class Config extends ControllerConfig implements IPagesConfig{
	public static final String CURRENT_PAGE_ATTRIBUTE = "page";
	public static final String PAGE_KEEP_PARAMETERS = "page_params";

	public static final String[] NAVIGATION_PSEUDONYMES = {"id","id_pages","name","type"};

	private String site_config_attr = "site_config";

    private String id_pagesParamName = "id_pages_nav";

	@Override
	public String getIdPagesParamName() {return id_pagesParamName;}
	public void setId_pagesParamName(String value) {this.id_pagesParamName = value;}

	public String getSiteConfigAttribute() {return this.site_config_attr;}
	public void setSiteConfigAttribute(String value) {this.site_config_attr = value;}

}
