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

import common.cms.delegates.FilterCmsDelegate;
import common.cms.delegates.KeepParamsBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * this controller shows records filtered.
 *
 * @author Dmitriy_Demchuk
 */
public abstract class FilterCmsController<T> extends PagenatedCmsController<T, Long> {

    private FilterCmsDelegate<T, Long> delegate;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        delegate = new FilterCmsDelegate<T, Long>(config, cmsService, keepParameters, paginatedListUtils) {

            @Override
            public List<String> getFilterProperties() {
                return FilterCmsController.this.getFilterProperties();
            }

            @Override
            public List<String> getFilterAliases() {
                return FilterCmsController.this.getFilterAliases();
            }

        };
    }

    @RequestMapping
    public String doView(Map<String, Object> model) {
        return delegate.doView(model);
    }

    //TODO: make filter properties and aliases to be called only once on controller creation

    /**
     * names of properties for filtering data, may return static array
     */
    public abstract List<String> getFilterProperties();

    /**
     * property aliases for filtering data, may return static array
     */
    public abstract List<String> getFilterAliases();

    // add filter properties to keep parameters, returns new array each time
    @Override
    public void initKeepParameters(KeepParamsBuilder builder) {
        super.initKeepParameters(builder);
        builder.addParameters(getFilterProperties());
    }

}
