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

import com.adv.config.web.ITemplateConfig;
import com.adv.core.model.DefaultsConLink;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;
import com.adv.core.types.ContentType;
import com.adv.service.block.IBlockService;
import com.adv.service.user.IUserDefaultsService;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

import com.adv.service.user.IUserService;
import com.multimedia.security.Utils;
import com.multimedia.security.dto.MyUser;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("defaultsUserController")
@RequestMapping("/private/defaults.htm")
public class DefaultsController {
    public static final String ATTRIBUTE_USE_DEFAULS = "useDefaults";
    public static final String ATTRIBUTE_USER_ID = "pub_id";

    private ITemplateConfig config;
    private IUserDefaultsService service;
    private IBlockService blockService;
    private IUserService userService;

    private EnumMap<ContentType, String> urls = new EnumMap<ContentType, String>(ContentType.class);
    {
        urls.put(ContentType.LINK, "/WEB-INF/jspf/user/defaults/editConLink.jsp");
    }

    @RequestMapping
    public final String doEditPrepare(Map<String, Object> model) {
        MyUser myUser = (MyUser)model.get("user");
        User user = (User)userService.getUser(myUser.getId());
        UserDefaults u = service.getUserDefaults(user);
        model.put(config.getContentDataAttribute(), u.getDefaultsContent());
        model.put(ATTRIBUTE_USE_DEFAULS, u.getUseDefaults());
        model.put(ATTRIBUTE_USER_ID, blockService.getDefaultBlock(user).getPub_id());
        model.put(config.getContentUrlAttribute(), urls.get(u.getDefaultsContent().getContent_type()));
        return config.getTemplateUrl();
    }

    /**
     * remember this method edits only DefaultsConLink objects ...
     */
    @RequestMapping(params = "do=editLink")
    public final String doEdit(Map<String, Object> model,
            @Valid DefaultsConLink defaults, BindingResult rez,
            @RequestParam("useDefaults") Boolean use)
    {
        User user = (User) model.get("user");
        UserDefaults u = service.getUserDefaults(user);
        if (rez.hasErrors()) {
            model.put(config.getContentDataAttribute(), defaults);
            model.put(BindingResult.MODEL_KEY_PREFIX + config.getContentDataAttribute(), rez);
            model.put(ATTRIBUTE_USE_DEFAULS, use);
            common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
        } else {
            service.saveDefaultsForUser(defaults, use, user);
            model.put(config.getContentDataAttribute(), u.getDefaultsContent());
            model.put(ATTRIBUTE_USE_DEFAULS, u.getUseDefaults());
            common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
        }
        model.put(ATTRIBUTE_USER_ID, blockService.getDefaultBlock(user).getPub_id());
        model.put(config.getContentUrlAttribute(), urls.get(ContentType.LINK));
        return config.getTemplateUrl();
    }

    @ModelAttribute("user")
    public final MyUser getCurrentUser() {
        return (MyUser) Utils.getCurrentUser();
    }

    //---------------------------------- initialization ------------------------------------

    @Required
    @Resource(name = "templateConfig")
    public final void setConfig(ITemplateConfig value) {
        this.config = value;
    }

    @Required
    @Resource(name = "userDefaultsService")
    public final void setService(IUserDefaultsService value) {
        this.service = value;
    }

    @Required
    @Resource(name = "blockService")
    public final void setBlockService(IBlockService value) {
        this.blockService = value;
    }

    @Required
    @Resource(name = "blockService")
    public final void setUserService(IUserService value) {
        this.userService = value;
    }
}