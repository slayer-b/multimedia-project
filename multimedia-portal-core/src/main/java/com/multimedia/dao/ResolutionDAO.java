package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import gallery.model.beans.Resolution;

public class ResolutionDAO extends GenericDAOHibernate<Resolution, Long> {

    public ResolutionDAO() {
        super(Resolution.class);
    }

}