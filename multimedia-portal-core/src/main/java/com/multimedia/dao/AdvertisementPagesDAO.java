package com.multimedia.dao;

import com.multimedia.model.beans.AdvertisementPages;
import common.dao.GenericDAOHibernate;

public class AdvertisementPagesDAO extends GenericDAOHibernate<AdvertisementPages, Long> {

    public AdvertisementPagesDAO() {
        super(AdvertisementPages.class);
    }

}
