package com.adv.cms.config;

import common.cms.ICmsConfig;

/**
 * Presents an abstract superclass for all cms configurations. Defines
 * attributes that may be common across all cms configurations. Attributes may
 * be overriden in children.
 * 
 * @author demchuck.dima@gmail.com
 */
public abstract class CommonCmsConfig implements ICmsConfig {

    private static final String CONTENT_URL_ATTRIBUTE = "content_url";
    private static final String NAVIGATION_URL_ATTRIBUTE = "navigation_url";

    private static final String TEMPLATE_URL = "/WEB-INF/jspf/templates/my.jsp";

    private static final String CONTENT_DATA_ATTRIBUTE = "content_data";
    private static final String NAVIGATION_DATA_ATTRIBUTE = "navigation_data";

    private static final String URL_ATTRIBUTE = "url";

    @Override
    public final String getContentUrlAttribute() {
        return CONTENT_URL_ATTRIBUTE;
    }

    @Override
    public final String getNavigationUrlAttribute() {
        return NAVIGATION_URL_ATTRIBUTE;
    }

    @Override
    public final String getTemplateUrl() {
        return TEMPLATE_URL;
    }

    @Override
    public final String getContentDataAttribute() {
        return CONTENT_DATA_ATTRIBUTE;
    }

    @Override
    public final String getNavigationDataAttribute() {
        return NAVIGATION_DATA_ATTRIBUTE;
    }

    @Override
    public final String getUrlAttribute() {
        return URL_ATTRIBUTE;
    }

}
