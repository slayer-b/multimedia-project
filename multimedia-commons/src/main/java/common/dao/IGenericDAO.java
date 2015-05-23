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

package common.dao;

import org.hibernate.Filter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @param <T>  class of an entity for this dao
 * @param <ID> class of id for given entity
 * @author demchuck.dima@gmail.com
 */
public interface IGenericDAO<T, ID extends Serializable> {

    /**
     * saves given entity to the database.
     *
     * @param entity -
     *               the transient instance to save or update (to be associated
     *               with the Hibernate Session)
     */
    void makePersistent(T entity);

    /**
     * updates given properties for given entity.
     *
     * @param entity -
     *               the transient instance to save or update (to be associated
     *               with the Hibernate Session)
     * @param names  property names to be updated, other fields are unchanged
     */
    int update(T entity, String... names);

    /**
     * updates given entity in the database.
     *
     * @param entity - use if you have already loaded an instance with same identifier
     */
    void merge(T entity);

    /**
     * refresh entity state(properties) from the database.
     *
     * @param entity entity to refresh
     */
    void refresh(T entity);

    /**
     * for persistent DAO implementations if you must to make any changes not backed to database.
     *
     * @param obj an entity to detach
     */
    void detach(T obj);

    /**
     * Remove an instance from the data store.
     *
     * @param id - id of entity to be deleted
     * @return quantity of entities deleted
     */
    int deleteById(ID id);

    /**
     * deletes an instances from database with given ids.
     *
     * @param id ids of instances to delete
     * @return quantity of rows deleted
     */
    int deleteById(ID[] id);

    /**
     * deletes an instance from database by given criteria.
     *
     * @param propertyName  name of field
     * @param propertyValue value of field
     * @return quantity of rows deleted
     */
    int deleteByPropertyValue(String propertyName, Object propertyValue);

    /**
     * this method saves or updates collection of entities.
     * if property names is null, update all properties
     * else update only give entities.
     *
     * @return quantity of updated entities
     */
    int updateCollection(Collection<T> entities, String... propertyNames);

    /**
     * updated some properties of entities.
     * WARNING entities must have an id not null!!!
     *
     * @param propertyValues values of properties to be updated
     * @param idValues       id of entities to be updated
     * @param propertyNames  property names to be updated
     * @return quantity of updated objects
     */
    int updateObjectArrayShortById(String[] propertyNames, ID[] idValues, Object[]... propertyValues);

    /**
     * updates propertyNames to propertyValues in all entities with property = Values.
     *
     * @param propertyNames  names of properties to update
     * @param propertyValues new values
     * @param property       name
     * @param values         values
     * @return quantity of updated entities
     */
    int updateObjectArrayShortByProperty(String[] propertyNames, Object[] propertyValues, String property, Object[] values);

    /**
     * update properties to given values for entity with given id.
     *
     * @param propertyNames  names of properties to update
     * @param propertyValues values to be set
     * @param id             id of entity to update
     * @return updated entities count (must be 1)
     */
    int updatePropertiesById(String[] propertyNames, Object[] propertyValues, ID id);

    /**
     * update properties to given values for entities with given ids.
     *
     * @param propertyNames  names of properties to update
     * @param propertyValues values to be set
     * @param id             id of entity to update
     * @return updated entities count (must be 1)
     */
    int updatePropertiesById(String[] propertyNames, Object[] propertyValues, ID[] id);

    /**
     * update given property to given value for entity with given id.
     *
     * @param propertyName name of property to update
     * @param value        an amount by which to increase property
     * @param id           id of entity to update
     * @return updated entities count (must be 1)
     */
    int updatePropertyById(String propertyName, Object value, ID id);

    /**
     * for example (p = p + 1).
     *
     * @param propertyName name of property to update
     * @param id           id of entity to update
     * @return updated entities count (must be 1)
     */
    int updatePropertyById(String propertyName, ID id);

    /**
     * returns an entity managed by this dao, with given id.
     *
     * @param id in of entity to get
     * @return the persistent instance of the entity T with the given
     *         identifier, or null if there is no such persistent instance.
     */
    T getById(ID id);

