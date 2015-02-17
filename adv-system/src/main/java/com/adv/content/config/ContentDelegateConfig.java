package com.adv.content.config;

import com.adv.core.types.ContentType;
import java.util.Map;

/**
 *
 * @author Dmitriy_Demchuk
 */
public interface ContentDelegateConfig {

    /**
     * get url map for all content types.
     * @return map
     */
    Map<ContentType, String> getUpdateUrlMap();

}
