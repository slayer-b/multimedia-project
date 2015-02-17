package com.multimedia.dao;

import com.multimedia.model.beans.Locale;
import common.dao.GenericDAOHibernate;

public class LocaleDAO extends GenericDAOHibernate<Locale, Long> {

    public LocaleDAO() {
        super(Locale.class);
    }

}