    /**
     * get list of entities by given property and value.
     *
     * @param propertyName  property name to be queried
     * @param propertyValue value by which records will be filtered
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertyValue(String propertyName, Object propertyValue);

    /**
     * get a list of entities with given property values.
     *
     * @param properties    names of properties to be selected
     * @param propertyName  property name to be queried
     * @param propertyValue value by which records will be filtered
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertyValues(String[] properties, String propertyName, Object[] propertyValue);

    /**
     * get a list of entities with given property values.
     */
    List<T> getByPropertyValues(String[] properties, String propertyName, List<Object> propertyValues);

    /**
     * get list of entities ordered.
     *
     * @param properties properties of a bean that will be selected
     * @param orderBy    an array of fields for order clause
     * @param orderHow   type of sorting ASC, DESC
     * @return a distinct list of instances
     */
    List<T> getAllShortOrdered(String[] properties, String[] orderBy, String[] orderHow);

    /**
     * get list of entities.
     *
     * @param firstResult first result to retrieve from db
     * @param resultCount result count
     */
    List<T> getPortion(int firstResult, int resultCount);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertyWhere   property for criteria by which records will be filtered
     * @param propertyValue   property value by which records will be filtered
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertyValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                             String propertyWhere, Object propertyValue,
                                             int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertyWhere   properties for criteria by which records will be filtered
     * @param propertyValue   property values by which records will be filtered
     * @param relation        relations between fields (<,>,=) if null then set to =
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertyValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                             String propertyWhere, Object propertyValue, String relation,
                                             int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertiesWhere properties for criteria by which records will be filtered
     * @param propertyValues  property values by which records will be filtered
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                               String[] propertiesWhere, Object[] propertyValues,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertiesWhere properties for criteria by which records will be filtered
     * @param propertyValues  property values by which records will be filtered
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertiesValuePortionOrdered(List<String> propertyNames, List<String> propertyAliases,
                                               List<String> propertiesWhere, List<Object> propertyValues,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertiesWhere properties for criteria by which records will be filtered
     * @param propertyValues  property values by which records will be filtered
     * @param relations       relations between fields (<,>,=) if null then set to =
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                               String[] propertiesWhere, Object[] propertyValues, String[] relations,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertiesWhere properties for criteria by which records will be filtered
     * @param propertyValues  property values by which records will be filtered
     * @param relations       relations between fields (<,>,=, LIKE) if null then set to =
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                               String[] propertiesWhere, String[][] relations, Object[][] propertyValues,
                                               int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get list of entities by given criteria.
     *
     * @param propertyNames   property names to retrieve
     * @param propertyAliases aliases of bean
     * @param propertiesWhere properties for criteria by which records will be filtered
     * @param propertyValues  property values by which records will be filtered a sub array defines values delimited by or condition
     * @param firstResult     first result to retrieve from db
     * @param resultCount     result count
     * @param orderBy         an array of fields for order clause
     * @param orderHow        type of sorting ASC, DESC
     * @return all entities that have the same value in given field
     */
    List<T> getByPropertiesValuesPortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                String[] propertiesWhere, Object[][] propertyValues,
                                                int firstResult, int resultCount, String[] orderBy, String[] orderHow);

    /**
     * get quantity of rows by given criteria.
     *
     * @param propertyName  name of property
     * @param propertyValue value of property
     * @return row count of T by property value
     */
    Long getRowCount(String propertyName, Object propertyValue);

    /**
     * get quantity of rows by given criteria.
     *
     * @param propertyName  names of property
     * @param propertyValue values of properties
     * @return row count of T by property value
     */
    Long getRowCount(String[] propertyName, Object propertyValue[]);

    /**
     * get quantity of rows by given criteria.
     *
     * @param propertyName  names of property
     * @param propertyValue values of properties
     * @return row count of T by property value
     */
    Long getRowCount(List<String> propertyName, List<Object> propertyValue);

    /**
     * get quantity of rows by given criteria.
     *
     * @param propertyName   name of a property
     * @param propertyValues value of property.
     * @return row count of T by property value
     */
    Long getRowCount(String propertyName, List<Object> propertyValues);

    /**
     * get quantity of rows by given criteria.
     *
     * @param propertyName  names of property
     * @param propertyValue values of properties
     * @return row count of T by property value
     */
    Long getRowCount(String[] propertyName, Object propertyValue[][]);

