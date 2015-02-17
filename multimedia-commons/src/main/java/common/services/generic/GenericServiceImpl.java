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

package common.services.generic;

import common.dao.IGenericDAO;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class GenericServiceImpl<T, ID extends Serializable> implements IGenericService<T, ID> {

    protected IGenericDAO<T, ID> dao;

    public GenericServiceImpl() {
        //delete this if it will be possible to proxy class without default constructor
    }

    public GenericServiceImpl(IGenericDAO<T, ID> dao) {
        this.dao = dao;
    }

    @PostConstruct
    public void init() {
        Assert.notNull(dao, "Dao must not be null");
    }

    @Override
    public void save(T entity) {
        dao.makePersistent(entity);
    }

    @Override
    public int update(T entity, String... names) {
        return dao.update(entity, names);
    }

    @Override
    public void merge(T entity) {
        dao.merge(entity);
    }

    @Override
    public int deleteById(ID id) {
        return dao.deleteById(id);
    }

    @Override
    public T getById(ID id) {
        return dao.getById(id);
    }

    @Override
    public int updatePropertiesById(String[] propertyNames, Object[] propertyValues, ID[] idValues) {
        return dao.updatePropertiesById(propertyNames, propertyValues, idValues);
    }

    @Override
    public int updateObjectArrayShortById(String[] propertyNames, ID[] idValues, Object[]... propertyValues) {
        return dao.updateObjectArrayShortById(propertyNames, idValues, propertyValues);
    }

    @Override
    public int updateObjectArrayShortByProperty(String[] propertyNames, Object[] propertyValues, String property, Object[] values) {
        return dao.updateObjectArrayShortByProperty(propertyNames, propertyValues, property, values);
    }

    @Override
    public int updatePropertyById(String propertyName, Object value, ID id) {
        return dao.updatePropertyById(propertyName, value, id);
    }

    @Override
    public Long getRowCount(String propertyName, Object propertyValue) {
        return dao.getRowCount(propertyName, propertyValue);
    }

    @Override
    public Long getRowCount(String propertyName, List<Object> propertyValue) {
        return dao.getRowCount(propertyName, propertyValue);
    }

    @Override
    public Long getRowCount(String[] propertyName, Object[] propertyValue) {
        return dao.getRowCount(propertyName, propertyValue);
    }

    @Override
    public Long getRowCount(List<String> propertyName, List<Object> propertyValue) {
        return dao.getRowCount(propertyName, propertyValue);
    }

    @Override
    public Object getSinglePropertyU(String prop, String propName, Object propValue) {
        return dao.getSinglePropertyU(prop, propName, propValue);
    }

    @Override
    public Object getSinglePropertyU(String prop, String propName, Object propValue, int number) {
        return dao.getSinglePropertyU(prop, propName, propValue, number);
    }

    @Override
    public Object getSinglePropertyU(String property, String propName, Object propValue, int number, String[] orderBy, String[] orderHow) {
        return dao.getSinglePropertyU(property, propName, propValue, number, orderBy, orderHow);
    }

    @Override
    public Object getSinglePropertyU(String prop, String[] propNames, Object[] propValues, int number) {
        return dao.getSinglePropertyU(prop, propNames, propValues, number, null, null);
    }

    @Override
    public Object getSinglePropertyU(String prop) {
        return dao.getSinglePropertyU(prop);
    }

    @Override
    public List getSingleProperty(String property, String[] propName, Object[] propValue, int number, int count, String[] orderBy, String[] orderHow) {
        return dao.getSingleProperty(property, propName, propValue, number, count, orderBy, orderHow);
    }

    @Override
    public List<T> getOrdered(String[] propertyNames, String[] orderBy, String[] orderHow) {
        return dao.getAllShortOrdered(propertyNames, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertyValueOrdered(String[] properties, String propertyName, Object propertyValue, String[] orderBy, String[] orderHow) {
        return dao.getByPropertyValuePortionOrdered(properties, properties, propertyName, propertyValue, 0, -1, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertyValueOrdered(String[] properties, String[] aliases, String propertyName, Object propertyValue, String[] orderBy, String[] orderHow) {
        return dao.getByPropertyValuePortionOrdered(properties, aliases, propertyName, propertyValue, 0, -1, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertyValuePortionOrdered(String[] properties, String[] aliases, String propertyName, Object propertyValue,
                                                    int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        return dao.getByPropertyValuePortionOrdered(properties, aliases, propertyName, propertyValue, firstResult, resultCount, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertiesValueOrdered(String[] properties, String[] propertyName, Object[] propertyValue, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuePortionOrdered(properties, properties, propertyName, propertyValue, 0, -1, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertiesValueOrdered(String[] properties, String[] aliases, String[] propertyName, Object[] propertyValue, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuePortionOrdered(properties, aliases, propertyName, propertyValue, 0, -1, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertiesValuePortionOrdered(String[] properties, String[] aliases, String[] propertyName, Object[] propertyValue,
                                                      int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuePortionOrdered(properties, aliases, propertyName, propertyValue, firstResult, resultCount, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertiesValuePortionOrdered(List<String> properties, List<String> aliases, List<String> propertyName, List<Object> propertyValue,
                                                      int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuePortionOrdered(properties, aliases, propertyName, propertyValue, firstResult, resultCount, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertiesValuesOrdered(String[] properties, String[] propertyName, Object[][] propertyValue, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuesPortionOrdered(properties, properties, propertyName, propertyValue, 0, -1, orderBy, orderHow);
    }

    @Override
    public int deleteByPropertyValue(String propertyName, Object propertyValue) {
        return dao.deleteByPropertyValue(propertyName, propertyValue);
    }

    @Override
    public int deleteById(ID[] id) {
        return dao.deleteById(id);
    }

    @Override
    public List<T> getByPropertyValueRelationOrdered(String[] properties, String[] aliases,
                                                     String where_prop, Object where_val, String where_rel, String[] orderBy, String[] orderHow) {
        return dao.getByPropertyValuePortionOrdered(properties, aliases, where_prop, where_val, where_rel, 0, 0, orderBy, orderHow);
    }

    @Override
    public void detach(T entity) {
        dao.detach(entity);
    }

    @Override
    public List<T> getByPropertiesValuesPortionOrdered(String[] propertyNames, String[] propertyAliases, String[] propertiesWhere, String[][] relations, Object[][] propertyValues, int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuePortionOrdered(propertyNames, propertyAliases, propertiesWhere, relations, propertyValues, firstResult, resultCount, orderBy, orderHow);
    }

    @Override
    public Object getSinglePropertyU(String property, String[] propName, String[][] relations, Object[][] propValue, int number, String[] orderBy, String[] orderHow) {
        return dao.getSinglePropertyU(property, propName, relations, propValue, number, orderBy, orderHow);
    }

    @Override
    public List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases, String[] propertiesWhere, String[] relations, Object[] propertyValues, int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        return dao.getByPropertiesValuePortionOrdered(propertyNames, propertyAliases, propertiesWhere, propertyValues, relations, firstResult, resultCount, orderBy, orderHow);
    }

    @Override
    public int updateCollection(Collection<T> entities, String... propertyNames) {
        return dao.updateCollection(entities, propertyNames);
    }

    @Override
    public Class<T> getBeanClass() {
        return dao.getPersistentClass();
    }
}
