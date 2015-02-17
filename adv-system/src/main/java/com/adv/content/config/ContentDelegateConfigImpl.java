package com.adv.content.config;

import com.adv.core.types.ContentType;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Configuration("contentDelegateConfig")
public class ContentDelegateConfigImpl implements ContentDelegateConfig {
    private final Map<ContentType, String> updateUrls;
    
    public ContentDelegateConfigImpl() {
        EnumMap<ContentType, String> tmp = new EnumMap<ContentType, String>(ContentType.class);
        tmp.put(ContentType.LINK, "/WEB-INF/jspf/user/block/content/update_link.jsp");
        updateUrls = java.util.Collections.unmodifiableMap(tmp);
    }

    @Override
    public Map<ContentType, String> getUpdateUrlMap() {
        return updateUrls;
    }
    
}
