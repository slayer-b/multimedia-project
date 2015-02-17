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

package gallery.web.controller.pages.types;

import com.multimedia.core.pages.types.APagesType;
import com.multimedia.service.IResolutionService;
import com.multimedia.service.IWallpaperService;
import gallery.web.controller.pages.filters.WallpaperResolutionFilter;
import gallery.web.controller.pages.submodules.ASubmodule;
import gallery.web.controller.pages.submodules.WallpaperRandomSubmodule;
import gallery.web.controller.pages.submodules.WallpaperTagCloudSubmodule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public abstract class AWallpaperType extends APagesType {
    @Autowired
    protected IWallpaperService wallpaperService;
    @Autowired
    protected IResolutionService resolutionService;

    /**
     * get submodules that will be added to request if an appropriate module is found
     *
     * @return map of submodules
     */
    protected Map<String, ASubmodule> getCommonSubmodules(HttpServletRequest request) {
        Map<String, ASubmodule> hs = new HashMap<String, ASubmodule>();
        hs.put(WallpaperRandomType.TYPE, new WallpaperRandomSubmodule(wallpaperService));
        hs.put(WallpaperTagCloudType.TYPE, new WallpaperTagCloudSubmodule(wallpaperService, request));
        return hs;
    }

    @Override
    public UrlBean execute(HttpServletRequest request, HttpServletResponse response) {
        UrlBean url = new UrlBean();
        url.setSubmodules(getCommonSubmodules(request));
        WallpaperResolutionFilter sub = new WallpaperResolutionFilter(resolutionService, wallpaperService, request);
        request.setAttribute(sub.getFilterName(), sub);
        sub.enableFilters();
        process(request, response, url);
        sub.disableFilters();
        //request.setAttribute(gallery.web.controller.pages.Config.PAGE_KEEP_PARAMETERS, sub.getQueryParam());
        return url;
    }

    /**
     * override this method to put some logic before disabling filters
     */
    public abstract void process(HttpServletRequest request, HttpServletResponse response, UrlBean url);
}
