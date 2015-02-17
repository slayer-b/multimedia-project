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
package common.cms.controller2;

import common.beans.MultiObjectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public abstract class GenericCmsController<T> extends PagenatedCmsController<T, Long> {

    private final Logger logger = LoggerFactory.getLogger(GenericCmsController.class);

    @RequestMapping(params = {"do=insert", "action=insert"})
    public String doInsert(Map<String, Object> model, @Valid T obj, BindingResult res) {
        logger.debug("do=insert, action=insert");
        getKeepParameters(model);

        model.put(config.getContentUrlAttribute(), config.getContentInsertTemplate());
        model.put(config.getContentDataAttribute(), obj);
        model.put(BindingResult.MODEL_KEY_PREFIX + config.getContentDataAttribute(), res);
        cmsService.initInsert(model);

        if (res.hasErrors()) {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            if (cmsService.insert(obj)) {
                common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
            } else {
                common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
            }
        }

        return config.getTemplateUrl();
    }

    /**
     * this method is called before we actually insert any data.
     * for example if you need to have some fields initialized by different values.
     *
     * @param model model for current request
     * @param obj   object to render on a form
     * @param res   binding result for an object
     * @return url to render
     */
    @RequestMapping(params = "do=insert")
    public String doInsertPrepare(Map<String, Object> model, @Valid T obj, BindingResult res) {
        logger.debug("do=insert prepare");
        getKeepParameters(model);

        model.put(config.getContentUrlAttribute(), config.getContentInsertTemplate());
        cmsService.initInsert(model);
        if (res.hasErrors()) {
            model.put(BindingResult.MODEL_KEY_PREFIX + config.getContentDataAttribute(), res);
            model.put(config.getContentDataAttribute(), obj);
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            model.put(config.getContentDataAttribute(), cmsService.getInsertBean(obj));
        }

        return config.getTemplateUrl();
    }

    @RequestMapping(params = {"do=update", "action=update"})
    public String doUpdate(Map<String, Object> model, @Valid T obj, BindingResult res) {
        logger.debug("do=update, action=update");
        getKeepParameters(model);

        model.put(config.getContentUrlAttribute(), config.getContentUpdateTemplate());
        model.put(config.getContentDataAttribute(), obj);
        model.put(BindingResult.MODEL_KEY_PREFIX + config.getContentDataAttribute(), res);
        cmsService.initUpdate(model);

        if (res.hasErrors()) {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            if (cmsService.update(obj) == 1) {
                common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
            } else {
                common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
            }
        }

        return config.getTemplateUrl();
    }

    @RequestMapping(params = "do=update")
    public String doUpdatePrepare(Map<String, Object> model, @RequestParam(value = "id", required = false) String id) {
        logger.debug("do=update prepare");

        Long idLong = common.utils.StringUtils.getLong(id);
        if (idLong != null) {
            T command = cmsService.getUpdateBean(idLong);
            if (command != null) {
                getKeepParameters(model);
                model.put(config.getContentUrlAttribute(), config.getContentUpdateTemplate());
                model.put(config.getContentDataAttribute(), command);
                cmsService.initUpdate(model);
                return config.getTemplateUrl();
            }
        }
        common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        return doView(model);
    }

    @RequestMapping(params = {"do=delete", "action=delete"})
    public String doDelete(Map<String, Object> model, @RequestParam(value = "id", required = false) String id) {
        logger.debug("do=delete, action=delete");

        Long idLong = common.utils.StringUtils.getLong(id);
        if (idLong == null) {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            if (cmsService.deleteById(idLong) == 1) {
                common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
            } else {
                common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
            }
        }
        return doView(model);
    }

    @RequestMapping(params = {"do=multi_update", "action=multi_update"})
    public String doMultiUpdate(Map<String, Object> model, @ModelAttribute("multiInsert") MultiObjectCommand<T> command, BindingResult res) {
        logger.debug("do=multi_update, action=multi_update");
        if (res.hasErrors()) {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            if (cmsService.saveOrUpdateCollection(command.getData()) == command.getData().size()) {
                common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
            } else {
                common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
            }
        }
        return doView(model);
    }

    @InitBinder("multiInsert")
    public void initBinder(WebDataBinder binder) {
        logger.debug("init Binder");
        if (binder.getTarget() != null && binder.getTarget() instanceof MultiObjectCommand) {
            ((MultiObjectCommand<T>) binder.getTarget()).initData(cmsService.getBeanClass());
        }
    }

    public abstract String doView(Map<String, Object> model);
}
