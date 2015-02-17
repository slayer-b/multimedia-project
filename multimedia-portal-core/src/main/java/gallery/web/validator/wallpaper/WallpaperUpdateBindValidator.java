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
import com.multimedia.security.dto.MyUser;
import com.multimedia.service.IWallpaperService;
import common.bind.CommonBindValidator;
import common.types.TypesCheckAndCorrect;
import common.utils.CommonAttributes;
import gallery.model.beans.Wallpaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * prevents user from updating items of other user
 * @author demchuck.dima@gmail.com
 */
public class WallpaperUpdateBindValidator extends CommonBindValidator {
    @Autowired
	private IWallpaperService wallpaperService;

    public WallpaperUpdateBindValidator(TypesCheckAndCorrect tc) {
        super(tc);
    }

    private static final String[] WHERE_CONDITION = {"id","id_users"};
	@Override
	protected void validate(Object command, Errors err, HttpServletRequest request) {
		super.validate(command, err, request);
		if (!err.hasErrors()){
			Wallpaper p = (Wallpaper) command;
			p.setDate_upload(new Date());
			validate(p.getId(), err, request, wallpaperService);
		}
	}

	/**
	 * validates a wallpaper with given id
	 * @param id_wallpaper wallpaper's id
	 * @param err object with errors
	 * @param request http request
	 * @param wallpaperService service
	 * @return false if another user
	 */
	public static boolean validate(Long id_wallpaper, Errors err, HttpServletRequest request, IWallpaperService wallpaperService) {
		MyUser u = Utils.getCurrentUser();
		if (wallpaperService.getRowCount(WHERE_CONDITION, new Object[]{id_wallpaper,u.getId()})!=1L) {
			if (err != null) {
			    err.reject("another_user");
			}
			CommonAttributes.addErrorMessage("another_user", request);
			return false;
		} else {
			return true;
		}
	}

}
