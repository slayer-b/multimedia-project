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

package com.multimedia.service.impl;

import com.multimedia.service.IPagesService;
import com.multimedia.service.IWallpaperService;
import common.utils.FileUtils;
import core.sitemap.model.Index;
import core.sitemap.model.Sitemap;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.types.WallpaperGalleryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author demchuck.dima@gmail.com
 */
public class SitemapService {
    private final Logger logger = LoggerFactory.getLogger(SitemapService.class);

    @Autowired
    private IPagesService pages_service;
    @Autowired
    private IWallpaperService wallpaper_service;

    private String path;
    private String path_tmp;

    private boolean generating;

    protected static final String[] SITEMAP_NAMES = {"id", "last", "type"};
    protected static final String[] SITEMAP_WHERE = {"id_pages", "active"};
    private static final int MAX_SITEMAP_INDEX_SIZE = 10000;


    @Transactional(readOnly = true)
    public void createSitemap() {
        if (generating) {
            logger.info("xml is already generating ...");
            return;
        }
        try {
            logger.info("start generate xml");
            generating = true;
            long time = System.currentTimeMillis();
            File base = new File(path_tmp);
            try {
                if (base.exists()) {
                    Index index = new Index(MAX_SITEMAP_INDEX_SIZE, base, gallery.web.Config.SITE_NAME);
                    Sitemap sitemap = index.getChild();

                    List<Pages> pages = pages_service.getByPropertiesValueOrdered(SITEMAP_NAMES, SITEMAP_WHERE, new Object[]{null, Boolean.TRUE}, null, null);
                    Deque<Long> pagesUnhandled = new LinkedList<Long>();
                    int k = 0;
                    while (k < pages.size()) {
                        Pages p = pages.get(k);
                        if (!p.getLast()) {
                            pagesUnhandled.add(p.getId());
                            sitemap.addRecord("index.htm?id_pages_nav=" + p.getId(), "daily", "0.8");
                        } else {
                            //check type
                            if (p.getType().equals(WallpaperGalleryType.TYPE)) {
                                Long id_pages = p.getId();
                                List<Long> ids = wallpaper_service.getSingleProperty("id", SITEMAP_WHERE, new Object[]{id_pages, Boolean.TRUE}, 0, 0, null, null);
                                int j = 0;
                                for (int i = 0; i < ids.size(); i = i + WallpaperGalleryType.CATEGORY_WALLPAPERS) {
                                    sitemap.addRecord("index.htm?id_pages_nav=" + id_pages + "&amp;page_number=" + j, "daily", "0.9");
                                    j++;
                                }
                                for (Object id_wallpaper : ids) {
                                    sitemap.addRecord("index.htm?id_pages_nav=" + id_pages + "&amp;id_photo_nav=" + id_wallpaper, "daily", "0.7");
                                }
                            } else {
                                sitemap.addRecord("index.htm?id_pages_nav=" + p.getId(), "daily", "0.9");
                            }
                        }
                        k++;
                        if ((k >= pages.size()) && (!pagesUnhandled.isEmpty())) {
                            pages = pages_service.getByPropertiesValueOrdered(SITEMAP_NAMES,
                                    SITEMAP_WHERE, new Object[]{pagesUnhandled.removeFirst(), Boolean.TRUE},
                                    null, null);
                            k = 0;
                        }
                    }
                    sitemap.close();
                    //moving to web content
                    clearSitemap();
                    File f = new File(path_tmp);
                    File[] files = f.listFiles();
                    for (File file : files) {
                        String name = file.getName();
                        if ((name.startsWith(core.sitemap.model.Config.SITEMAP_PREFIX) || name.startsWith(core.sitemap.model.Config.INDEX_PREFIX)) && name.endsWith(core.sitemap.model.Config.XML_SUFFIX)) {
                            file.renameTo(new File(path, file.getName()));
                        }
                    }
                }
            } catch (IOException ex) {
                logger.error("error while generating sitemap", ex);
            }
            time = System.currentTimeMillis() - time;
            logger.info("end generate xml. generated in: " + time);
        } finally {
            generating = false;
        }
    }

    public void clearSitemap() {
        File f = new File(path);
        File[] files = f.listFiles();
        for (File file : files) {
            String name = file.getName();
            if ((name.startsWith(core.sitemap.model.Config.SITEMAP_PREFIX) || name.startsWith(core.sitemap.model.Config.INDEX_PREFIX)) && name.endsWith(core.sitemap.model.Config.XML_SUFFIX)) {
                file.delete();
            }
        }
    }

    @Value("/")
    public void setPath(Resource res) throws IOException {
        path = FileUtils.createDirectory(res);
        logger.info("path = " + path);
    }

    @Value("WEB-INF/tmp/")
    public void setPath_tmp(Resource res) throws IOException {
        path_tmp = FileUtils.createDirectory(res);
        logger.info("path_tmp = " + path_tmp);
    }
}
