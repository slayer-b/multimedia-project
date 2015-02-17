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

package common.cms.delegates;

import com.multimedia.web.support.PaginatedListUtils;
import common.beans.PagerBean;
import common.cms.ICmsConfig;
import common.cms.services2.ICmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy_Demchuk
 */
public abstract class FilterCmsDelegate<T, ID extends Serializable> {
    private final Logger logger = LoggerFactory.getLogger(FilterCmsDelegate.class);

    private final ICmsConfig config;
    private final ICmsService<T, ID> cmsService;

    private final KeepParamsDelegate keepParameters;
    private final PaginatedListUtils paginatedListUtils;

    public FilterCmsDelegate(ICmsConfig config, ICmsService<T, ID> cmsService,
                             KeepParamsDelegate keepParameters, PaginatedListUtils paginatedListUtils) {
        this.config = config;
        this.cmsService = cmsService;
        this.keepParameters = keepParameters;
        this.paginatedListUtils = paginatedListUtils;
    }

    public String doView(Map<String, Object> model) {
        logger.debug("do=view, filter");
        cmsService.initFilter(model);
        //1-st get pagination data
        Integer pageNum = (Integer) model.get("page_number");
        Integer pageSize = (Integer) model.get("page_size");
        //2-nd get data for filter
        List<String> tempAliases = new ArrayList<String>(getFilterProperties().size());
        List<Object> tempValues = new ArrayList<Object>(getFilterProperties().size());
        int j = 0;
        for (String s : getFilterProperties()) {
            Object value = model.get(s);
            if (value != null) {
                tempAliases.add(getFilterAliases().get(j));
                tempValues.add(value);
            }
            j++;
        }
        //3-rd creating keep parameters
        String keepParameters = this.keepParameters.getKeepParameters(model).toString();
        //4-rth counting actually select range, getting records and saving to model
        Long itemsCount = cmsService.getCountByPropertiesValue(tempAliases, tempValues);
        PagerBean pb = paginatedListUtils.getPagerBean(pageNum, itemsCount.intValue(), pageSize, keepParameters);
        model.put("page_counter", pb);//TODO remove('this.') if it will be fixed in hibernate
        model.put(config.getContentDataAttribute(), cmsService.getFilteredByPropertiesValue(tempAliases, tempValues,
                pb.getFirstItemNumber(), pb.getItemsPerPage()));

        model.put(config.getContentUrlAttribute(), config.getContentViewTemplate());
        return config.getTemplateUrl();
    }

    /**
     * names of properties for filtering data, may return static array
     */
    public abstract List<String> getFilterProperties();

    /**
     * property aliases for filtering data, may return static array
     */
    public abstract List<String> getFilterAliases();
}
