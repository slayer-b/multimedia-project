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

package gallery.web.validator.wallpaper.comment;

import com.multimedia.security.Utils;
import com.multimedia.security.model.User;
import com.multimedia.service.IWallpaperService;
import common.bind.CommonBindValidator;
import common.types.TypesCheckAndCorrect;
import gallery.model.beans.WallpaperComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class WallpaperCommentValidator extends CommonBindValidator {
    @Autowired
	private IWallpaperService wallpaperService;

    public WallpaperCommentValidator(TypesCheckAndCorrect tc) {
        super(tc);
    }

	@Override
	public BindingResult bindAndValidate(Object command, HttpServletRequest request) {
		BindingResult res = super.bindAndValidate(command, request);
		if (!res.hasErrors()) {
			WallpaperComment p = (WallpaperComment) command;
			//checking if there is such wallpaper
			if (wallpaperService.getRowCount("id", p.getId_photo())==1L) {
				p.setCreationTime(new Timestamp(System.currentTimeMillis()));
				//setting user
                User user = new User();
                user.setId(Utils.getCurrentUser().getId());
                p.setUser(user);
			} else {
				common.utils.CommonAttributes.addErrorMessage("not_exists.wallpaper", request);
				res.reject("not_exists.wallpaper");
			}
		}
		return res;
	}

}
