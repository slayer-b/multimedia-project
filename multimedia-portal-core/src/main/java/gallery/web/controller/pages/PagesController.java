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

package gallery.web.controller.pages;

import com.multimedia.core.pages.types.APagesType;
import com.multimedia.core.pages.types.IPagesType;
import com.multimedia.service.IAutoreplaceService;
import com.multimedia.service.IPagesService;
import com.multimedia.service.IPagesServiceView;
import com.multimedia.service.IRubricationService;
import com.multimedia.service.impl.SiteConfigService;
import common.utils.CommonAttributes;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.submodules.ASubmodule;
import gallery.web.controller.pages.submodules.EmptySubmodule;
import gallery.web.controller.pages.types.EMailSendType;
import gallery.web.controller.pages.types.UrlBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author demchuck.dima@gmail.com
 */
@Controller("myPagesControllerBean")
public class PagesController {
    private final Logger logger = LoggerFactory.getLogger(PagesController.class);
    //-------------------------------------------------------------------------------------------------------------
    public static final String COUNTERS_ATTRIBUTE = "counters";
    public static final String ADVERTISEMENT_ATTRIBUTE = "advertisement";
    public static final String RUBRICATOR_ATTRIBUTE = "rubrication";

    //TODO: remake as random_wallpaper_gallery in WallpaperGalleryType
    public static final Map<String, ASubmodule> COMMON_SUBMODULES;

    static {
        COMMON_SUBMODULES = new HashMap<String, ASubmodule>();
        //COMMON_SUBMODULES.put(gallery.web.controller.pages.types.ErrorType.TYPE,  new EmptySubmodule());
        //COMMON_SUBMODULES.put(gallery.web.controller.pages.types.WallpaperAddType.TYPE,  new EmptySubmodule());
    }
//-------------------------------------------------------------------------------------------------------------
    /**
     * config class is used to store some constants
     */
    @Autowired
    private Config commonConf;
    /**
     * types of pages that will be handled
     */
    private Map<String, IPagesType> types;
    /**
     * service for working with pages
     */
    @Autowired
    private IPagesService pagesService;
    /**
     * service for autoreplacement
     */
    @Autowired
    private IAutoreplaceService autoreplaceService;
    /**
     * view service for pages
     */
    @Autowired
    private IPagesServiceView pagesViewService;
    /**
     * for site config
     */
    @Autowired
    private SiteConfigService siteConfigService;
    @Autowired
    private IRubricationService rubricationService;

    private static final String DEFAULT_OPTIMIZATION_URL = "/WEB-INF/jsp/view/pages.jsp";
    private static final String DEFAULT_NAVIGATION_URL = "/WEB-INF/jspf/view/navigation.jsp";

    @PostConstruct
    public void init() {
        Collection<IPagesType> c = types.values();
        for (IPagesType it : c) {
            if (it instanceof APagesType) {
                ((APagesType) it).setConfig(commonConf);
            }
        }
    }

    @RequestMapping({"/", "/index.htm"})
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String lang = RequestContextUtils.getLocale(request).getLanguage();
        //getting current page
        Pages page = getCurPage(request);

        request.setAttribute(Config.CURRENT_PAGE_ATTRIBUTE, page);

        //getting navigation
        List<Pages> navigation = pagesService.getAllPagesParents(page.getId(), Config.NAVIGATION_PSEUDONYMES);
        for (Pages nav : navigation) {
            if (!nav.getActive()) {
                page = null;
                break;
            }
        }

        //getting its type
        UrlBean url = null;

