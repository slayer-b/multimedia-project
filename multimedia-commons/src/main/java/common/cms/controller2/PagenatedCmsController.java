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

import com.multimedia.web.support.PaginatedListUtils;
import common.cms.delegates.KeepParamsBuilder;
import common.cms.utils.DefaultsFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Map;

/**
 * adds pagination functionality.
 *
 * @author Dmitriy_Demchuk
 */
public abstract class PagenatedCmsController<T, ID extends Serializable> extends AGenericCmsController<T, ID> {

    protected final PaginatedListUtils paginatedListUtils = DefaultsFactory.getListUtils();

    @ModelAttribute
    public void populatePaginationData(@RequestParam(value = "page_number", required = false) Integer currentPage,
                                       @RequestParam(value = "page_size", required = false) Integer itemsPerPage, Map<String, Object> model) {

        model.put("page_number", currentPage == null ? paginatedListUtils.getStartPageNumber() : currentPage);
        model.put("page_size", itemsPerPage == null ? paginatedListUtils.getItemsPerPage() : itemsPerPage);
    }

    @Override
    public void initKeepParameters(KeepParamsBuilder builder) {
        super.initKeepParameters(builder);
        builder.addParameter("page_size");
    }


}
