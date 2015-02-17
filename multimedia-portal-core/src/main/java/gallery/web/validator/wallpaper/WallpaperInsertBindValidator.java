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

package gallery.web.validator.wallpaper;

import com.multimedia.security.Utils;
import com.multimedia.security.model.User;
import common.types.TypesCheckAndCorrect;
import gallery.model.beans.Wallpaper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;


/**
 * @author demchuck.dima@gmail.com
 */
public class WallpaperInsertBindValidator extends common.bind.CommonBindValidator {
    private final Logger logger = LoggerFactory.getLogger(WallpaperInsertBindValidator.class);

    public WallpaperInsertBindValidator(TypesCheckAndCorrect tc) {
        super(tc);
    }

    @Override
    public BindingResult bindAndValidate(Object command, HttpServletRequest request) {
        BindingResult res = super.bindAndValidate(command, request);
        if (!res.hasErrors()) {
            //setting user
            Wallpaper p = (Wallpaper) command;
            p.setActive(Boolean.TRUE);
            User user = new User();
            user.setId(Utils.getCurrentUser().getId());
            p.setUser(user);
            logger.debug("User [{}] uploads wallpaper.", user.getId());
        }
        return res;
    }

}
