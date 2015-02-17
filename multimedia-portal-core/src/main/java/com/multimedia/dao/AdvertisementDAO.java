package com.multimedia.dao;

import com.multimedia.model.beans.Advertisement;
import common.dao.GenericDAOHibernate;

public class AdvertisementDAO extends GenericDAOHibernate<Advertisement, Long> {

    public AdvertisementDAO() {
        super(Advertisement.class);
    }

}
