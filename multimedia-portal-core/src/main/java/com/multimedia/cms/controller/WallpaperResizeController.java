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

package com.multimedia.cms.controller;

import com.multimedia.exceptions.ResolutionNotFound;
import com.multimedia.exceptions.WallpaperNotFound;
import com.multimedia.service.IResolutionService;
import com.multimedia.service.IWallpaperService;
import common.services.IStaticsService;
import gallery.model.beans.Wallpaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author demchuck.dima@gmail.com
 */
@Controller("wallpaperResizeController")
@RequestMapping("/images/wallpaper/resolution/**")
public class WallpaperResizeController {
    private final Logger logger = LoggerFactory.getLogger(WallpaperResizeController.class);
    @Autowired
    private IResolutionService resolutionService;
    @Autowired
    private IWallpaperService wallpaperService;
    @Autowired
    private IStaticsService statisticService;

    private static final String[] RESOLUTION_WIDTH_HEIGHT = {"width", "height"};

    @RequestMapping(value = "/{resolutionX}x{resolutionY}/{wallpaper:.*}",
            method = RequestMethod.GET)
    @ResponseBody
    public Resource resizeWallpaper(
            @PathVariable("resolutionX") int resolutionX,
            @PathVariable("resolutionY") int resolutionY,
            @PathVariable("wallpaper") String wallpaperName,
            WebRequest request)
            throws IOException {
        checkResolutionExists(resolutionX, resolutionY);

        Wallpaper wallpaper = wallpaperService.findWallpaperFileByFolderAndName(wallpaperName, "");

        long lastModified = wallpaperService.getWallpaperLastModified(wallpaper, resolutionX, resolutionY);
        if (request.checkNotModified(lastModified)) {
            logger.debug("Resource [{}] not modified.", request.getDescription(false));
            return null;
        }

        statisticService.increaseStat(resolutionX + "x" + resolutionY + ":start_resize");
        File wallpaperFile = wallpaperService.getResizedWallpaper(wallpaper, resolutionX, resolutionY);
        statisticService.increaseStat(resolutionX + "x" + resolutionY + ":finish_resize");
        return new FileSystemResource(wallpaperFile);
    }

    @RequestMapping(value = "/{resolutionX}x{resolutionY}/{folder:.*}/{wallpaper:.*}",
            method = RequestMethod.GET)
    @ResponseBody
    public Resource folder(
            @PathVariable("resolutionX") int resolutionX,
            @PathVariable("resolutionY") int resolutionY,
            @PathVariable("folder") String folder,
            @PathVariable("wallpaper") String wallpaperName,
            WebRequest request)
            throws IOException  {
        checkResolutionExists(resolutionX, resolutionY);

        Wallpaper wallpaper = wallpaperService.findWallpaperFileByFolderAndName(wallpaperName, folder);

        long lastModified = wallpaperService.getWallpaperLastModified(wallpaper, resolutionX, resolutionY);
        if (request.checkNotModified(lastModified)) {
            logger.debug("Resource [{}] not modified.", request.getDescription(false));
            return null;
        }

        statisticService.increaseStat(resolutionX + "x" + resolutionY + ":start_resize");
        File wallpaperFile = wallpaperService.getResizedWallpaper(wallpaper, resolutionX, resolutionY);
        statisticService.increaseStat(resolutionX + "x" + resolutionY + ":finish_resize");
        return new FileSystemResource(wallpaperFile);
    }

    private void checkResolutionExists(int resolutionX, int resolutionY) {
        if (resolutionX < 0 ||
                resolutionY < 0 ||
                resolutionService.getRowCount(RESOLUTION_WIDTH_HEIGHT, new Object[]{resolutionX, resolutionY}) != 1L) {
            throw new ResolutionNotFound(resolutionX, resolutionY);
        }
    }

    @ExceptionHandler
    public HttpEntity<String> handleError(IOException ex) {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.add("Content-Type", "text/html;charset=UTF-8");
        return new ResponseEntity<String>(ex.getMessage(), map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WallpaperNotFound.class, ResolutionNotFound.class})
    public HttpEntity<String> resourceNotFound(RuntimeException ex) {
        //I added this to remove "image/jpg" header that was set by an original request handler.
        MultiValueMap<String, String> map = new HttpHeaders();
        map.add("Content-Type", "text/html;charset=UTF-8");
        return new ResponseEntity<String>(ex.getMessage(), map, HttpStatus.NOT_FOUND);
    }

}