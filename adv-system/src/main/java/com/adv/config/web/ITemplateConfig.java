
package com.adv.config.web;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface ITemplateConfig {

    /**
     * url of page that serves as a template (may include places for including navigation and content pages).
     * @return url
     */
    String getTemplateUrl();

    /**
     * model name for content.
     * @return name of attribute
     */
    String getContentDataAttribute();

    /**
     * model name for navigation.
     * @return name of attribute
     */
    String getNavigationDataAttribute();

    /**
     * url to jsp that renders main content.
     * @return attribute name with url for rendering content
     */
    String getContentUrlAttribute();

    /**
     * url to jsp that renders navigation content.
     * @return attribute name with url for rendering navigation
     */
    String getNavigationUrlAttribute();
}
