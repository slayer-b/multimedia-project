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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @param <T> Should be modified for project-specific common functions
 * @author demchuck.dima@gmail.com
 */
public interface IGenericService<T, ID extends Serializable> {

    void save(T entity);

    int update(T entity, String... names);

    void merge(T entity);

    void detach(T entity);

    int deleteById(ID id);

    /**
     * deletes chosen items
     *
     * @param id identifier of items
     * @return affected items
     */
    int deleteById(ID[] id);

    int deleteByPropertyValue(String propertyName, Object propertyValue);

    /**
     * Updates entities with given ids.
     * Sets propertyNames of entities with given ids with given name to given propertyValues.
     * i.e. all entities have same values at given properties.
     *
     * @param propertyNames  names of properties to update
     * @param propertyValues values to set
     * @param idValues       ids of objects to update
     * @return quantity of changed objects
     */
    int updatePropertiesById(String[] propertyNames, Object[] propertyValues, ID[] idValues);

    int updateObjectArrayShortById(String[] propertyNames, ID[] idValues, Object[]... propertyValues);

    /**
     * updates propertyNames to propertyValues in all entities with property = Values
     *
     * @param propertyNames  names of properties to update
     * @param propertyValues new values
     * @param property       name
     * @param values         values
     */
    int updateObjectArrayShortByProperty(String[] propertyNames, Object[] propertyValues, String property, Object[] values);

    /**
     * @param propertyName name of property to update
     * @param value        an amount by which to increase property
     * @param id           id of entity to update
     * @return updated entities count (must be 1)
     */
    int updatePropertyById(String propertyName, Object value, ID id);

    /**
     * this method saves or updates collection of entities
     * if property names is null, update all properties
     * else update only give entities
     *
     * @param entities collection of entities to be updated
     * @return quantity of updated entities
     */
    int updateCollection(Collection<T> entities, String... propertyNames);

    T getById(ID id);

    List<T> getOrdered(String[] propertyNames, String[] orderBy, String[] orderHow);

    List<T> getByPropertyValueOrdered(String[] properties, String propertyName, Object propertyValue, String[] orderBy, String[] orderHow);

    List<T> getByPropertyValueOrdered(String[] properties, String[] aliases, String propertyName, Object propertyValue, String[] orderBy, String[] orderHow);

    List<T> getByPropertyValuePortionOrdered(String[] properties, String[] aliases, String propertyName, Object propertyValue,
                                             int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValueOrdered(String[] propNames, String[] propertyName, Object[] propertyValue, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValueOrdered(String[] propNames, String[] aliases, String[] propertyName, Object[] propertyValue, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValuePortionOrdered(String[] propNames, String[] aliases, String[] propertyName, Object[] propertyValue,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValuePortionOrdered(List<String> propNames, List<String> aliases, List<String> propertyName, List<Object> propertyValue,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValuesOrdered(String[] propNames, String[] propertyName, Object[][] propertyValue, String[] orderBy, String[] orderHow);

    List<T> getByPropertyValueRelationOrdered(String[] properties, String[] aliases,
                                              String where_prop, Object where_val, String where_rel, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                               String[] propertiesWhere, String[] relations, Object[] propertyValues,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    List<T> getByPropertiesValuesPortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                String[] propertiesWhere, String[][] relations, Object[][] propertyValues,
                                                int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    //public Object getSinglePropertyU(String prop, String propNames[], Object propValues[]);
    Long getRowCount(String propertyName, Object propertyValue);

    Long getRowCount(String[] propertyName, Object[] propertyValue);

    Long getRowCount(List<String> propertyName, List<Object> propertyValue);

    Long getRowCount(String propertyName, List<Object> propertyValue);

    Object getSinglePropertyU(String prop);

    Object getSinglePropertyU(String prop, String propName, Object propValue);

    Object getSinglePropertyU(String prop, String propName, Object propValue, int number);

    Object getSinglePropertyU(String prop, String[] propName, Object[] propValue, int number);

    Object getSinglePropertyU(String property, String propName, Object propValue, int number, String[] orderBy, String[] OrderHow);

    Object getSinglePropertyU(String property, String[] propName, String[][] relations, Object[][] propValue, int number, String[] orderBy, String[] orderHow);

    List getSingleProperty(String property, String[] propName, Object[] propValue, int number, int count, String[] orderBy, String[] OrderHow);

    /**
     * @return class of bean that is used to retrieve data
     */
    Class<T> getBeanClass();

    void evict(T entity);
}
