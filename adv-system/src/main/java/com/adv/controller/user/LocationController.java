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

import com.adv.core.model.BlockLocation;
import com.multimedia.security.Utils;
import common.cms.ICmsConfig;
import common.cms.controller2.FilterGenericCmsController;
import common.cms.delegates.KeepParamsBuilder;
import common.cms.services2.ICmsService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author demchuck.dima@gmail.com
 */
@Controller("locationUserController")
@RequestMapping("/private/location.htm")
public class LocationController extends FilterGenericCmsController<BlockLocation> {
    private final List<String> filter_properties = java.util.Arrays.asList("id_block", "id_user");
    private final List<String> filter_aliases = java.util.Arrays.asList("id_block", "block.id_user");

    /*@Override
    @RequestMapping(params={"do=update", "action=update"})
    public String doUpdate(Map<String, Object> map, BlockLocation t, BindingResult br) {
        switch (t.getBlockContent().getContent_type()) {
            case LINK:
                return super.doUpdate(map, t, br);
            default:
                throw new UnsupportedOperationException("not implemented yet.");
        }
    }*/

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
        return filter_properties;
    }

    @Override
    public List<String> getFilterAliases() {
        return filter_aliases;
    }

    @Override
    public void initKeepParameters(KeepParamsBuilder builder) {
        super.initKeepParameters(builder);
        builder.removeParameter("id_user");
    }

    //-------------------------------------- initialization ---------------------------------------

    @Required
    @Resource(name = "locationCmsConfig")
    public void setLocationConfig(ICmsConfig value) {
        this.config = value;
    }

    @Required
    @Resource(name = "locationGenericCmsService")
    public void setCmsLocationService(ICmsService<BlockLocation, Long> value) {
        this.cmsService = value;
    }
}
