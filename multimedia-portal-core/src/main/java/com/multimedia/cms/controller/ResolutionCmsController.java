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

import com.multimedia.service.IResolutionService;
import common.cms.ICmsConfig;
import common.utils.CommonAttributes;
import common.web.controller.CommonActions;
import gallery.model.beans.Resolution;
import gallery.web.controller.resolution.Config;
import gallery.web.controller.resolution.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author demchuck.dima@gmail.com
 */
@Controller("resolutionCmsController")
@RequestMapping("/cms/resolution/index.htm")
public class ResolutionCmsController {
    /** config class is used to store some constants */
    @Autowired
    private ICmsConfig commonCmsConf;
    /** service for working with data */
    @Autowired
    private IResolutionService resolutionService;
    /** validator */
    private Validation validator;

    private static final String SHOW_URL = "/WEB-INF/jspf/cms/resolution/show.jsp";
    private static final String INSERT_URL = "/WEB-INF/jspf/cms/resolution/insert.jsp";
    private static final String NAVINATION_URL = "/WEB-INF/jspf/cms/resolution/navigation.jsp";

    @PostConstruct
    public void init() {
        validator = new Validation(resolutionService);
    }

    @RequestMapping(params = "do=view")
    public ModelAndView doView(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> m = getCommonModel();
        m.put(commonCmsConf.getContentUrlAttribute(), SHOW_URL);
        m.put("resolutions", resolutionService.getOrdered(null,
                Config.ORDER_BY, Config.ORDER_HOW));

        return new ModelAndView(commonCmsConf.getTemplateUrl(), m);
    }

    private static final String[] REQUIRED_FIELDS = { "width", "height" };

    @RequestMapping(params = "do=insert")
    public ModelAndView doInsert(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> m = getCommonModel();
        m.put(commonCmsConf.getContentUrlAttribute(), INSERT_URL);
        m.put("editForm_topHeader", "Добавление");

        String action = req
                .getParameter(gallery.web.controller.Config.ACTION_PARAM_NAME);
        Resolution command = new Resolution();

        if ("insert".equals(action)) {
            /** bind command */
            ServletRequestDataBinder binder = new ServletRequestDataBinder(
                    command);
            binder.setRequiredFields(REQUIRED_FIELDS);
            binder.bind(req);
            BindingResult res = binder.getBindingResult();
            validator.validateCMS(command, res);
            if (res.hasErrors()) {
                m.put(BindingResult.MODEL_KEY_PREFIX + "command", res);
                m.put("command", command);
                CommonAttributes.addErrorMessage("form_errors",
                        req);
            } else {
                resolutionService.save(command);
                m.put("command", command);
                CommonAttributes.addHelpMessage(
                        "operation_succeed", req);
            }
        } else {
            m.put("command", command);
        }

        return new ModelAndView(commonCmsConf.getTemplateUrl(), m);
    }

    @RequestMapping(params = "multiUpdate")
    public ModelAndView doMultiUpdate(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if ("multiUpdate".equals(action)) {
            ResolutionMulti command = new ResolutionMulti();

            ServletRequestDataBinder binder = new ServletRequestDataBinder(
                    command);
            binder.bind(req);
            if (binder.getBindingResult().hasErrors()) {
                CommonAttributes.addErrorMessage("form_errors",
                        req);
            } else {
                int rez = resolutionService.updateObjectArrayShortById(
                        new String[] { "width", "height" }, command.getId(),
                        command.getWidth(), command.getHeight());
                if (rez > 0) {
                    CommonAttributes.addHelpMessage("operation_succeed", req);
                } else {
                    CommonAttributes.addErrorMessage("operation_fail", req);
                }
            }
        }
        // after updating values shoving all pages(doView)
        return doView(req, resp);
    }

    @RequestMapping(params = "delete")
    public ModelAndView doDelete(HttpServletRequest req,
            HttpServletResponse resp) {

        CommonActions.doDelete(resolutionService, req);

        return doView(req, resp);
    }

    public Map<String, Object> getCommonModel() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("title", "Разрешения");
        m.put("top_header", "Разрешения");

        m.put(commonCmsConf.getNavigationUrlAttribute(), NAVINATION_URL);
        return m;
    }

}

class ResolutionMulti {
    private Long[] id;
    private Integer[] width;
    private Integer[] height;

    public Long[] getId() {
        return id;
    }

    public void setId(Long[] id) {
        this.id = id;
    }

    public Integer[] getWidth() {
        return width;
    }

    public void setWidth(Integer[] width) {
        this.width = width;
    }

    public Integer[] getHeight() {
        return height;
    }

    public void setHeight(Integer[] height) {
        this.height = height;
    }
}
