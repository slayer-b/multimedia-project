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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @param <T> type of beans to use
 * @author demchuck.dima@gmail.com
 */
public interface ICmsService<T, ID extends Serializable> extends IFilterCmsService<T>, IMultiupdateCmsService<T, ID> {

//--------------------- view --------------------------------------------------

    /**
     * get all rows to show in cms
     *
     * @return list of objects
     */
    List<T> getAllShort(int firstItem, int quantity);

    /**
     * prepare model for view
     */
    void initView(Map<String, Object> model);

//--------------------- insert ------------------------------------------------

    /**
     * an existing bean may be passed
     * if it is needed to bind some parameters on it
     *
     * @return returns a bean for before insert phase
     */
    T getInsertBean(T obj);

    /**
     * inserts an appropriate row to database
     *
     * @param obj object to be saved
     * @return true if inserted
     */
    boolean insert(T obj);

    /**
     * here you can make all required attributes for insert (such as data for combo boxes ...)
     *
     * @param model with required attributes, and the new attributes will be added here
     */
    void initInsert(Map<String, Object> model);

//--------------------- update ------------------------------------------------

    /**
     * @param id of object to update
     * @return object for update
     */
    T getUpdateBean(ID id);

    /**
     * here you can make all required attributes for insert (such as data for combo boxes ...)
     *
     * @param model with required attributes, and the new attributes will be added here
     */
    void initUpdate(Map<String, Object> model);

    /**
     * updates a command
     *
     * @param command object to update
     * @return quantity of objects updated
     */
    int update(T command);

    /**
     * updates a command
     *
     * @param command object to update
     * @param names   property names to update
     * @return quantity of objects updated
     */
    int update(T command, String... names);

//--------------------- delete --------------------------------------------------

    /**
     * @param id an id of entity to delete
     */
    int deleteById(ID id);

    /**
     * @return class of bean that is used to retrieve data
     */
    Class<T> getBeanClass();
}
