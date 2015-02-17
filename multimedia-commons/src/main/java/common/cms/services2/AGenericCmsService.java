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

import common.services.generic.IGenericService;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public abstract class AGenericCmsService<T, ID extends Serializable> implements ICmsService<T, ID> {

    protected IGenericService<T, ID> service;

    @PostConstruct
    public void init() {
        StringBuilder sb = new StringBuilder();
        common.utils.MiscUtils.checkNotNull(service, "service", sb);
        if (sb.length() > 0) {
            throw new NullPointerException(sb.toString());
        }
    }

    //--------------------- insert ------------------------------------------------
    @Override
    public boolean insert(T obj) {
        service.save(obj);
        return true;
    }

    /**
     * empty method
     */
    @Override
    public void initInsert(Map<String, Object> model) {
    }

    /**
     * returns obj or throws NullPointerException if it is null
     */
    @Override
    public T getInsertBean(T obj) {
        if (obj == null) {
            throw new NullPointerException("bean must not be null");
        } else {
            return obj;
        }
    }

    //--------------------- update ------------------------------------------------
    @Override
    public T getUpdateBean(ID id) {
        return service.getById(id);
    }

    /**
     * empty method
     */
    @Override
    public void initUpdate(Map<String, Object> model) {
    }

    @Override
    public int update(T command) {
        service.save(command);
        return 1;
    }

    @Override
    public int update(T command, String... names) {
        return service.update(command, names);
    }

    //--------------------- delete --------------------------------------------------
    @Override
    public int deleteById(ID id) {
        return service.deleteById(id);
    }

    //--------------------- filter --------------------------------------------------
    @Override
    public void initFilter(Map<String, Object> model) {
        initView(model);
    }

    @Override
    public Long getCountByPropertiesValue(List<String> propertyName, List<Object> propertyValue) {
        return service.getRowCount(propertyName, propertyValue);
    }

//-------------------- multi update ---------------------------------------------

    /**
     * updates all properties
     */
    @Override
    public int saveOrUpdateCollection(Collection<T> c) {
        return service.updateCollection(c);
    }

    @Override
    public Class<T> getBeanClass() {
        return service.getBeanClass();
    }

    //------------------ list items --------------------------------------------------
    @Override
    public void initView(Map<String, Object> model) {
    }

    @Override
    public List<T> getAllShort(int firstItem, int quantity) {
        //TODO: make sorted
        return service.getByPropertiesValuePortionOrdered(getListPropertyNames(), getListPropertyAliases(), null, null, firstItem, quantity, getListOrderBy(), getListOrderHow());
    }

    @Override
    public List<T> getFilteredByPropertiesValue(List<String> propertyName, List<Object> propertyValue, int firstItem, int quantity) {
        //TODO: make sorted
        return service.getByPropertiesValuePortionOrdered(getListPropertyNames(), getListPropertyAliases(), propertyName, propertyValue, firstItem, quantity, getListOrderBy(), getListOrderHow());
    }

    /**
     * override if you want to return not all properties
     */
    public List<String> getListPropertyNames() {
        return null;
    }

    /**
     * if you have overrided getPropertyNamesForList override it to return result property names
     */
    public List<String> getListPropertyAliases() {
        return null;
    }

    /**
     * override if you want to order resulting list
     */
    public String[] getListOrderBy() {
        return null;
    }

    /**
     * override if you want to order resulting list
     */
    public String[] getListOrderHow() {
        return null;
    }
}
