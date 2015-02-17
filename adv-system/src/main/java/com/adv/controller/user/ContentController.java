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

package com.adv.controller.user;

import com.adv.content.controller.ContentDelegate;
import com.adv.core.model.BlockContent;
import com.adv.web.support.BlockContentParam;
import com.multimedia.security.Utils;
import common.cms.ICmsConfig;
import common.cms.controller2.FilterCmsController;
import common.cms.delegates.KeepParamsBuilder;
import common.cms.services2.ICmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Controller("contentUserController")
@RequestMapping("/private/content.htm")
public class ContentController extends FilterCmsController<BlockContent> {
    private final Logger logger = LoggerFactory.getLogger(ContentController.class);

    private static final List<String> FILTER_PROPERTIES = java.util.Arrays.asList("blockLocation.id_block", "id_user");
    private static final List<String> FILTER_ALIASES = java.util.Arrays.asList("blockLocation.id_block", "blockLocation.block.id_user");

    @Resource(name = "contentBeanFactory")
    private ContentDelegate contentDelegate;

    @RequestMapping(params = {"do=update"})
    public String doUpdatePrepare(Map<String, Object> model, @RequestParam("id") Long id) {
        logger.debug("do=update prepare");
        BlockContent command = cmsService.getUpdateBean(id);
        if (command != null) {
            getKeepParameters(model);
            model.put(config.getContentUrlAttribute(), contentDelegate.getUpdateUrl(command.getContent_type()));
            model.put(config.getContentDataAttribute(), command);
            cmsService.initUpdate(model);
        } else {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        }
        return config.getTemplateUrl();
    }

//    @ModelAttribute("blockContent")
//    public BlockContent populateBlockContent(@RequestParam("content_type") ContentType contentType) {
//        return contentDelegate.getBean(contentType);
//    }

    @RequestMapping(params = {"do=update", "action=update"})
    public String doUpdate(
            @BlockContentParam BlockContent blockContent,
            BindingResult res,
            Map<String, Object> model) {

        logger.debug("do=update, action=update");
        getKeepParameters(model);

        model.put(config.getContentUrlAttribute(), contentDelegate.getUpdateUrl(blockContent.getContent_type()));
        model.put(config.getContentDataAttribute(), blockContent);
        model.put(BindingResult.MODEL_KEY_PREFIX + config.getContentDataAttribute(), res);
        cmsService.initUpdate(model);

        if (res.hasErrors()) {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else if (cmsService.update(blockContent) == 1) {
            common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
        } else {
            common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
        }

        return config.getTemplateUrl();
    }

    /**
     * we need id_user here, to control that user can see only its own blocks, locations, ...
     * @return id of current logged user
     */
    @ModelAttribute("id_user")
    public Long getCurrentUser() {
        return Utils.getCurrentUser().getId();
    }

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
        builder.removeParameter("blockLocation.id_user");
    }

    //-------------------------------------- initialization ---------------------------------------

    @Required
    @Resource(name = "contentCmsConfig")
    public void setContentConfig(ICmsConfig value) {
        this.config = value;
    }

    @Required
    @Resource(name = "contentGenericCmsService")
    public void setCmsContentService(ICmsService<BlockContent, Long> value) {
        this.cmsService = value;
    }

}
