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

package com.multimedia.cms.controller;

import common.cms.ICmsConfig;
import common.cms.delegates.KeepParamsBuilder;
import common.cms.services2.ICmsService;
import core.cms.controller2.AGenericPagesCmsController;
import gallery.model.beans.PagesPseudonym;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Controller("pagesPseudonymCmsController")
@RequestMapping("/cms/pagesPseudonym/index.htm")
public class PagesPseudonymCmsController extends AGenericPagesCmsController<PagesPseudonym> {
    private static final List<String> FILTER_PROPERTIES = Arrays.asList("id_pages_nav");
    private static final List<String> FILTER_ALIASES = Arrays.asList("this.id_pages");

    @Override
    public List<String> getFilterProperties() {
        return FILTER_PROPERTIES;
    }

    @Override
    public List<String> getFilterAliases() {
        return FILTER_ALIASES;
    }

    @Override
    public void initKeepParameters(KeepParamsBuilder builder) {
        super.initKeepParameters(builder);
        builder.addParameter("do");
    }

// ------------- initialization -------------------
    @Resource
    public void setPagesPseudonymCmsConfig(ICmsConfig pagesPseudonymCmsConfig) {
        this.config = pagesPseudonymCmsConfig;
    }

    @Resource
    public void setPagesPseudonymServiceCms(ICmsService<PagesPseudonym, Long> pagesPseudonymServiceCms) {
        this.cmsService = pagesPseudonymServiceCms;
    }

}
