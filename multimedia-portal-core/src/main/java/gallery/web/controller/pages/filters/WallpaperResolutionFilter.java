/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gallery.web.controller.pages.filters;

import com.multimedia.service.IResolutionService;
import com.multimedia.service.IWallpaperService;
import common.utils.RequestUtils;
import gallery.model.beans.Resolution;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author demchuck.dima@gmail.com
 */
public class WallpaperResolutionFilter implements IFilter {
    protected static final String NAME = "WallpaperResolutionFilter";
    protected static final String SESSION_KEY = "WallpaperResolution";
    protected static final Long REMOVE_VALUE = 0L;
    public static final String REGION_KEY = "wallpaper_resolutions";

    private Long id_resolution_nav;
    private final Ehcache cache;

    private final Resolution cur_resolution;
    private List<Resolution> resolutions;

    private final IResolutionService resolutionService;
    private final IWallpaperService wallpaperService;

    public WallpaperResolutionFilter(IResolutionService service, IWallpaperService wallpaperService, HttpServletRequest request) {
        this.resolutionService = service;
        this.wallpaperService = wallpaperService;
        id_resolution_nav = RequestUtils.getLongParam(request, "id_resolution_nav");
        HttpSession session = request.getSession();
        if (id_resolution_nav == null) {
            id_resolution_nav = (Long) session.getAttribute(SESSION_KEY);
        }
        if (id_resolution_nav != null) {
            //TODO: optimize, or mb cache results or mb xz
            if (id_resolution_nav.equals(REMOVE_VALUE)) {
                this.id_resolution_nav = null;
                this.cur_resolution = null;
            } else {
                this.cur_resolution = resolutionService.getById(id_resolution_nav);
                if (cur_resolution == null) {
                    this.id_resolution_nav = null;
                }
            }
        } else {
            this.cur_resolution = null;
        }
        session.setAttribute(SESSION_KEY, id_resolution_nav);

        //TODO: replace with non singleton instance
        cache = CacheManager.getInstance().getEhcache(REGION_KEY);
        if (cache == null) {
            throw new IllegalArgumentException("no cache region defined for: " + REGION_KEY);
        }
    }

    @Override
    public void enableFilters() {
        if (id_resolution_nav != null) {
            Resolution r = resolutionService.getById(id_resolution_nav);
            //TODO: remake it to filter by id, because anyway we have resolution id
            wallpaperService.enableResolutionFilter(r.getWidth(), r.getHeight());
        }
    }

    @Override
    public void disableFilters() {
        if (id_resolution_nav != null) {
            wallpaperService.disableResolutionFilter();
        }
    }

    public Resolution getCurrent() {
        return cur_resolution;
    }

    public List<Resolution> getData() {
        if (resolutions == null) {
            Element rez = cache.get(REGION_KEY);
            if (rez == null || rez.getObjectValue() == null) {
                resolutions = resolutionService.getByPropertyValueOrdered(null, null, null,
                        gallery.web.controller.resolution.Config.ORDER_BY, gallery.web.controller.resolution.Config.ORDER_HOW);
                cache.put(new Element(REGION_KEY, resolutions));
            } else {
                resolutions = (List<Resolution>) rez.getValue();
            }
        }
        return resolutions;

    }

    @Override
    public String getQueryParam() {
        return null;
    }

    @Override
    public String getFilterName() {
        return NAME;
    }
}
