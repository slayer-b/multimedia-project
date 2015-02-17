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

package com.multimedia.cms.controller;

import com.multimedia.cms.config.IWallpaperCmsConfig;
import com.multimedia.cms.service.ICmsWallpaperService;
import common.beans.MultiObjectCommand;
import common.cms.delegates.KeepParamsBuilder;
import common.utils.CommonAttributes;
import common.utils.StringUtils;
import core.cms.controller2.AGenericPagesCmsController;
import gallery.model.beans.Wallpaper;
import gallery.model.command.MultiInsertWallpaperCms;
import gallery.model.misc.StatusBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.lang.Object;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author demchuck.dima@gmail.com
 */
@Controller("wallpaperCmsController")
@RequestMapping("/cms/wallpaper/index.htm")
public class WallpaperCmsController extends AGenericPagesCmsController<Wallpaper>
        implements ServletContextAware {
    private final Logger logger = LoggerFactory.getLogger(WallpaperCmsController.class);

    private static final List<String> FILTER_PROPERTIES = Arrays
            .asList("id_pages_nav");
    private static final List<String> FILTER_ALIASES = Arrays
            .asList("this.id_pages");

    private IWallpaperCmsConfig wallpaperConfig;
    private ICmsWallpaperService wallpaperService;

    private ServletContext servletContext;

    // -------------------------------------- request extra methods for
    // wallpaper cms controller ---

    @RequestMapping(params = "do=prepare")
    public String doPrepare(Map<String, Object> model) {
        getKeepParameters(model);
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getPrepareUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=get_wallpaper")
    public String doAjaxGetWallpaper(Map<String, Object> model,
            @RequestParam(value = "id", required = false) String id) {

        logger.debug("do=get_wallpaper");
        Long _id = StringUtils.getLong(id);
        if (_id == null) {
            _id = (Long) model.get("id_pages_nav");
            // logger.info("getting wallpapers from page="+id);
            if (_id != null) {
                model.put(wallpaperConfig.getContentDataAttribute(),
                        wallpaperService.getWallpapersInCategory(_id));
            }
            return wallpaperConfig.getAjaxListUrl();
        } else {
            model.put(wallpaperConfig.getContentDataAttribute(),
                    wallpaperService.getUpdateBean(_id));
            return wallpaperConfig.getAjaxWallpaperUrl();
        }
    }

    @RequestMapping(params = { "do=ajax_update", "action=ajax_update" })
    public String doAjaxUpdate(Map<String, Object> model, @Valid Wallpaper obj,
            BindingResult res) {
        logger.debug("do=ajax_update; action=ajax_update");
        if (res.hasErrors()) {
            model.put(wallpaperConfig.getContentDataAttribute(), "0");
        } else {
            if (wallpaperService.update(obj, "name", "description", "title",
                    "tags", "id_pages", "active", "optimized",
                    "optimized_manual") == 1) {
                model.put(wallpaperConfig.getContentDataAttribute(), "1");
            } else {
                model.put(wallpaperConfig.getContentDataAttribute(), "0");
            }
        }
        return wallpaperConfig.getAjaxRespUrl();
    }

    @RequestMapping(params = "do=view_onlyImages")
    public String doAjaxView(Map<String, Object> model) {
        super.doView(model);
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getViewOnlyImgUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=updateAjaxForm")
    public String doUpdateAjaxForm(Map<String, Object> model) {
        logger.debug("do=updateAjaxForm");
        getKeepParameters(model);
        wallpaperService.initUpdate(model);

        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getUpdateAjaxForm());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=insertMulti")
    public String doInsertMultiPrepare(
            Map<String, Object> model,
            @RequestParam(value = "id_pages_one", required = false) Long id_pages_one) {

        logger.debug("do=insertMulti");
        getKeepParameters(model);
        wallpaperService.initInsert(model);

        model.put(wallpaperConfig.getContentDataAttribute(),
                new MultiInsertWallpaperCms(id_pages_one));
        model.put("empty_object", new Wallpaper());
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getInsertMultiFormUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = { "do=insertMulti", "action=insertMulti" })
    public String doInsertMulti(
            Map<String, Object> model,
            @ModelAttribute("multiInsert") MultiObjectCommand<Wallpaper> command,
            BindingResult res,
            @RequestParam(value = "id_pages_one", required = false) Long id_pages_one) {
        logger.debug("do=insertMulti; action=insertMulti");

        if (res.hasErrors()) {
            CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            if (id_pages_one != null) {
                for (Wallpaper wallpaper : command.getData()) {
                    wallpaper.setId_pages(id_pages_one);
                }
            }
            int count = wallpaperService.insert(command.getData());
            if (count > 0) {
                model.put("wallpaper_count", count);
                model.put("wallpaper_quantity", command.getData().size());
                CommonAttributes.addHelpMessage(
                        "operation_succeed", model);
            } else {
                CommonAttributes.addErrorMessage("operation_fail",
                        model);
            }
        }

        return doInsertMultiPrepare(model, id_pages_one);
    }

    @RequestMapping(params = { "do=ajax_insert", "action=ajax_insert" })
    public String doAjaxInsert(Map<String, Object> model, @Valid Wallpaper obj,
            BindingResult res) {
        logger.debug("do=ajax_insert; action=ajax_insert");
        if (res.hasErrors()) {
            model.put(wallpaperConfig.getContentDataAttribute(), "0");
        } else {
            if (wallpaperService.insert(obj)) {
                model.put(wallpaperConfig.getContentDataAttribute(), "1");
            } else {
                model.put(wallpaperConfig.getContentDataAttribute(), "0");
            }
        }
        return wallpaperConfig.getAjaxRespUrl();
    }

    @RequestMapping(params = "do=upload")
    public String doUpload(Map<String, Object> model) {
        logger.debug("do=upload");
        Object o = servletContext.getAttribute(wallpaperConfig
                .getUploadAttributeName());

        if (o == null) {
            // upload not started
            model.put(wallpaperConfig.getContentDataAttribute(),
                    wallpaperService.createPageFolders());
            model.put(wallpaperConfig.getContentUrlAttribute(),
                    wallpaperConfig.getUploadPrepareUrl());
        } else {
            // upload in progress -- show progress
            CommonAttributes.addHelpMessage(
                    "operation_in_progress", model);
            model.put(wallpaperConfig.getContentUrlAttribute(),
                    wallpaperConfig.getUploadProcessUrl());
        }
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = { "do=upload", "action=upload" })
    public String doUploadStart(Map<String, Object> model,
            @RequestParam(value = "id", required = false) Long id) {
        logger.debug("do=upload; action=upload");
        Object o = servletContext.getAttribute(wallpaperConfig
                .getUploadAttributeName());

        if (o == null) {
            // start upload
            StatusBean usb = new StatusBean();
            servletContext.setAttribute(
                    wallpaperConfig.getUploadAttributeName(), usb);
            try {
                wallpaperService.uploadWallpapers(null, Boolean.FALSE, id, usb);
                CommonAttributes.addHelpMessage("operation_succeed", model);
            } finally {
                servletContext.removeAttribute(wallpaperConfig
                        .getUploadAttributeName());
            }
            model.put(wallpaperConfig.getContentUrlAttribute(),
                    wallpaperConfig.getPrepareUrl());
        } else {
            // upload in progress -- show progress
            CommonAttributes.addHelpMessage("operation_in_progress", model);
            model.put(wallpaperConfig.getContentUrlAttribute(),
                    wallpaperConfig.getUploadProcessUrl());
        }
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=pre_upload")
    public String doPreUpload(Map<String, Object> model) {
        logger.debug("do=pre_upload");
        Object o = servletContext.getAttribute(wallpaperConfig
                .getUploadAttributeName());

        if (o == null) {
            // start upload prepare
            StatusBean usb = new StatusBean();
            servletContext.setAttribute(
                    wallpaperConfig.getUploadAttributeName(), usb);
            try {
                wallpaperService.preUploadWallpapers(usb);
                CommonAttributes.addHelpMessage(
                        "operation_succeed", model);
            } finally {
                servletContext.removeAttribute(wallpaperConfig
                        .getUploadAttributeName());
            }
            model.put(wallpaperConfig.getContentUrlAttribute(),
                    wallpaperConfig.getPrepareUrl());
        } else {
            // upload in progress -- show progress
            CommonAttributes.addHelpMessage(
                    "operation_in_progress", model);
            model.put(wallpaperConfig.getContentUrlAttribute(),
                    wallpaperConfig.getUploadProcessUrl());
        }
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=wallpaper_resolution")
    public String doRenewResolution(Map<String, Object> model) {
        logger.debug("do=wallpaper_resolution");

        Object o = servletContext.getAttribute(wallpaperConfig
                .getRenewResolutionAttributeName());

        if (o == null) {
            StatusBean sb = new StatusBean();
            servletContext.setAttribute(
                    wallpaperConfig.getRenewResolutionAttributeName(), sb);
            try {
                wallpaperService.renewResolution(sb);
            } finally {
                servletContext.removeAttribute(wallpaperConfig
                        .getRenewResolutionAttributeName());
            }
            if (sb.getDone() > 0L) {
                CommonAttributes.addHelpMessage(
                        "operation_succeed", model);
            } else {
                CommonAttributes.addErrorMessage("operation_fail",
                        model);
            }
            model.put(wallpaperConfig.getContentDataAttribute(), sb);
        } else {
            CommonAttributes.addHelpMessage(
                    "operation_in_progress", model);
            model.put(wallpaperConfig.getContentDataAttribute(), o);
        }

        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getPrepareUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=find_duplicates")
    public String doFindDuplicates(Map<String, Object> model) {
        logger.debug("do=find_duplicates");

        model.put(wallpaperConfig.getContentDataAttribute(),
                wallpaperService.findWallpaperDuplicates(0));
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getDuplicatesShowUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = { "do=deleteMulti", "action=deleteMulti" })
    public String doDeleteMulti(Map<String, Object> model,
            @RequestParam("id") Long[] id) {
        logger.debug("do=deleteMulti; action=deleteMulti");
        if (wallpaperService.deleteById(id) > 0) {
            CommonAttributes.addHelpMessage("operation_succeed",
                    model);
        } else {
            CommonAttributes.addErrorMessage("operation_fail",
                    model);
        }
        return doView(model);
    }

    @RequestMapping(params = { "do=moveMulti", "action=moveMulti" })
    public String doMoveMulti(Map<String, Object> model,
            @RequestParam("id") Long[] id,
            @RequestParam("id_pages_new") Long id_pages_new) {
        logger.debug("do=moveMulti; action=moveMulti");
        if (wallpaperService.moveWallpapersToPage(id, id_pages_new) > 0) {
            CommonAttributes.addHelpMessage("operation_succeed",
                    model);
        } else {
            CommonAttributes.addErrorMessage("operation_fail",
                    model);
        }
        return doView(model);
    }

    @RequestMapping(params = { "do=optimize", "action=optimize" })
    public String doOptimizeWallpaper(Map<String, Object> model,
            @RequestParam("id") Long id,
            @RequestParam(value = "ajax", required = false) String ajax) {
        logger.debug("do=optimize; action=optimize");
        wallpaperService.optimizeWallpaper(id);
        if (ajax == null) {
            CommonAttributes.addHelpMessage("operation_succeed",
                    model);
            return doView(model);
        } else {
            model.put(wallpaperConfig.getContentDataAttribute(),
                    wallpaperService.getUpdateBean(id));
            return wallpaperConfig.getAjaxWallpaperUrl();
        }
    }

    @RequestMapping(params = "do=optimize_wallpapers")
    public String doOptimizePrepare(Map<String, Object> model) {
        logger.debug("do=optimize_wallpapers");

        model.put(wallpaperConfig.getContentDataAttribute(),
                wallpaperService.getCategoriesLayered());
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getOptimizationPrepareUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = { "do=optimize_wallpapers",
            "action=optimize_wallpapers" })
    public String doOptimizeWallpapers(Map<String, Object> model,
            @RequestParam("id") Long[] id) {
        logger.debug("do=optimize_wallpapers; action=optimize_wallpapers");

        wallpaperService.optimizeWallpaperCategories(id);
        CommonAttributes
                .addHelpMessage("operation_succeed", model);

        model.put(wallpaperConfig.getContentDataAttribute(),
                wallpaperService.getCategoriesLayered());
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getOptimizationPrepareUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = { "do=optimize_wallpapers", "action=set_optimized" })
    public String doSetOptimized(Map<String, Object> model,
            @RequestParam("id") Long[] id,
            @RequestParam(value = "type", required = false) Boolean type) {
        logger.debug("do=optimize_wallpapers; action=set_optimized");

        if (type == null) {
            wallpaperService.setWallpaperOptimizationCategories(id,
                    Boolean.FALSE);
        } else {
            wallpaperService.setWallpaperOptimizationCategories(id, type);
        }

        CommonAttributes
                .addHelpMessage("operation_succeed", model);

        model.put(wallpaperConfig.getContentDataAttribute(),
                wallpaperService.getCategoriesLayered());
        model.put(wallpaperConfig.getContentUrlAttribute(),
                wallpaperConfig.getOptimizationPrepareUrl());
        return wallpaperConfig.getTemplateUrl();
    }

    @RequestMapping(params = "do=moveWallpapersFromRoot")
    @ResponseBody
    public List<String> doMoveWallpapersFromRoot() {
        List<String> result = wallpaperService.moveWallpaperImagesFromRootToChild();
        logger.info("Moving root wallpapers done. {}", result.get(0));
        return result;
    }

    @InitBinder
    public void initUploadBinder(WebDataBinder binder) {
        logger.debug("Setting bind empty multipart files = false");
        binder.setBindEmptyMultipartFiles(false);
    }

    // -------------------------------------- overridden methods from parent

    @Override
    public List<String> getFilterProperties() {
        return FILTER_PROPERTIES;
    }

    @Override
    public List<String> getFilterAliases() {
        return FILTER_ALIASES;
    }

    @Override
    public void initKeepParameters(KeepParamsBuilder builder) {
        super.initKeepParameters(builder);
        builder.addParameter("do");
    }

    // ------------- initialization -------------------
    @Resource(name = "wallpaperCmsConfig")
    public void setWallpaperConfig(IWallpaperCmsConfig value) {
        this.config = value;
        this.wallpaperConfig = value;
    }

    @Resource(name = "cmsWallpaperService")
    public void setCmsWallpaperService(ICmsWallpaperService value) {
        this.cmsService = value;
        this.wallpaperService = value;
    }

    @Override
    public void setServletContext(ServletContext value) {
        this.servletContext = value;
    }
}
