package com.adv.content.controller;

import com.adv.core.model.BlockContent;
import com.adv.core.types.ContentType;

/**
 *
 * @author Dmitriy_Demchuk
 */
public interface ContentDelegate {

    /**
     * get a bean corresponding to the given type.(a subtype of BlockContent)
     * @param type type of a result bean
     * @return concrete bean
     */
    BlockContent getBean(ContentType type);

    /**
     * get a url for rendering given type.(a subtype of BlockContent)
     * @param type type of a result bean
     * @return concrete bean
     */
    String getUpdateUrl(ContentType type);

}
