package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import gallery.model.beans.PagesPseudonym;

public class PagesPseudonymDAO extends GenericDAOHibernate<PagesPseudonym, Long>{

    public PagesPseudonymDAO() {
        super(PagesPseudonym.class);
    }

}