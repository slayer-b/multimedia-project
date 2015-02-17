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

import com.multimedia.dao.PagesPseudonymDAO;
import com.multimedia.service.IPagesPseudonymService;
import common.services.generic.GenericServiceImpl;
import gallery.model.beans.PagesPseudonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author demchuck.dima@gmail.com
 */
@Service
public class PagesPseudonymServiceImpl extends GenericServiceImpl<PagesPseudonym, Long> implements IPagesPseudonymService {
    private static final String[] ORDER_BY = {"sort"};
    private static final String[] ORDER_HOW = {"asc"};

    @Autowired
    public PagesPseudonymServiceImpl(PagesPseudonymDAO pagesPseudonymDAO) {
        super(pagesPseudonymDAO);
    }

    //----------------------------------------------------------------------
    @Override
    public Object getSinglePropertyUOrdered(String prop, String[] propNames, Object[] propValues, int number) {
        return dao.getSinglePropertyU(prop, propNames, propValues, number, ORDER_BY, ORDER_HOW);
    }

    @Override
    public List<PagesPseudonym> getShortByPropertyValueOrdered(String property, Object value) {
        return getByPropertyValueOrdered(null, property, value, ORDER_BY, ORDER_HOW);
    }

    @Override
    public List<PagesPseudonym> getShortByPropertiesValueOrdered(String[] property, Object[] value) {
        return getByPropertiesValueOrdered(null, property, value, ORDER_BY, ORDER_HOW);
    }

}
