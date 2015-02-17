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

import common.cms.ICmsConfig;
import common.cms.delegates.KeepParamsBuilder;
import common.cms.delegates.KeepParamsDelegate;
import common.cms.services2.ICmsService;
import common.cms.utils.DefaultsFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Map;

/**
 * Provides keep parameters, cmsConfig, cmsService.
 * use initKeepParameters to add keep parameters.
 *
 * @author demchuck.dima@gmail.com
 */
public abstract class AGenericCmsController<T, ID extends Serializable> implements InitializingBean {

    protected ICmsConfig config;
    protected ICmsService<T, ID> cmsService;

    protected KeepParamsDelegate keepParameters;

    /**
     * if you will override it, do not forget to call parents method.
     */
    @Override
    public void afterPropertiesSet() {
        StringBuilder sb = new StringBuilder();
        common.utils.MiscUtils.checkNotNull(config, "config", sb);
        common.utils.MiscUtils.checkNotNull(cmsService, "cmsService", sb);
        if (sb.length() > 0) {
            throw new NullPointerException(sb.toString());
        }

        KeepParamsBuilder builder = DefaultsFactory.getKeepParameters();
        initKeepParameters(builder);
        keepParameters = builder.getObject();
    }

    /**
     * use this method to init required keep parameters.
     *
     * @param builder an object for creating keepParams
     */
    public void initKeepParameters(KeepParamsBuilder builder) {
    }

    /**
     * this method might be called in all handler methods
     * it returns view name and initializes model with common attributes
     */
    @ModelAttribute
    protected void initModel(Map<String, Object> model) {
        model.put(config.getNavigationUrlAttribute(), config.getNavigationTemplate());
        model.put("title", config.getNameRu());
        model.put("top_header", config.getNameRu());
    }

    //TODO: if where is a way to call this method last to it, but for now call it in every request mapping

    /**
     * populates keep parameters from model if they are not currently there
     * searches keep_parameters in params and saves them to model under the same name
     */
    @ModelAttribute
    public void prePopulateKeepParameters(Map<String, Object> model, @RequestParam Map<String, String> params) {
        keepParameters.copyParameters(model, params);
    }

    /**
     * searches keep_parameters in model and saves them as stringBuilder in model
     */
    public StringBuilder getKeepParameters(Map<String, Object> model) {
        return keepParameters.getKeepParameters(model);
    }

}
