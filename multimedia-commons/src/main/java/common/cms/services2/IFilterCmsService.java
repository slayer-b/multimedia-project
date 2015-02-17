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
package common.cms.services2;

import java.util.List;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public interface IFilterCmsService<T> {

    /**
     * here you can make all required attributes for filter (such as data for combo boxes ...)
     *
     * @param model with required attributes, and the new attributes will be added here
     */
    void initFilter(Map<String, Object> model);

    /**
     * @param propertyName  name of property to search by
     * @param propertyValue value of property
     * @return list of data that matches given criteria
     */
    List<T> getFilteredByPropertiesValue(List<String> propertyName, List<Object> propertyValue, int firstItem, int quantity);

    /**
     * @param propertyName  name of property to search by
     * @param propertyValue value of property
     * @return quantity of items that matches given criteria
     */
    Long getCountByPropertiesValue(List<String> propertyName, List<Object> propertyValue);
}
