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

import common.hibernate.HQLPartGenerator;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @param <T>
 * @author demchuck.dima@gmail.com
 */
public class GenericDAOHibernate<T, ID extends Serializable> implements IGenericDAO<T, ID> {
    private final Logger logger = LoggerFactory.getLogger(GenericDAOHibernate.class);

    @PersistenceContext
    private EntityManager entityManager;

    protected final Class<T> persistentClass;
    protected final String entityName;
    private static final String entityAlias = "this";

    public GenericDAOHibernate(java.lang.String entityName, java.lang.Class<T> persistentClass) {
        this.entityName = entityName;
        this.persistentClass = persistentClass;
    }

    public GenericDAOHibernate(Class<T> persistentClass) {
        this(persistentClass.getName(), persistentClass);
    }

    @Override
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public Session getCurrentSession() {
        logger.trace("session = {}", Integer.toHexString(entityManager.getDelegate().hashCode()));
        return (Session) entityManager.getDelegate();
    }

    /**
     * appends an entity name with its alias to the sb
     *
     * @param sb string builder where to append
     * @return sb
     */
    StringBuilder appendEntityNameWithAlias(StringBuilder sb) {
        return sb.append(entityName).append(" ").append(entityAlias);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int deleteById(ID id) {
        StringBuilder hql = new StringBuilder("delete ");
        hql.append(entityName);
        hql.append(" where id = :id");
        try {
            return getCurrentSession().createQuery(hql.toString()).setParameter("id", id).executeUpdate();
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            return -1;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertyValue(String propertyName, Object propertyValue) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        HQLPartGenerator.getWhereColumnValue(propertyName, propertyValue, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());
        if (propertyName != null && propertyValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propertyValue);
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertyValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                    String propertyWhere, Object propertyValue,
                                                    int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        appendEntityNameWithAlias(baseHQL);
        HQLPartGenerator.getWhereColumnValue(propertyWhere, propertyValue, baseHQL);
        HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyWhere != null && propertyValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propertyValue);
        }
        if (propertyNames != null && propertyAliases != null && propertyNames.length > 0 && propertyNames.length == propertyAliases.length) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertyValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                    String propertyWhere, Object propertyValue, String relation,
                                                    int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        HQLPartGenerator.getWhereColumnValueRelation(propertyWhere, propertyValue, relation, baseHQL);
        HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyWhere != null && propertyValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propertyValue);
        }
        if (propertyNames != null && propertyAliases != null && propertyNames.length > 0 && propertyNames.length == propertyAliases.length) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                      String[] propertiesWhere, Object[] propertyValues,
                                                      int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        appendEntityNameWithAlias(baseHQL);
        common.hibernate.HQLPartGenerator.getWhereManyColumns(propertiesWhere, propertyValues, baseHQL);
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (propertiesWhere != null && propertyValues != null) {
            for (int i = 0; i < propertiesWhere.length; i++) {
                if (propertyValues[i] != null) {
                    q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX + i, propertyValues[i]);
                }
            }
        }
        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyNames != null && propertyAliases != null && propertyAliases.length == propertyNames.length) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertiesValuePortionOrdered(List<String> propertyNames, List<String> propertyAliases,
                                                      List<String> propertiesWhere, List<Object> propertyValues,
                                                      int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        appendEntityNameWithAlias(baseHQL);
        common.hibernate.HQLPartGenerator.getWhereManyColumns(propertiesWhere, propertyValues, baseHQL);
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (propertiesWhere != null && propertyValues != null) {
            for (int i = 0; i < propertiesWhere.size(); i++) {
                if (propertyValues.get(i) != null) {
                    q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX + i, propertyValues.get(i));
                }
            }
        }
        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyNames != null && propertyAliases != null && propertyAliases.size() == propertyNames.size()) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                      String[] propertiesWhere, Object[] propertyValues, String[] relations,
                                                      int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        common.hibernate.HQLPartGenerator.getWhereManyColumnsRelations(propertiesWhere, propertyValues, relations, baseHQL);
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (propertiesWhere != null && propertyValues != null) {
            for (int i = 0; i < propertiesWhere.length; i++) {
                if (propertyValues[i] != null) {
                    q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX + i, propertyValues[i]);
                }
            }
        }
        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyNames != null && propertyAliases != null && propertyAliases.length == propertyNames.length) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getPortion(int firstResult, int resultCount) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }

        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAllShortOrdered(String[] properties, String[] orderBy, String[] orderHow) {
        //selecting using queries with aliases and result transform
        StringBuilder sb = new StringBuilder();
        HQLPartGenerator.getValuesListWithAliases(properties, properties, sb);
        sb.append(" from ");
        sb.append(entityName);
        HQLPartGenerator.getOrderBy(orderBy, orderHow, sb);

        Query q = getCurrentSession().createQuery(sb.toString());
        if (properties != null) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }

        return q.list();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateCollection(Collection<T> entities, String... propertyNames) {
        if (entities == null || entities.isEmpty()) {
            return 0;
        }
        int rez = 0;
        Session session = getCurrentSession();
        if (propertyNames == null || propertyNames.length == 0) {
            //Iterator<T> i;
            for (T item : entities) {
                session.saveOrUpdate(entityName, item);
                rez++;
            }
        } else {
            StringBuilder hql = new StringBuilder("update ");
            hql.append(entityName);
            HQLPartGenerator.getValuesListForUpdateProperties(propertyNames, hql);
            hql.append(" where id = :id");
            for (T item : entities) {
                //TODO: add id to method parameters and mb test item on null somewhere else
                if (item != null) {
                    session.createQuery(hql.toString()).setProperties(item).executeUpdate();
                }
                rez++;
            }
        }
        return rez;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updatePropertyById(String propertyName, ID id) {
        StringBuilder hql = new StringBuilder("update ");
        hql.append(entityName);
        hql.append(" set ").append(propertyName);
        hql.append(" where id=?");
        return getCurrentSession().createQuery(hql.toString()).setParameter(0, id).executeUpdate();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateObjectArrayShortById(String[] propertyNames, ID[] idValues, Object[]... propertyValues) {
        if (propertyNames == null || idValues == null || propertyValues == null
                || propertyNames.length == 0 || idValues.length == 0 || propertyValues.length != propertyNames.length) {
            return -1;
        }

        int rez = 0;
        //actually creating hql
        StringBuilder hql = new StringBuilder("update ");
        hql.append(entityName);
        HQLPartGenerator.getValuesListForUpdate(propertyNames, hql);
        hql.append(" where id = :id");
        Session session = getCurrentSession();
        for (int i = 0; i < idValues.length; i++) {
            if (idValues[i] != null) {
                Query q = session.createQuery(hql.toString())
                        .setParameter("id", idValues[i]);
                for (int j = 0; j < propertyNames.length; j++) {
                    q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX + j, propertyValues[j][i]);
                }
                rez += q.executeUpdate();
            }
        }

        return rez;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateObjectArrayShortByProperty(String[] propertyNames, Object[] propertyValues, String property, Object[] values) {
        if (propertyNames == null || values == null || propertyValues == null
                || propertyNames.length == 0 || values.length == 0 || propertyValues.length != propertyNames.length) {
            return -1;
        }

        int rez = 0;
        //actually creating hql
        StringBuilder hql = new StringBuilder("update ");
        hql.append(entityName);
        HQLPartGenerator.getValuesListForUpdate(propertyNames, hql);
        hql.append(" where ");
        hql.append(property);
        hql.append(" in (:property)");

        Query q = getCurrentSession().createQuery(hql.toString()).setParameterList("property", values);
        for (int i = 0; i < propertyNames.length; i++) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX + i, propertyValues[i]);
        }

        rez += q.executeUpdate();

        return rez;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updatePropertiesById(String[] propertyNames, Object[] propertyValues, ID id) {
        if (propertyNames == null || id == null || propertyValues == null
                || propertyNames.length == 0 || propertyValues.length != propertyNames.length) {
            return -1;
            //throw new NullPointerException("updateObjectArrayShortById: One of arguments is null of has 0 length or propertyNames length not eq to propertyValues length");
        }

        //actually creating hql----------------------------------------
        StringBuilder hql = new StringBuilder("update ");
        hql.append(entityName);
        HQLPartGenerator.getValuesListForUpdateNumbers(propertyNames, hql);
        hql.append(" where id = :id");
        //-------------------------------------------------------------
        Query q = getCurrentSession().createQuery(hql.toString()).setParameter("id", id);
        //appending values
        for (int j = 0; j < propertyNames.length; j++) {
            q = q.setParameter(j, propertyValues[j]);
        }
        return q.executeUpdate();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updatePropertiesById(String[] propertyNames, Object[] propertyValues, ID[] id) {
        if (propertyNames == null || id == null || propertyValues == null
                || propertyNames.length == 0 || propertyValues.length != propertyNames.length) {
            return -1;
            //throw new NullPointerException("updateObjectArrayShortById: One of arguments is null of has 0 length or propertyNames length not eq to propertyValues length");
        }

        //actually creating hql----------------------------------------
        StringBuilder hql = new StringBuilder("update ");
        hql.append(entityName);
        HQLPartGenerator.getValuesListForUpdateNumbers(propertyNames, hql);
        hql.append(" where id in (:id)");
        //-------------------------------------------------------------
        Query q = getCurrentSession().createQuery(hql.toString()).setParameterList("id", id);
        //appending values
        for (int j = 0; j < propertyNames.length; j++) {
            q = q.setParameter(j, propertyValues[j]);
        }
        return q.executeUpdate();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updatePropertyById(String propertyName, Object value, ID id) {
        if (propertyName == null || id == null || value == null) {
            return -1;
            //throw new NullPointerException("updateObjectArrayShortById: One of arguments is null of has 0 length or propertyNames length not eq to propertyValues length");
        }

        //actually creating hql----------------------------------------
        StringBuilder hql = new StringBuilder("update ");
        hql.append(entityName);
        hql.append(" set ");
        hql.append(propertyName);
        hql.append(" = ");
        hql.append(propertyName);
        hql.append(" + :prop where id = :id");
        //-------------------------------------------------------------
        Query q = getCurrentSession().createQuery(hql.toString())
                .setParameter("id", id)
                .setParameter("prop", value);
        return q.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertiesValuesPortionOrdered(String[] propertyNames, String[] propertyAliases,
                                                       String[] propertiesWhere, Object[][] propertyValues, int firstResult, int resultCount,
                                                       String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        common.hibernate.HQLPartGenerator.getWhereManyColumnsManyValues(propertiesWhere, propertyValues, baseHQL);
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (propertiesWhere != null) {
            q = HQLPartGenerator.appendWherePropertiesValues(propertyValues, q);
        }
        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyNames != null && propertyAliases != null && propertyAliases.length == propertyNames.length) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRowCount(String propertyName, Object propertyValue) {
        StringBuilder hql = new StringBuilder("select count(*) from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereColumnValue(propertyName, propertyValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        if (propertyName != null && propertyValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propertyValue);
        }
        return (Long) q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRowCount(String propertyName, List<Object> propertyValue) {
        StringBuilder hql = new StringBuilder("select count(*) from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereColumnValues(propertyName, propertyValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        q = HQLPartGenerator.appendWherePropertiesValue(propertyValue, q);

        return (Long) q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRowCount(String[] propertyName, Object[] propertyValue) {
        StringBuilder hql = new StringBuilder("select count(*) from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereManyColumns(propertyName, propertyValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        q = HQLPartGenerator.appendPropertiesValue(propertyValue, q);

        return (Long) q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRowCount(List<String> propertyName, List<Object> propertyValue) {
        StringBuilder hql = new StringBuilder("select count(*) from ");
        hql.append(entityName).append(" this");
        HQLPartGenerator.getWhereManyColumns(propertyName, propertyValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        q = HQLPartGenerator.appendPropertiesValue(propertyValue, q);

        return (Long) q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRowCount(String[] propertyName, Object[][] propertyValue) {
        StringBuilder hql = new StringBuilder("select count(*) from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereManyColumnsManyValues(propertyName, propertyValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        q = HQLPartGenerator.appendWherePropertiesValues(propertyValue, q);

        return (Long) q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSinglePropertyU(String property, String propName, Object propValue) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereColumnValue(propName, propValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        if (propName != null && propValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propValue);
        }

        return q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSinglePropertyU(String property, String propName, Object propValue, int number) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereColumnValue(propName, propValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString())
                .setFirstResult(number)
                .setMaxResults(1);
        if (propName != null && propValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propValue);
        }

        return q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSinglePropertyU(String property, String propName, Object propValue, int number, String[] orderBy, String[] orderHow) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereColumnValue(propName, propValue, hql);
        HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);

        Query q = getCurrentSession().createQuery(hql.toString())
                .setFirstResult(number)
                .setMaxResults(1);
        if (propName != null && propValue != null) {
            q = q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propValue);
        }

        return q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSinglePropertyU(String property, String[] propName, Object[] propValue, int number, String[] orderBy, String[] orderHow) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereManyColumns(propName, propValue, hql);
        HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);

        Query q = getCurrentSession().createQuery(hql.toString())
                .setFirstResult(number)
                .setMaxResults(1);
        q = HQLPartGenerator.appendPropertiesValue(propValue, q);

        return q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSinglePropertyU(String property) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        return getCurrentSession().createQuery(hql.toString())
                .uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List getSinglePropertyOrderRand(String property, String whereName, Object whereValue, int first, int max) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        if (whereName != null) {
            hql.append(" where ");
            hql.append(whereName);
            if (whereValue == null) {
                hql.append(" is null ");
            } else {
                hql.append(" = :prop ");
            }
        }
        hql.append(" order by rand()");
        Query q = getCurrentSession().createQuery(hql.toString());
        if (max > 0) {
            q = q.setMaxResults(max);
        }
        if (first > 0) {
            q = q.setFirstResult(first);
        }
        if (whereName != null && whereValue != null) {
            q = q.setParameter("prop", whereValue);
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List getSingleProperty(String property, String[] whereName, Object[] whereValue, int first, int max, String[] orderBy, String[] orderHow) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        HQLPartGenerator.getWhereManyColumns(whereName, whereValue, hql);
        HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        if (max > 0) {
            q = q.setMaxResults(max);
        }
        if (first > 0) {
            q = q.setFirstResult(first);
        }
        q = HQLPartGenerator.appendPropertiesValue(whereValue, q);
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List getSingleProperty(String property, String whereName, Object whereValue) {
        StringBuilder hql = new StringBuilder("select ");
        hql.append(property);
        hql.append(" from ");
        hql.append(entityName);
        hql.append(" where ");
        hql.append(whereName);
        if (whereValue == null) {
            hql.append(" is null");
        } else {
            hql.append(" = :prop");
        }

        Query q = getCurrentSession().createQuery(hql.toString());
        if (whereValue != null) {
            q = q.setParameter("prop", whereValue);
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public void detach(T obj) {
        getCurrentSession().evict(obj);
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(ID id) {
        return (T) getCurrentSession().get(entityName, id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void makePersistent(T entity) {
        getCurrentSession().saveOrUpdate(entityName, entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int update(T entity, String... names) {
        if (names == null || names.length == 0) {
            getCurrentSession().saveOrUpdate(entityName, entity);
            return 1;
        } else {
            StringBuilder hql = new StringBuilder("update ");
            hql.append(entityName);
            HQLPartGenerator.getValuesListForUpdateProperties(names, hql);
            hql.append(" where id = :id");
            return getCurrentSession().createQuery(hql.toString()).setProperties(entity).executeUpdate();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void merge(T entity) {
        getCurrentSession().merge(entityName, entity);
    }

    @Override
    @Transactional(readOnly = true)
    public void refresh(T entity) {
        getCurrentSession().refresh(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int deleteByPropertyValue(String propertyName, Object propertyValue) {
        if (propertyName == null) {
            return -1;
        }
        StringBuilder hql = new StringBuilder("delete ");
        hql.append(entityName);
        HQLPartGenerator.getWhereColumnValue(propertyName, propertyValue, hql);

        Query q = getCurrentSession().createQuery(hql.toString());
        return q.setParameter(HQLPartGenerator.PROP_NAME_PREFIX, propertyValue).executeUpdate();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int deleteById(ID[] id) {
        StringBuilder hql = new StringBuilder("delete ");
        hql.append(entityName);
        hql.append(" where id in (:idList)");

        Query q = getCurrentSession().createQuery(hql.toString())
                .setParameterList("idList", id);
        return q.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertyValues(String[] properties, String propertyName, Object[] propertyValues) {
        StringBuilder hql = new StringBuilder();
        HQLPartGenerator.getValuesListWithAliases(properties, properties, hql);
        hql.append(" from ");
        hql.append(entityName);
        hql.append(" where ");
        hql.append(propertyName);
        hql.append(" in (:idList)");

        Query q = getCurrentSession().createQuery(hql.toString())
                .setParameterList("idList", propertyValues);
        if (properties != null) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertyValues(String[] properties, String propertyName, List<Object> propertyValues) {
        if (propertyName == null || propertyValues == null || propertyValues.isEmpty()) {
            return null;
        }

        StringBuilder hql = new StringBuilder();
        HQLPartGenerator.getValuesListWithAliases(properties, properties, hql);
        hql.append(" from ");
        hql.append(entityName);
        hql.append(" where ");
        hql.append(propertyName);
        hql.append(" in (:idList)");

        Query q = getCurrentSession().createQuery(hql.toString())
                .setParameterList("idList", propertyValues);
        if (properties != null) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getRowNumber(Object[] values, String[] orderBy, String[] orderHow,
                               String[] propertyName, Object[] propertyValue) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        baseHQL.append("select count(*)+1 from ");
        baseHQL.append(this.entityName);
        if (orderBy != null && values != null && orderHow != null) {
            if (propertyName != null && propertyValue != null) {
                HQLPartGenerator.getWhereManyColumns(propertyName, propertyValue, baseHQL);
                baseHQL.append(" and (");
            } else {
                baseHQL.append(" where (");
            }
            for (int i = 0; i < orderBy.length; i++) {
                for (int j = 0; j < i; j++) {
                    baseHQL.append(orderBy[j]);
                    if (values[j] == null) {
                        baseHQL.append(" is null");
                    } else {
                        baseHQL.append(" = :p");
                        baseHQL.append(j);
                    }
                }
                baseHQL.append(orderBy[i]);
                if ("desc".equalsIgnoreCase(orderHow[i])) {
                    if (values[i] == null) {
                        baseHQL.append(" > null");
                    } else {
                        baseHQL.append(" > :p");
                        baseHQL.append(i);
                    }
                } else {
                    if (values[i] == null) {
                        baseHQL.append(" < null");
                    } else {
                        baseHQL.append(" < :p");
                        baseHQL.append(i);
                    }
                }
            }
            baseHQL.append(")");
        }
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        if (values != null && orderBy != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    q = q.setParameter("p" + i, values[i]);
                }
            }
        }
        q = HQLPartGenerator.appendPropertiesValue(propertyValue, q);

        return q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getByPropertiesValuePortionOrdered(String[] propertyNames, String[] propertyAliases, String[] propertiesWhere, String[][] relations, Object[][] propertyValues, int firstResult, int resultCount, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        common.hibernate.HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        common.hibernate.HQLPartGenerator.getWhereManyColumnsManyValuesRelations(propertiesWhere, relations, propertyValues, baseHQL);
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        common.hibernate.HQLPartGenerator.appendWhereManyColumnsManyValuesRelations(propertyValues, q);
        if (firstResult > 0) {
            q = q.setFirstResult(firstResult);
        }
        if (resultCount > 0) {
            q = q.setMaxResults(resultCount);
        }
        if (propertyNames != null && propertyAliases != null && propertyAliases.length == propertyNames.length) {
            q = q.setResultTransformer(new AliasToBeanResultTransformer(persistentClass));
        }
        return q.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSinglePropertyU(String property, String[] propName, String[][] relations, Object[][] propValue, int number, String[] orderBy, String[] orderHow) {
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        baseHQL.append("select ");
        baseHQL.append(property);
        baseHQL.append(" from ");
        baseHQL.append(this.entityName);
        common.hibernate.HQLPartGenerator.getWhereManyColumnsManyValuesRelations(propName, relations, propValue, baseHQL);
        common.hibernate.HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        //----------------------------------------------------------------------
        Query q = getCurrentSession().createQuery(baseHQL.toString());

        common.hibernate.HQLPartGenerator.appendWhereManyColumnsManyValuesRelations(propValue, q);
        q.setFirstResult(number);
        q.setMaxResults(1);
        return q.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public void enableFilter(String name, String[] paramNames, Object[] paramValues) {
        Filter f = getCurrentSession().enableFilter(name);
        if (paramNames == null || paramValues == null) {
            return;
        }
        for (int i = 0; i < paramNames.length; i++) {
            f = f.setParameter(paramNames[i], paramValues[i]);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void disableFilter(String name) {
        getCurrentSession().disableFilter(name);
    }

    @Override
    public void evict(T entity) {
        getCurrentSession().evict(entity);
    }
}