        if (page != null) {
            request.setAttribute(commonConf.getSiteConfigAttribute(), siteConfigService.getInstance());

            request.setAttribute(commonConf.getNavigationDataAttribute(), navigation);

            url = getType(page).execute(request, response);

            //if url == null that means that we do not need to render any view in this method
            // and thus we do not need to load any submodules, rubrications etc
            if (url != null) {
                makeReplacement(page, lang);

                loadSubmodules(navigation, request, url.getSubmodules());

                System.out.println(url.getSubmodules().keySet());
                createRubrication(request, page);

                request.setAttribute(COUNTERS_ATTRIBUTE, pagesViewService.getCounters());
                request.setAttribute(ADVERTISEMENT_ATTRIBUTE, pagesViewService.getAdvertisementForPage(page.getId()));
            }
        }
        if (url == null) {
            return null;
        } else {
            saveUrlBean(request, url);
            return new ModelAndView(commonConf.getTemplateUrl());
        }
    }

    protected void loadSubmodules(List<Pages> navigation, HttpServletRequest request, Map<String, ASubmodule> submodules) {
        if (submodules != null) {
            for (Entry<String, ASubmodule> s : COMMON_SUBMODULES.entrySet()) {
                submodules.put(s.getKey(), s.getValue());
            }
        } else {
            submodules = new HashMap<String, ASubmodule>(COMMON_SUBMODULES);
        }
        System.out.println(submodules);
        submodules.put(EMailSendType.TYPE, new EmptySubmodule());
        pagesService.activateSubmodules(navigation, submodules);
        //TODO: remake
        for (Entry<String, ASubmodule> sub : submodules.entrySet()) {
            ASubmodule subm = sub.getValue();
            if (subm.getActive()) {
                if (subm.getEmpty()) {
                    request.setAttribute(subm.getPage().getType() + "_page", subm.getPage());
                } else {
                    request.setAttribute(sub.getKey() + "_submodule", sub.getValue());
                }
            }
        }
    }

    /**
     * must return page based on received request
     */
    protected Pages getCurPage(HttpServletRequest request) {
        //first trying to load from request parameter
        String id = request.getParameter(commonConf.getIdPagesParamName());
        Pages p = null;
        if (id != null) {
            try {
                p = pagesService.getById(Long.parseLong(id, 10));
            } catch (NumberFormatException e) {
                //silent catch
            }
            if (p == null || !p.getActive()) {
                CommonAttributes.addErrorMessage("not_exists.page", request);
                p = null;
            }
        }
        if (id == null || p == null || !p.getActive()) {
            //1-st trying to load from url
            /*String url = request.getServletPath();
               if (request.getPathInfo()!=null){
                   url+=request.getPathInfo();
               }
               Long _id = getPageFromUrl(url.substring(1));
               if (_id==null){*/
            p = pagesViewService.getMainPage();
            /*} else {
                   p = pagesService.getById(_id);
               }*/
        }
        pagesService.detach(p);
        return p;
    }

    protected Long getPageFromUrl(String url) {
        String[] paths = url.split("/");
        //TODO: remake
        if (paths.length > 1 && "index.htm".equals(paths[paths.length - 1])) {
            paths[paths.length - 1] = null;
        }
        Long id = null;
        String[] where = {"id_pages", "name"};
        Object[] values = new Object[2];
        for (String name : paths) {
            Long tmp_id = null;
            if (name != null) {
                values[0] = id;
                values[1] = name;
                tmp_id = (Long) pagesService.getSinglePropertyU("id", where, values, 0);
            }
            if (tmp_id == null) {
                break;
            } else {
                id = tmp_id;
            }
        }
        logger.info("servlet = " + url);
        logger.info("id = " + id);
        return id;
    }

    protected void makeReplacement(Pages p, String lang) {
        IAutoreplaceService.IReplacement repl = autoreplaceService.getAllReplacements(lang);
        p.setTitle(repl.replaceAll(p.getTitle()));
        p.setDescription(repl.replaceAll(p.getDescription()));
        p.setKeywords(repl.replaceAll(p.getKeywords()));
        p.setInfo_top(repl.replaceAll(p.getInfo_top()));
        p.setInfo_bottom(repl.replaceAll(p.getInfo_bottom()));
    }

    protected void createRubrication(HttpServletRequest request, Pages p) {
        request.setAttribute(RUBRICATOR_ATTRIBUTE, rubricationService.getCurrentBranch(p.getId()));
    }

    /**
     * checks if navigation bean has an appropriate urls
     * if not sets defaults
     *
     * @param r   where to set urls
     * @param url bean from where to get them
     */
    protected void saveUrlBean(HttpServletRequest r, UrlBean url) {
        if (url.getNavigation() == null) {
            url.setNavigation(DEFAULT_NAVIGATION_URL);
        }
        if (url.getOptimization() == null) {
            url.setOptimization(DEFAULT_OPTIMIZATION_URL);
        }
        r.setAttribute(commonConf.getUrlAttribute(), url);
    }

    /**
     * get type of current page
     *
     * @param p page
     * @return type of page
     */
    protected IPagesType getType(Pages p) {
        try {
            return types.get(p.getType());
        } catch (Exception e) {
            return null;
        }
    }

    @Autowired
    public void setTypes(IPagesType[] types) {
        this.types = new HashMap<String, IPagesType>();
        for (IPagesType type : types) {
            this.types.put(type.getType(), type);
        }
    }

}
