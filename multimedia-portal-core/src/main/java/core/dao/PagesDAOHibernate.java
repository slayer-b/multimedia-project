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

package core.dao;

import com.multimedia.core.pages.types.APagesType;
import com.multimedia.model.beans.PagesRubrication;
import common.dao.GenericDAOHibernate;
import common.hibernate.HQLPartGenerator;
import core.model.beans.Pages;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author demchuck.dima@gmail.com
 */
public class PagesDAOHibernate<T extends Pages, ID extends Serializable> extends GenericDAOHibernate<T, ID> implements IPagesDAO<T, ID> {

    public PagesDAOHibernate(Class<T> klass) {
        super(klass);
    }

    /**
     * pseudonyms of columns that will be selected by getPagesForRelocate(Long) method
     * must have at least "id" and "id_pages" columns
     */
    private static final String[] PAGES_FOR_RELOCATE_PSEUDONYMS = {"id", "id_pages", "name"};

    @Override
    @Transactional(readOnly = true)
    public List<T> getPagesForRelocateOrdered(Long id, String[] orderBy, String[] orderHow) {
        if (id == null) {
            return new ArrayList<T>();
        } else {
            //creating table for results
            List<T> rez = new ArrayList<T>();
            AliasToBeanResultTransformer pagesTransformer = new AliasToBeanResultTransformer(persistentClass);
            //forming HQL-----------------------------------------------------------
            StringBuilder baseHQL = new StringBuilder();
            HQLPartGenerator.getValuesListWithAliases(PAGES_FOR_RELOCATE_PSEUDONYMS, PAGES_FOR_RELOCATE_PSEUDONYMS, baseHQL);
            baseHQL.append(" FROM ");
            baseHQL.append(this.entityName);
            //----------------------------------------------------------------------
            List<T> temp;
            {
                //selecting base entities
                StringBuilder hql = new StringBuilder(baseHQL);
                hql.append(" WHERE id_pages is null");
                HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);

                temp = getCurrentSession().createQuery(hql.toString()).setResultTransformer(pagesTransformer).list();
            }

            //deleting instance with given id and setting layer
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getId().equals(id)) {
                    temp.remove(i);
                    i--;
                } else {
                    temp.get(i).setLayer(0L);
                }
            }

            //completing hql forming
            baseHQL.append(" WHERE id_pages = :id_pages");
            HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
            String hql = baseHQL.toString();

            while (!temp.isEmpty()) {
                //getting first element
                T currRow = temp.remove(0);

                List<T> children = getCurrentSession().createQuery(hql).setParameter("id_pages", currRow.getId()).
                        setResultTransformer(pagesTransformer).list();

                //deleting instance with given id and setting layer
                for (int i = 0; i < children.size(); i++) {
                    if (children.get(i).getId().equals(id)) {
                        children.remove(i);
                        i--;
                    } else {
                        children.get(i).setLayer(currRow.getLayer() + 1L);
                    }
                }
                temp.addAll(0, children);
                rez.add(currRow);
            }
            return rez;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getPagesChildrenRecurciveOrderedWhere(String[] properties, String[] propPseudonyms, String[] propertyNames, Object[][] propertyValues,
                                                         String[] orderBy, String[] orderHow, Long first_id) {
        //creating table for results
        List<T> rez = new ArrayList<T>();
        AliasToBeanResultTransformer pagesTransformer = new AliasToBeanResultTransformer(persistentClass);
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        HQLPartGenerator.getValuesListWithAliases(properties, propPseudonyms, baseHQL);
        baseHQL.append(" FROM ");
        baseHQL.append(this.entityName);
        HQLPartGenerator.getWhereManyColumnsManyValues(propertyNames, propertyValues, baseHQL);
        //----------------------------------------------------------------------
        List<T> temp;
        {
            //selecting base entities
            StringBuilder hql = new StringBuilder(baseHQL);
            if (propertyNames == null) {
                hql.append(" WHERE");
            } else {
                hql.append(" AND");
            }
            if (first_id == null) {
                hql.append(" id_pages is null");
            } else {
                hql.append(" id = ");
                hql.append(first_id);
            }
            HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);

            Query q = getCurrentSession().createQuery(hql.toString());
            HQLPartGenerator.appendWherePropertiesValues(propertyValues, q);

            temp = q.setResultTransformer(pagesTransformer).list();
        }

        //deleting instance with given id and setting layer
        for (T aTemp : temp) {
            aTemp.setLayer(0L);
        }

        //completing hql forming
        if (propertyNames == null) {
            baseHQL.append(" WHERE");
        } else {
            baseHQL.append(" AND");
        }
        baseHQL.append(" id_pages = :id_pages");
        HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);
        String hql = baseHQL.toString();

        while (!temp.isEmpty()) {
            //getting first element
            T currRow = temp.remove(0);

            Query q = getCurrentSession().createQuery(hql);
            HQLPartGenerator.appendWherePropertiesValues(propertyValues, q);
            List<T> children = q.setParameter("id_pages", currRow.getId()).
                    setResultTransformer(pagesTransformer).list();

            //setting layer
            for (T aChildren : children) {
                aChildren.setLayer(currRow.getLayer() + 1L);
            }
            temp.addAll(0, children);
            rez.add(currRow);
        }
        return rez;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkRecursion(Long id, Long newIdPages) {
        if (id == null) {
            return false;
        }
        if (newIdPages == null) {
            return true;
        }
        //forming hql----------------------------------------------------
        StringBuilder baseHQL = new StringBuilder("SELECT pages.id FROM ");
        baseHQL.append(this.entityName);
        baseHQL.append(" WHERE id = :id");
        //---------------------------------------------------------------

        Session sess = getCurrentSession();

        Object curId = newIdPages;
        while (curId != null) {
            if (id.equals(curId)) {
                return false;
            }
            curId = sess.createQuery(baseHQL.toString()).setParameter("id", curId).uniqueResult();
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAllParentsRecursive(Long id, String[] propertyNames, String[] propertyAliases) {
        if (id == null || propertyAliases == null || propertyNames == null || propertyNames.length != propertyAliases.length) {
            return null;
        }
        //forming hql----------------------------------------------------
        StringBuilder baseHQL = new StringBuilder();
        HQLPartGenerator.getValuesListWithAliases(propertyNames, propertyAliases, baseHQL);
        baseHQL.append(" FROM ");
        baseHQL.append(this.entityName);
        baseHQL.append(" WHERE ");
        baseHQL.append("id = :id");
        //---------------------------------------------------------------
        List<T> rez = new ArrayList<T>();
        AliasToBeanResultTransformer trans = new AliasToBeanResultTransformer(persistentClass);

        boolean completed = false;
        Long curId = id;
        //TODO: check recursion
        while (!completed) {
            T p = (T) getCurrentSession().createQuery(baseHQL.toString())
                    .setParameter("id", curId).setResultTransformer(trans).uniqueResult();
            if (p == null) {
                return rez;
            }
            rez.add(p);
            curId = p.getId_pages();
            if (curId == null || id.equals(curId)) {
                completed = true;
            }
        }
        return rez;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getAllChildrenId(Long id, String[] orderBy, String[] orderHow, Boolean active) {
        List<Long> rez = new ArrayList<Long>();
        //forming HQL-----------------------------------------------------------
        StringBuilder baseHQL = new StringBuilder("SELECT ");
        baseHQL.append("id");
        baseHQL.append(" FROM ");
        baseHQL.append(this.entityName);
        //----------------------------------------------------------------------
        Session sess = getCurrentSession();
        List<Long> temp;
        if (id == null) {
            //selecting base entities
            StringBuilder hql = new StringBuilder(baseHQL);
            hql.append(" WHERE id_pages is null");
            HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);
            temp = sess.createQuery(hql.toString()).list();
        } else {
            temp = new ArrayList<Long>(1);
            temp.add(id);
        }
        //----------------------------------------------------------------------
        //completing hql forming
        baseHQL.append(" WHERE id_pages = :id_pages");
        if (active != null) {
            baseHQL.append(" AND active = :active");
        }
        HQLPartGenerator.getOrderBy(orderBy, orderHow, baseHQL);

        Boolean activeVal = active == null ? Boolean.TRUE : active;
        while (!temp.isEmpty()) {
            //getting first element
            Long currRow = temp.remove(0);

            Query query = sess.createQuery(baseHQL.toString()).setLong("id_pages", currRow);
            if (active != null) {
                query = query.setBoolean("active", activeVal);
            }
            List<Long> children = query.list();

            temp.addAll(0, children);

            rez.add(currRow);
        }
        return rez;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagesRubrication> getPagesRubricationByIdPages(Long id_pages, String[] types, String[] orderBy, String[] orderHow) {
        StringBuilder hql = new StringBuilder("select id, name, type, last from ");
        hql.append(entityName);
        hql.append(" where active = 1 and id_pages = :id_pages");
        if (types != null) {
            hql.append(" and type in (:types)");
        }
        HQLPartGenerator.getOrderBy(orderBy, orderHow, hql);

        return getCurrentSession().createQuery(hql.toString())
                .setParameter("id_pages", id_pages)
                .setParameterList("types", types)
                .setResultTransformer(PAGES_RUBRICATION_TRANSFORMER)
                .list();
    }

    private static final ResultTransformer PAGES_RUBRICATION_TRANSFORMER = new ResultTransformer() {
        private static final long serialVersionUID = 1738697157292932925L;

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {
            PagesRubrication pr = new PagesRubrication();
            pr.setId((Long) tuple[0]);
            pr.setName((String) tuple[1]);
            pr.setModuleName(APagesType.getModuleName((String) tuple[2]));
            pr.setLast((Boolean) tuple[3]);
            return pr;
        }

        @Override
        public List transformList(List collection) {
            return collection;
        }
    };
}