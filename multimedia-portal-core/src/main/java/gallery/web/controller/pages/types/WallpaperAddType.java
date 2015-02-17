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

package gallery.web.controller.pages.types;

import com.multimedia.service.IPagesService;
import com.multimedia.service.IWallpaperService;
import common.bind.ABindValidator;
import gallery.model.beans.Wallpaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

public class WallpaperAddType extends ASingleContentType {
    public static final String TYPE = "system_add_wallpaper";
    public static final String TYPE_RU = "---Добавление фото---";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTypeRu() {
        return TYPE_RU;
    }

    public static final String CATEGORIES_ATTR = "categories_wallpaper";
    ABindValidator validator;
    @Autowired
    private IPagesService pagesService;
    @Autowired
    private IWallpaperService wallpaperService;

    @Override
    public UrlBean execute(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        request.setAttribute(CATEGORIES_ATTR, pagesService.getAllCombobox(Boolean.TRUE, Boolean.TRUE, WallpaperGalleryType.TYPE));

        String action = request.getParameter(gallery.web.controller.Config.ACTION_PARAM_NAME);
        /** bind command */
        Wallpaper command = new Wallpaper();
        if ("addWallpaper".equals(action)) {
            BindingResult res = validator.bindAndValidate(command, request);
            if (res.hasErrors() || !wallpaperService.getImage(command)) {
                //m.putAll(res.getModel());
                request.setAttribute(BindingResult.MODEL_KEY_PREFIX + "command", res);
                request.setAttribute("command", command);
                common.utils.CommonAttributes.addErrorMessage("form_errors", request);
            } else {
                //command.setUser(com.multimedia.security.Utils.getCurrentUser(request));
                command.setActive(Boolean.FALSE);
                wallpaperService.save(command);
                request.setAttribute("command", new Wallpaper());
                common.utils.CommonAttributes.addHelpMessage("operation_succeed", request);
            }
        } else {
            request.setAttribute("command", command);
        }

        UrlBean url = new UrlBean();
        url.setContent(contentUrl);

        return url;
    }

    @Required
    public void setBindValidator(ABindValidator validator) {
        this.validator = validator;
    }

}
