package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import gallery.model.beans.Autoreplace;

public class AutoreplaceDAO extends GenericDAOHibernate<Autoreplace, Long> {

    public AutoreplaceDAO() {
        super(Autoreplace.class);
    }

}
