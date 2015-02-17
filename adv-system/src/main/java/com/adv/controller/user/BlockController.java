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

import com.adv.core.model.Block;
import com.multimedia.security.Utils;
import common.cms.ICmsConfig;
import common.cms.controller2.FilterGenericCmsController;
import common.cms.delegates.KeepParamsBuilder;
import common.cms.services2.ICmsService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * provides control of blocks for user.
 * @author demchuck.dima@gmail.com
 */
@Controller("blockUserController")
@RequestMapping("/private/block.htm")
public class BlockController extends FilterGenericCmsController<Block> {
    private final List<String> filter_properties = java.util.Arrays.asList("id_user");
    private final List<String> filter_aliases = java.util.Arrays.asList("id_user");

    @Override
    @RequestMapping(params = {"do=insert", "action=insert"} )
    public final String doInsert(Map<String, Object> model, Block obj, BindingResult res) {
        Long id_user = (Long) model.get("id_user");
        obj.setId_user(id_user);
        return super.doInsert(model, obj, res);
    }

    @ModelAttribute("id_user")
    public final Long getCurrentUser() {
        return Utils.getCurrentUser().getId();
    }

    @Override
    public final List<String> getFilterProperties() {
        return filter_properties;
    }

    @Override
    public final List<String> getFilterAliases() {
        return filter_aliases;
    }

    @Override
    public void initKeepParameters(KeepParamsBuilder builder) {
        super.initKeepParameters(builder);
        builder.removeParameter("id_user");
    }

    //-------------------------------------- initialization ---------------------------------------

    @Resource(name = "blockCmsConfig")
    public final void setBlockCmsConfig(ICmsConfig value) {
        this.config = value;
    }

    @Resource(name = "blockGenericCmsService")
    public final void setCmsBlockGenericService(ICmsService<Block, Long> value) {
        this.cmsService = value;
    }
}
