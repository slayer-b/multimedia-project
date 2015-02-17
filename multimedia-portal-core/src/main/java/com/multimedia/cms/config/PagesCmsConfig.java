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

package com.multimedia.cms.config;

import common.cms.config.CmsConfig;
import common.web.IControllerConfig;
import core.IPagesConfig;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class PagesCmsConfig extends CmsConfig implements IPagesConfig {

    private String idPagesParamName = "id_pages_nav";
	private String siteConfigAttr = "site_config";

	private IControllerConfig conf;
   
	public void setConf(IControllerConfig conf) {this.conf = conf;}

	@Override
	public String getNavigationDataAttribute() {return conf.getNavigationDataAttribute();}

	@Override
	public String getTemplateUrl() {return conf.getTemplateUrl();}

	@Override
	public String getUrlAttribute() {return conf.getUrlAttribute();}

	@Override
	public String getContentDataAttribute() {return conf.getContentDataAttribute();}

	public String getSiteConfigAttribute() {return this.siteConfigAttr;}
	public void setSiteConfigAttribute(String value) {this.siteConfigAttr = value;}

    @Override
    public String getIdPagesParamName() {return idPagesParamName;}
    public void setIdPagesParamName(String value) {this.idPagesParamName = value;}

}
