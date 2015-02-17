/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adv.config.web;

import org.springframework.stereotype.Component;


/**
 *
 * @author demchuck.dima@gmail.com
 */
@Component("templateConfig")
public class TemplateConfig implements ITemplateConfig {

    private static final String TEMPLATE_URL = "/WEB-INF/jspf/templates/my.jsp";

    private static final String CONTENT_URL_ATTRIBUTE = "content_url";
    private static final String NAVIGATION_URL_ATTRIBUTE = "navigation_url";

    private static final String CONTENT_DATA_ATTRIBUTE = "content_data";
    private static final String NAVIGATION_DATA_ATTRIBUTE = "navigation_data";

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
    public final String getContentUrlAttribute() {
        return CONTENT_URL_ATTRIBUTE;
    }

    @Override
    public final String getNavigationUrlAttribute() {
        return NAVIGATION_URL_ATTRIBUTE;
    }
}