    /**
     * select a property (aggregative functions must be used to select a single result)
     * ex: max(sort)...
     *
     * @param property name of property (may contain aggregative functions)
     * @return single result
     */
    Object getSinglePropertyU(String property);

    /**
     * select a property (aggregative functions must be used to select a single result)
     * get a single property that matches criteria;
     * ex: max(sort)...
     *
     * @param property  name of property (may contain aggregative functions)
     * @param propName  where property name
     * @param propValue where property value
     * @return single result
     */
    Object getSinglePropertyU(String property, String propName, Object propValue);

    /**
     * select a property (aggregative functions must be used to select a single result)
     * get a single property that matches criteria;
     * ex: max(sort)...
     *
     * @param property  name of property (may contain aggregative functions)
     * @param propName  where property name
     * @param propValue where property value
     * @param number    number of selected item(if more then one items returned)
     * @return single result
     */
    Object getSinglePropertyU(String property, String propName, Object propValue, int number);

    /**
     * select a property (aggregative functions must be used to select a single result)
     * get a single property that matches criteria;
     * ex: max(sort)...
     *
     * @param property  name of property (may contain aggregative functions)
     * @param propName  where property name
     * @param propValue where property value
     * @param number    number of selected item(if more then one items returned)
     * @return single result
     */
    Object getSinglePropertyU(String property, String propName, Object propValue, int number, String[] orderBy, String[] orderHow);

    /**
     * select a property (aggregative functions must be used to select a single result)
     * get a single property that matches criteria;
     * ex: max(sort)...
     *
     * @param property  name of property (may contain aggregative functions)
     * @param propName  where properties names
     * @param propValue where properties values
     * @return single result
     */
    Object getSinglePropertyU(String property, String[] propName, Object[] propValue, int number, String[] orderBy, String[] orderHow);

    /**
     * select a property (aggregative functions must be used to select a single result)
     * get a single property that matches criteria;
     * ex: max(sort)...
     *
     * @param property  name of property (may contain aggregative functions)
     * @param propName  where properties names
     * @param relations relation between name and value
     * @param propValue where properties values
     * @return single result
     */
    Object getSinglePropertyU(String property, String[] propName, String[][] relations, Object[][] propValue, int number, String[] orderBy, String[] orderHow);

    /**
     * get list of single properties.
     *
     * @param property   property name to get
     * @param whereName  where criteria prop name
     * @param whereValue where criteria prop value
     * @param first      first result
     * @param max        max results
     * @return list of values
     */
    List getSingleProperty(String property, String[] whereName, Object[] whereValue, int first, int max, String[] orderBy, String[] orderHow);

    /**
     * get list of single properties.
     *
     * @param property   property name to get
     * @param whereName  where criteria prop name
     * @param whereValue where criteria prop value
     * @return list of values
     */
    List getSingleProperty(String property, String whereName, Object whereValue);

    /**
     * get list of single properties in random order.
     *
     * @param property   property name to get
     * @param whereName  where criteria prop name
     * @param whereValue where criteria prop value
     * @param first      first result number
     * @param max        total count
     * @return list of values
     */
    List getSinglePropertyOrderRand(String property, String whereName, Object whereValue, int first, int max);

    /**
     * makes flush in ORM frameworks to make some actions have.
     * effect within the session.
     */
    void flush();

    /**
     * get a number of row in a query with given order by clause
     * !!! first select the row from database(by id for example).
     * <p/>
     * @param values        value of property for current entity
     * @param orderBy       an array of fields for order clause
     * @param orderHow      type of sorting ASC, DESC
     * @param propertyName  name of property for where condition
     * @param propertyValue value of property for where condition
     * @return single result
     */
    Object getRowNumber(Object[] values, String[] orderBy, String[] orderHow, String[] propertyName, Object[] propertyValue);

    Filter enableFilter(String name);

    /**
     * disables a filter defined in xml.
     *
     * @param name filter name
     */
    void disableFilter(String name);

    /**
     * class managed by this dao.
     *
     * @return entity class
     */
    Class<T> getPersistentClass();

    void evict(T entity);
}
