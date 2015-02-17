package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import core.model.beans.SiteConfig;

public class SiteConfigDAO extends GenericDAOHibernate<SiteConfig, Long> {

    public SiteConfigDAO() {
        super(SiteConfig.class);
    }

}
