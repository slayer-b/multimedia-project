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

package gallery.web.controller.pages.submodules;

import com.multimedia.service.IWallpaperService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * is needed to draw a tag cloud
 * 
 * @author demchuck.dima@gmail.com
 */
public class WallpaperTagCloudSubmodule extends ASubmodule {
    private final Ehcache cache;
    private String query_param;
    private final IWallpaperService wallpaperService;
    private final int tag_quantity;
    private final double tag_max_weight;
    private final double tag_min_weight;
    private static final String REGION_KEY = "tag_cloud";

    public WallpaperTagCloudSubmodule(IWallpaperService wallpaperService,
            HttpServletRequest request) {
        this.wallpaperService = wallpaperService;
        tag_quantity = 45;
        tag_max_weight = 23.0;
        tag_min_weight = 9.0;

        this.query_param = request.getParameter("tag");
        if (this.query_param == null || this.query_param.isEmpty()) {
            this.query_param = null;
        } else {
            this.query_param = "&amp;tag=" + this.query_param;
        }
        cache = CacheManager.getInstance().getEhcache(REGION_KEY);// TODO:
                                                                  // replace
                                                                  // with non
                                                                  // singleton
                                                                  // instance
        if (cache == null) {
            throw new IllegalArgumentException("no cache region defined for: "
                    + REGION_KEY);
        }
    }

    /**
     * this method is very
     */
    public List<Tag> getData() {
        Element elem = cache.get("big_tags");
        List<Tag> rez;
        if (elem == null || elem.getObjectValue() == null) {
            Cloud cloud = new Cloud();

            cloud.setMaxWeight(tag_max_weight);
            cloud.setMinWeight(tag_min_weight);

            cloud.setMaxTagsToDisplay(1000);

            fillCloudFromMap(cloud, wallpaperService.getTags(tag_quantity));
            rez = cloud.tags(new Tag.NameComparatorAsc());
            cache.put(new Element("big_tags", rez));
        } else {
            rez = (List<Tag>) elem.getValue();
        }
        return rez;
    }

    public String getQueryParam() {
        return this.query_param;
    }

    public static void fillCloudFromMap(Cloud cloud, Map<String, Double> values) {
        for (Entry<String, Double> e : values.entrySet()) {
            cloud.addTag(new Tag(e.getKey(), e.getValue()));
        }
    }

    @Override
    public boolean getEmpty() {
        return false;
    }

}
