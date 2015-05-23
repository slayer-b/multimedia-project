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

package com.multimedia.service.impl;

import com.multimedia.dao.GalleryPagesDAOHibernate;
import com.multimedia.model.beans.PagesRubrication;
import com.multimedia.service.IPagesService;
import common.services.generic.GenericServiceImpl;
import core.dao.IPagesDAO;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.submodules.ASubmodule;
import gallery.web.controller.pages.types.WallpaperGalleryType;
import org.hibernate.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author demchuck.dima@gmail.com
 */
@Service
public class PagesServiceImpl extends GenericServiceImpl<Pages, Long> implements IPagesService {
    private static final String[] ORDER_BY = {"type", "sort", "name"};
    private static final String[] ORDER_HOW = {"ASC", "ASC", "ASC"};

    private final IPagesDAO<Pages, Long> pagesDAO;

    @Autowired
    public PagesServiceImpl(GalleryPagesDAOHibernate pagesDAO) {
        super(pagesDAO);
        this.pagesDAO = pagesDAO;
    }

    @Override
    public void save(Pages entity) {
        //setting new parent as false
        if (entity.getId_pages() != null) {
            pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.FALSE}, entity.getId_pages());
        }
        super.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        //and setting parent last to false if any
        Long id_pages = (Long) pagesDAO.getSinglePropertyU("id_pages", "id", id);
        if (id_pages != null) {
            Long count = (Long) pagesDAO.getSinglePropertyU("count(*)", "id_pages", id_pages);
            if (count < 2L) {
                pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.TRUE}, id_pages);
            }
        }
        return super.deleteById(id);
    }

    @Override
    public int deleteById(Long[] id) {
        throw new UnsupportedOperationException("unimplemented yet");
    }

    private static final String[] ALL_SHORT_CMS = {"id", "name", "sort", "active", "last", "type"};

    @Override
    public List<Pages> getAllShortCms() {
        return dao.getAllShortOrdered(ALL_SHORT_CMS, ORDER_BY, ORDER_HOW);
    }

    @Override
    public List<Pages> getShortOrderedByPropertyValueCms(String propertyName, Object propertyValue) {
        return pagesDAO.getByPropertyValuePortionOrdered(ALL_SHORT_CMS, ALL_SHORT_CMS, propertyName, propertyValue, 0, -1, ORDER_BY, ORDER_HOW);
    }

    @Override
    public List<Pages> getPagesForRelocate(Long id) {
        return pagesDAO.getPagesForRelocateOrdered(id, ORDER_BY, ORDER_HOW);
    }

    private static final String[] RELOCATE_PSEUDONYMS = {"id_pages"};
    private static final String[] LAST_PSEUDONYM = {"last"};

    @Override
    public boolean relocatePages(Long id, Long newIdPages) {
        if (pagesDAO.checkRecursion(id, newIdPages)) {
            //and setting parent last to false if any
            Long id_pages = (Long) pagesDAO.getSinglePropertyU("id_pages", "id", id);
            if (id_pages != null) {
                Long count = (Long) pagesDAO.getSinglePropertyU("count(*)", "id_pages", id_pages);
                if (count < 2L) {
                    pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.TRUE}, id_pages);
                }
            }

            pagesDAO.updatePropertiesById(RELOCATE_PSEUDONYMS, new Object[]{newIdPages}, id);
            //setting new parent as false
            if (newIdPages != null) {
                pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.FALSE}, newIdPages);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Pages> getAllPagesParents(Long id, String[] property_names) {
        if (id == null) {
            return null;
        }
        List<Pages> temp = pagesDAO.getAllParentsRecursive(id, property_names, property_names);
        if (temp == null) {
            return null;
        }
        List<Pages> rez = new ArrayList<Pages>(temp.size() + 1);
        //reversing order
        for (int i = temp.size(); i > 0; i--) {
            rez.add(temp.get(i - 1));
        }
        return rez;
    }

    private static final String[] CATEGORIES_SELECT_PSEUDONYMES = {"id", "name", "last"};
    private static final String[] CATEGORIES_WHERE = {"id_pages", "type", "active"};

    @Override
    public List<Pages> getCategories(Long id, String type) {
        if (id != null) {
            Object[] values = {id, type, Boolean.TRUE};
            return dao.getByPropertiesValuePortionOrdered(CATEGORIES_SELECT_PSEUDONYMES, CATEGORIES_SELECT_PSEUDONYMES,
                    CATEGORIES_WHERE, values, 0, -1, ORDER_BY, ORDER_HOW);
        } else {
            return null;
        }
    }

    /**
     * constant for properties to be selected from DB for select html element for choosing a category
     */
    private static final String[] COMBOBOX_PROPERTIES = {"id", "name"};
    private static final String[] COMBOBOX_ORDER_BY = {"name"};
    private static final String[] COMBOBOX_ORDER_HOW = {"ASC"};

    /**
     * returns only id and name properties
     * result is ordered by name
     */
    @Override
    public List<Pages> getAllCombobox(Boolean active, Boolean last, String type) {
        int len = 0;
        String[] names = new String[3];
        Object[] items = new Object[3];
        if (active != null) {
            names[len] = "active";
            items[len] = active;
            len++;
        }
        if (last != null) {
            names[len] = "last";
            items[len] = last;
            len++;
        }
        if (type != null) {
            names[len] = "type";
            items[len] = type;
            len++;
        }
        if (len == 0) {
            return dao.getAllShortOrdered(COMBOBOX_PROPERTIES, COMBOBOX_ORDER_BY, COMBOBOX_ORDER_HOW);
        } else if (len == 1) {
            return dao.getByPropertyValuePortionOrdered(COMBOBOX_PROPERTIES, COMBOBOX_PROPERTIES,
                    names[0], items[0], 0, 0, COMBOBOX_ORDER_BY, COMBOBOX_ORDER_HOW);
        } else {
            names = Arrays.copyOf(names, len);
            items = Arrays.copyOf(items, len);
            return dao.getByPropertiesValuePortionOrdered(COMBOBOX_PROPERTIES, COMBOBOX_PROPERTIES,
                    names, items, 0, 0, COMBOBOX_ORDER_BY, COMBOBOX_ORDER_HOW);
        }
    }

    @Override
    public List<Long> getAllActiveChildrenId(Long id) {
        return pagesDAO.getAllActiveChildrenId(id, null, null);
    }

    private static final String[] SUBMODULES_WHERE = {"active", "id_pages", "type"};
    private static final String[] SUBMODULES_PSEUDONYMES = {"id", "name", "type"};

    @Override
    public List<Pages> activateSubmodules(List<Pages> navigation, Map<String, ASubmodule> submodules) {
        List<Pages> rez = new ArrayList<Pages>(submodules.size());
        Set<String> submodulesTypes = new HashSet<String>(submodules.keySet());

        List<Pages> temp;

        int i = navigation.size();

        Object[][] values = new Object[SUBMODULES_WHERE.length][];
        values[0] = new Object[]{Boolean.TRUE};
        while (!submodulesTypes.isEmpty() && i > 0) {
            i--;
            values[1] = new Object[]{navigation.get(i).getId()};
            values[2] = submodulesTypes.toArray();
            //selecting all submodules from current page (i)
            temp = dao.getByPropertiesValuesPortionOrdered(SUBMODULES_PSEUDONYMES, SUBMODULES_PSEUDONYMES,
                    SUBMODULES_WHERE, values, 0, -1, null, null);
            //deleting all submodules types that have been selected by this query
            //also set and activate its submodule
            for (Pages p : temp) {
                rez.add(p);
                ASubmodule sub = submodules.get(p.getType());
                sub.setActive(Boolean.TRUE);
                sub.setPage(p);
                submodulesTypes.remove(p.getType());
            }
        }
        //last checking root (id_pages = null)
        if (!submodulesTypes.isEmpty()) {
            values[1] = null;
            values[2] = submodulesTypes.toArray();
            //selecting all submodules from current page (i)
            temp = dao.getByPropertiesValuesPortionOrdered(SUBMODULES_PSEUDONYMES, SUBMODULES_PSEUDONYMES,
                    SUBMODULES_WHERE, values, 0, -1, null, null);
            //deleting all submodules types that have been selected by this query
            //also set and activate its submodule
            for (Pages p : temp) {
                rez.add(p);
                ASubmodule sub = submodules.get(p.getType());
                sub.setActive(Boolean.TRUE);
                sub.setPage(p);
                submodulesTypes.remove(p.getType());
            }
        }
        return rez;
    }

    @Override
    public List<Pages> getPagesChildrenRecurciveOrderedWhere(String[] properties, String[] propertyNames, Object[][] propertyValues) {
        return pagesDAO.getPagesChildrenRecurciveOrderedWhere(properties, properties, propertyNames, propertyValues, ORDER_BY, ORDER_HOW, null);
    }

    @Override
    public List<Pages> getPagesChildrenRecurciveOrderedWhere(String[] properties, String[] propPseudonyms, String[] propertyNames, Object[][] propertyValues) {
        return pagesDAO.getPagesChildrenRecurciveOrderedWhere(properties, propPseudonyms, propertyNames, propertyValues, ORDER_BY, ORDER_HOW, null);
    }

    @Override
    public List<Pages> getPagesChildrenRecurciveOrderedWhere(String[] properties, String[] propPseudonyms, String[] propertyNames, Object[][] propertyValues, Long first_id) {
        return pagesDAO.getPagesChildrenRecurciveOrderedWhere(properties, propPseudonyms, propertyNames, propertyValues, ORDER_BY, ORDER_HOW, first_id);
    }

    private static final String[] ID_PSEUDONYM = {"id"};

    @Override
    public void recalculateLast(Long id) {
        if (id == null) {
            List<Pages> ids = pagesDAO.getAllShortOrdered(ID_PSEUDONYM, null, null);
            Long count;
            for (Pages p : ids) {
                count = (Long) pagesDAO.getSinglePropertyU("count(*)", "id_pages", p.getId());
                if (count > 0L) {
                    pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.FALSE}, p.getId());
                } else {
                    pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.TRUE}, p.getId());
                }
            }
        } else {
            Long count = (Long) pagesDAO.getSinglePropertyU("count(*)", "id_pages", id);
            if (count > 0L) {
                pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.FALSE}, id);
            } else {
                pagesDAO.updatePropertiesById(LAST_PSEUDONYM, new Object[]{Boolean.TRUE}, id);
            }
        }
    }

    private static final String[] RUBRICATION_TYPES =
            {WallpaperGalleryType.TYPE};

    @Override
    public List<PagesRubrication> getPagesRubricationByIdPages(Long id_pages) {
        return pagesDAO.getPagesRubricationByIdPages(id_pages, RUBRICATION_TYPES, ORDER_BY, ORDER_HOW);
    }

    @Override
    public void enablePageIdFilter(List<Long> pagesIds) {
        Filter filter = dao.enableFilter("page_id");
        filter.setParameterList("pagesIds", pagesIds);
    }

    @Override
    public void disablePageIdFilter() {
        dao.disableFilter("page_id");
    }

}
