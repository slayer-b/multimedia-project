package com.multimedia.service.impl;

import com.multimedia.dao.SiteConfigDAO;
import common.services.generic.SingletonInstanceServiceImpl;
import core.model.beans.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("siteConfigService")
public class SiteConfigService extends SingletonInstanceServiceImpl<SiteConfig, Long> {

    @Autowired
    public SiteConfigService(SiteConfigDAO siteConfigDAO) {
        super(siteConfigDAO);
    }

}
