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
package common.cms.controller2;

import common.beans.PagerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public abstract class SimpleGenericCmsController<T> extends GenericCmsController<T> {

    private final Logger logger = LoggerFactory.getLogger(SimpleGenericCmsController.class);

    @Override
    @RequestMapping
    public String doView(Map<String, Object> model) {
        logger.debug("do=view");
        cmsService.initView(model);
        //1-st get pagination data
        Integer pageNum = (Integer) model.get("page_number");
        Integer pageSize = (Integer) model.get("page_size");
        //2-nd creating keep parameters
        String keepParameters = getKeepParameters(model).toString();
        //3-rd counting actually select range, getting records and saving to model
        Long itemsCount = cmsService.getCountByPropertiesValue(null, null);
        PagerBean pb = this.paginatedListUtils.getPagerBean(pageNum, itemsCount.intValue(), pageSize, keepParameters);
        model.put("page_counter", pb);

        model.put(config.getContentDataAttribute(), cmsService.getAllShort(pb.getFirstItemNumber(), pb.getItemsPerPage()));
        model.put(config.getContentUrlAttribute(), config.getContentViewTemplate());
        return config.getTemplateUrl();
    }

}
