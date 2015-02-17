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

package com.multimedia.cms.wallpaper;

import com.multimedia.cms.service.CmsWallpaperService;
import com.multimedia.security.model.User;
import gallery.model.beans.Wallpaper;
import gallery.model.misc.StatusBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: slayer
 * Date: 28.06.12
 */
public class WallpaperUploader {
    private final List<Wallpaper> wallpapers;
    private final List<Wallpaper> preUploadedWallpapers;
    private final List<File> preUploadFolders;
    private final User user;
    private final Long defaultPageId;
    private Set<String> dimensionsSet;
    private final StatusBean statusBean;
    private final int batchSize;


    public WallpaperUploader(int batchSize, User user, Long defaultPageId, StatusBean statusBean,
                             Set<String> dimensionsSet) {
        this.user = user;
        this.defaultPageId = defaultPageId;
        this.dimensionsSet = dimensionsSet;
        this.wallpapers =  new ArrayList<Wallpaper>();
        this.preUploadedWallpapers =  new ArrayList<Wallpaper>();
        this.preUploadFolders = new ArrayList<File>();
        this.statusBean = statusBean;
        this.batchSize = batchSize;
    }

    public Wallpaper wallpaper(Boolean active, Long id_pages, File content) {
        Wallpaper wallpaper = initWallpaper(active, id_pages, content);
        wallpapers.add(wallpaper);
        return wallpaper;
    }

    public Wallpaper preUploadedWallpaper(Boolean active, Long id_pages, File content, File folder) {
        Wallpaper wallpaper = initWallpaper(active, id_pages, content);
        preUploadedWallpapers.add(wallpaper);
        preUploadFolders.add(folder);
        return wallpaper;
    }

    private Wallpaper initWallpaper(Boolean active, Long id_pages, File content) {
        Wallpaper wallpaper = new Wallpaper();
        wallpaper.setActive(active);
        if (id_pages == null) {
            wallpaper.setId_pages(defaultPageId);
        } else {
            wallpaper.setId_pages(id_pages);
        }
        wallpaper.setContent_file(content);
        wallpaper.setUser(user);
        return wallpaper;
    }

    public void upload(CmsWallpaperService service) {
        if (batchSize <= wallpapers.size() + preUploadedWallpapers.size()) {
            doUpload(service);
        }
    }

    public void finish(CmsWallpaperService service) {
        doUpload(service);
    }

    private void doUpload(CmsWallpaperService service) {
        //This is okey because
        // 1-st) usually we will upload normal or pre uploaded wallpapers not mixed
        // 2-nd) even if not nothing bad will happen
        if (wallpapers.size() > 0 && service.insert(wallpapers, null) == wallpapers.size()) {
            cleanupWallpapers();
        }

        if (preUploadedWallpapers.size() > 0 && service.insert(preUploadedWallpapers, preUploadFolders) == preUploadedWallpapers.size()) {
            cleanupPreUploadedWallpapers();
        }

        prepareNewUpload();
    }

    private void cleanupWallpapers() {
        for (Wallpaper wallpaper : wallpapers) {
            if (!wallpaper.getContent_file().delete()) {
                wallpaper.getContent_file().deleteOnExit();
            }
        }
        statusBean.increaseDone(wallpapers.size());
    }

    private void cleanupPreUploadedWallpapers() {
        for (int i = 0; i < preUploadedWallpapers.size(); i++) {
            File folder = preUploadFolders.get(i);
            File file = preUploadedWallpapers.get(i).getContent_file();
            for (String dimension : dimensionsSet) {
                File preUploadedImage = new File(folder, dimension + File.separator + file.getName());
                if (!preUploadedImage.delete()) {
                    preUploadedImage.deleteOnExit();
                }
            }
        }
        statusBean.increaseDone(preUploadedWallpapers.size());
    }

    private void prepareNewUpload() {
        wallpapers.clear();
        preUploadedWallpapers.clear();
        preUploadFolders.clear();
    }
}
