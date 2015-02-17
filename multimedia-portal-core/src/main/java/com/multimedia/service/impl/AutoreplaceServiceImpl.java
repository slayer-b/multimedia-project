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

import com.multimedia.dao.AutoreplaceDAO;
import com.multimedia.dao.AutoreplaceLDAO;
import com.multimedia.service.IAutoreplaceService;
import common.services.generic.GenericLocalizedServiceImpl;
import gallery.model.beans.Autoreplace;
import gallery.model.beans.AutoreplaceL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author demchuck.dima@gmail.com
 */
@Service
public class AutoreplaceServiceImpl extends GenericLocalizedServiceImpl<AutoreplaceL, Long, Autoreplace, Long> implements IAutoreplaceService {
    private static final String[] WHERE_NAMES = {"localeParent.active", "lang"};
    private static final String[] ORDER_BY = {"localeParent.sort"};
    private static final String[] ORDER_HOW = {"asc"};

    @Autowired
    public AutoreplaceServiceImpl(AutoreplaceLDAO autoreplaceLDAO, AutoreplaceDAO autoreplaceDAO) {
        super(autoreplaceLDAO, autoreplaceDAO);
    }

    /**
     * get all values for auto replacing
     *
     * @return class for replacing
     */
    @Override
    public IReplacement getAllReplacements(String lang) {
        //connects to a DB and gets new values and names from there
        List<AutoreplaceL> data = dao.getByPropertiesValuePortionOrdered(
                null, null, WHERE_NAMES, new Object[]{Boolean.TRUE, lang}, 0, -1, ORDER_BY, ORDER_HOW);
        List<String> values = new ArrayList<String>(data.size());
        List<Pattern> namesPattern = new ArrayList<Pattern>(data.size());
        for (AutoreplaceL aData : data) {
            namesPattern.add(Pattern.compile(Pattern.quote(aData.getLocaleParent().getCode())));
            values.add(aData.getText());
        }
        return new Replacement(values, namesPattern);
    }

    static class Replacement implements IReplacement {
        private final List<String> values;
        private final List<Pattern> namesPattern;

        private Replacement(List<String> values, List<Pattern> names_pattern) {
            this.values = values;
            this.namesPattern = names_pattern;
        }


        @Override
        public String replaceAll(String str) {
            if (str == null || values == null || values.isEmpty()) {
                return str;
            }
            boolean notReplaced = true;
            String rez = str;
            while (notReplaced) {
                notReplaced = false;
                for (int i = 0; i < values.size(); i++) {
                    Matcher m = namesPattern.get(i).matcher(rez);

                    StringBuffer sb = new StringBuffer();
                    if (m.find()) {
                        do {
                            m.appendReplacement(sb, values.get(i));
                        } while (m.find());
                        m.appendTail(sb);
                        rez = sb.toString();
                        notReplaced = true;
                    }
                }
            }
            return rez;
        }
    }

}