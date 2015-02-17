package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import gallery.model.beans.AutoreplaceL;

public class AutoreplaceLDAO extends GenericDAOHibernate<AutoreplaceL, Long> {

    public AutoreplaceLDAO() {
        super(AutoreplaceL.class);
    }

}
