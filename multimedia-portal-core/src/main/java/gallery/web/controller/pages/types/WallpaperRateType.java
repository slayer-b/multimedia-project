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

import com.multimedia.core.pages.types.IPagesType;
import com.multimedia.dao.WallpaperRatingRepository;
import com.multimedia.service.IWallpaperService;
import common.utils.CommonAttributes;
import gallery.model.beans.WallpaperRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author demchuck.dima@gmail.com
 */
public class WallpaperRateType implements IPagesType {
    /**
     * string constant that represents type for this page
     */
    public static final String TYPE = "system_rate_wallpaper";
    /**
     * rus type
     */
    public static final String TYPE_RU = "---Оценка фото---";

    public static final Long MAX_RATE = 10L;
    public static final Long MIN_RATE = 0L;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTypeRu() {
        return TYPE_RU;
    }

    @Autowired
    private WallpaperRatingRepository wallpaperRatingRepository;
    @Autowired
    private IWallpaperService wallpaperService;

    private static final String[] REQUIRED_FIELDS = {"id_photo", "rate"};

    @Override
    public UrlBean execute(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws IOException {
        /** bind command */
        WallpaperRating command = new WallpaperRating();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command);
        binder.setRequiredFields(REQUIRED_FIELDS);

        binder.bind(request);
        BindingResult res = binder.getBindingResult();

        char error = '1';
        if (res.hasErrors()) {
            CommonAttributes.addErrorMessage("form_errors", request);
        } else {
            //correcting rating
            WallpaperRateType.correctRate(command);

            command.setIp(request.getRemoteAddr());
            if (wallpaperService.getRowCount("id", command.getId_photo()) == 1L) {
                if (wallpaperRatingRepository.countByIdPhotoAndIp(command.getId_photo(), command.getIp()) > 0L) {
                    CommonAttributes.addErrorMessage("duplicate_ip", request);
                } else {
                    wallpaperRatingRepository.save(command);
                    CommonAttributes.addHelpMessage("operation_succeed", request);
                    error = '0';
                }
            } else {
                CommonAttributes.addErrorMessage("not_exists.wallpaper", request);
            }
        }
        OutputStream os = response.getOutputStream();
        os.write(error);
        os.flush();

        return null;
    }

    private static void correctRate(WallpaperRating pr){
        if (pr.getRate()==null||pr.getRate()<0d) {
            pr.setRate(MIN_RATE);
        } else if (pr.getRate()>MAX_RATE) {
            pr.setRate(MAX_RATE);
        }
    }
}
