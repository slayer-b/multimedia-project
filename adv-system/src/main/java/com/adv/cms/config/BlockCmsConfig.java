package com.adv.cms.config;

import org.springframework.stereotype.Component;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Component("blockCmsConfig")
public class BlockCmsConfig extends CommonCmsConfig {

    private static final String CONTENT_VIEW_TEMPLATE = "/WEB-INF/jspf/user/block/show.jsp";
    private static final String CONTENT_MULTIUPDATE_TEMPLATE = "/WEB-INF/jspf/user/block/show.jsp";
    private static final String CONTENT_INSERT_TEMPLATE = "/WEB-INF/jspf/user/block/insert.jsp";
    private static final String CONTENT_UPDATE_TEMPLATE = "/WEB-INF/jspf/user/block/update.jsp";
    private static final String CONTENT_NAVIGATION_TEMPLATE = "/WEB-INF/jspf/user/block/navigation.jsp";
    private static final String NAME_RU = "Блоки";

    @Override
    public final String getContentViewTemplate() {
        return CONTENT_VIEW_TEMPLATE;
    }

    @Override
    public final String getContentMultiupdateTemplate() {
        return CONTENT_MULTIUPDATE_TEMPLATE;
    }

    @Override
    public final String getContentInsertTemplate() {
        return CONTENT_INSERT_TEMPLATE;
    }

    @Override
    public final String getContentUpdateTemplate() {
        return CONTENT_UPDATE_TEMPLATE;
    }

    @Override
    public final String getNavigationTemplate() {
        return CONTENT_NAVIGATION_TEMPLATE;
    }

    @Override
    public final String getNameRu() {
        return NAME_RU;
    }
}
