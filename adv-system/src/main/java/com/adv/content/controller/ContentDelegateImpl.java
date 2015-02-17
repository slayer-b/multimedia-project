package com.adv.content.controller;

import com.adv.content.config.ContentDelegateConfig;
import com.adv.core.model.BlockContent;
import com.adv.core.model.ContentLink;
import com.adv.core.types.ContentType;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

/**
 * simple implementation of ContentBeanFactory.
 * @author Dmitriy_Demchuk
 */
@Component("contentBeanFactory")
public class ContentDelegateImpl implements ContentDelegate {
    
    private ContentDelegateConfig config;

    @Override
    public BlockContent getBean(ContentType type) {
        switch (type) {
            case LINK:
                return new ContentLink();
            default:
                return null;
        }
    }

    @Override
    public String getUpdateUrl(ContentType type) {
        return config.getUpdateUrlMap().get(type);
    }
    
//-------------------------- injection --------------------------------
    @Required
    @Resource(name = "contentDelegateConfig")
    public void setConfig(ContentDelegateConfig value) {
        this.config = value;
    }

}
