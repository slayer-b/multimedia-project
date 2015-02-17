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

package com.multimedia.cms.service;

import com.multimedia.cms.config.LocaleConfig;
import com.multimedia.service.IAutoreplaceService;
import com.multimedia.service.ILocaleService;
import common.beans.IFilterBean;
import common.beans.IMultiupdateBean;
import common.cms.services.ILocalizedCmsService;
import gallery.model.beans.Autoreplace;
import gallery.model.beans.AutoreplaceL;
import gallery.model.command.MultiAutoreplaceCms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author demchuck.dima@gmail.com
 */
@Service("cmsAutoreplaceService")
public class AutoreplaceServiceCmsImpl implements ILocalizedCmsService<AutoreplaceL, Long> {
    private final Logger logger = LoggerFactory.getLogger(AutoreplaceServiceCmsImpl.class);

    @Autowired
    private IAutoreplaceService service;
    @Autowired
    private ILocaleService localeService;

    private static final String[] ORDER_BY = {"localeParent.sort"};
    private static final String[] ORDER_HOW = {"asc"};

    private static final String[] AUTOREPLACE_SELECT = {"lang", "localeParent.id"};

    @Override
    public List<AutoreplaceL> getCurrentLocalization(String locale) {
        return service.getByPropertyValueOrdered(null, "lang", locale, ORDER_BY, ORDER_HOW);
    }

    @Override
    public List<AutoreplaceL> getOtherLocalization(String locale, List<AutoreplaceL> selected) {
        //1-st selecting all possible parents.id
        Set<Object> parents = new HashSet<Object>(service.getSingleProperty("localeParent.id", null, null, 0, 0, null, null));
        //2-nd creating list for results
        List<AutoreplaceL> result = new ArrayList<AutoreplaceL>();
        //3-rd checking if all parents are selected
        if (getUnselectedParents(parents, selected)) {
            //means that we have not selected some Autoreplace Objects
            //1-st select all locales
            List<Object> locales = localeService.getSingleProperty("name", null, null, 0, 0, LocaleConfig.ORDER_BY, LocaleConfig.ORDER_HOW);
            //2-nd select for all unselected parents
            List<AutoreplaceL> tmpResult;
            for (Object o : locales) {
                if (!o.equals(locale)) {
                    logger.debug("selecting lang = {}", o);
                    tmpResult = service.getByPropertiesValuesOrdered(null,
                            AUTOREPLACE_SELECT, new Object[][]{new Object[]{o}, parents.toArray()},
                            ORDER_BY, ORDER_HOW);
                    //tmp_result =  service.getShortByPropertyValueOrdered(null, new String[]{"lang"}, o, ORDER_BY, ORDER_HOW);
                    result.addAll(tmpResult);
                    if (!getUnselectedParents(parents, tmpResult)) {
                        break;
                    }
                }
            }
        }
        return result;
        //return service.getShortByPropertyValueOrdered(null, "lang", locale, ORDER_BY, ORDER_HOW);
    }

    /**
     * @param parents id's of parents that are left to select
     * @param result  parents that are selected in current iteration
     * @return true if all parents are selected
     */
    protected boolean getUnselectedParents(Set<Object> parents, List<AutoreplaceL> result) {
        for (AutoreplaceL a : result) {
            parents.remove(a.getLocaleParent().getId());
        }
        return !parents.isEmpty();
    }

    @Override
    public IMultiupdateBean<AutoreplaceL, Long> getMultiupdateBean() {
        return new MultiAutoreplaceCms();
    }

    @Override
    public int deleteById(Long id) {
        return service.deleteById(id);
    }

    @Override
    public AutoreplaceL getInsertBean() {
        //first selecting max sort
        AutoreplaceL a = new AutoreplaceL();
        Autoreplace parent = new Autoreplace();
        Long sort = (Long) service.getSinglePropertyU("max(localeParent.sort)");
        if (sort == null) {
            sort = 0L;
        } else {
            sort++;
        }
        parent.setSort(sort);
        a.setLocaleParent(parent);
        return a;
    }

    @Override
    public int updateObjectArrayShortById(String[] propertyNames, Long[] idValues, Object[]... propertyValues) {
        return service.updateObjectArrayShortById(propertyNames, idValues, propertyValues);
    }

    @Override
    public boolean insert(AutoreplaceL obj) {
        service.save(obj);
        return true;
    }

    @Override
    public Map<String, Object> initInsert() {
        return null;
    }

    @Override
    public AutoreplaceL getUpdateBean(Long id) {
        return service.getById(id);
    }

    @Override
    public Map<String, Object> initUpdate() {
        return null;
    }

    @Override
    public int update(AutoreplaceL command) {
        service.save(command);
        return 1;
    }

    @Override
    public Map<String, Object> initFilter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IFilterBean<AutoreplaceL> getFilterBean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AutoreplaceL> getFilteredByPropertyValue(String propertyName, Object propertyValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AutoreplaceL> getFilteredByPropertiesValue(String[] propertyName, Object[] propertyValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int saveOrUpdateCollection(Collection<AutoreplaceL> c) {
        return service.updateCollection(c);
    }

}
