package common.cms.utils;

import com.multimedia.web.support.PaginatedListUtils;
import common.cms.delegates.KeepParamsBuilder;

/**
 *
 * @author Dmitriy_Demchuk
 */
public final class DefaultsFactory {
    private DefaultsFactory() { }
    
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_NAVIGATION_SIZE = 5;

    public static PaginatedListUtils getListUtils() {
        return new PaginatedListUtils(DEFAULT_PAGE_SIZE, DEFAULT_NAVIGATION_SIZE);
    }

    public static KeepParamsBuilder getKeepParameters() {
        return new KeepParamsBuilder();
    }
}
