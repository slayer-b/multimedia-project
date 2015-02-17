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

import com.multimedia.service.IAutoreplaceService;
import com.multimedia.service.IPagesService;
import com.multimedia.service.IWallpaperService;
import common.utils.FileUtils;
import core.rss.RSSFeedGenerator;
import core.rss.RSSFeedGeneratorImpl;
import core.rss.elem.Channel;
import core.rss.elem.Enclosure;
import core.rss.elem.Image;
import core.rss.elem.Item;
import core.rss.elem.RSS;
import gallery.model.beans.Pages;
import gallery.model.beans.Wallpaper;
import gallery.web.support.wallpaper.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author demchuck.dima@gmail.com
 */
public class RssService {
    private final Logger logger = LoggerFactory.getLogger(RssService.class);

    @Autowired
    private IPagesService pagesService;
    @Autowired
    private IWallpaperService wallpaperService;
    @Autowired
    private IAutoreplaceService autoreplaceService;
    private RSSFeedGenerator rssFeedGenerator = new RSSFeedGeneratorImpl();
    private String path;
    private String path_tmp;
    private boolean generating = false;

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

    private static final String[] MAIN_PSEUDONYMES = {"id_pages", "active", "type"};
    private static final Object[] MAIN_VALUES = {null, Boolean.TRUE, "general_wallpaper_gallery"};

    private static final String[] CREATE_NAMES = {"id", "id_pages", "description", "title", "date_upload", "name", "folder"};
    private static final String[] CREATE_ORDER_BY = {"date_upload"};
    private static final String[] CREATE_ORDER_HOW = {"DESC"};

    @Transactional(readOnly = true)
    public void create() {
        if (generating) {
            logger.info("xml is already generating ...");
            return;
        }
        try {
            generating = true;
            logger.info("start generate xml");
            long time = System.currentTimeMillis();

            File imgDir = new File(wallpaperService.getStorePath(), Config.ENCLOSURE_IMG_SUBDIR);

            //get main wallpaper page
            Channel chan = new Channel();
            List<Pages> temp = pagesService.getByPropertiesValueOrdered(null, MAIN_PSEUDONYMES, MAIN_VALUES, null, null);
            if (temp.isEmpty()) {
                chan.setTitle(gallery.web.Config.SITE_NAME);
                chan.setDescription(gallery.web.Config.SITE_NAME);
                chan.setLink(gallery.web.Config.SITE_NAME);
            } else {
                //TODO localize it !!!
                IAutoreplaceService.IReplacement repl = autoreplaceService.getAllReplacements("ru");
                String title = repl.replaceAll(temp.get(0).getTitle());
                String description = repl.replaceAll(temp.get(0).getDescription());
                chan.setTitle(title);
                chan.setLink(gallery.web.Config.SITE_NAME);
                chan.setDescription(description);
            }

            RSS rss = new RSS();

            Image image = new Image();
            image.setUrl(gallery.web.Config.SITE_NAME + Config.LOGO_WEBDIR);
            image.setTitle(chan.getTitle());
            image.setLink(chan.getLink());

            chan.setImage(image);
            chan.setLastBuildDate(new Date());

            rss.addChannel(chan);
            List<Wallpaper> wallpapers =
                    wallpaperService.getByPropertyValuePortionOrdered(
                            CREATE_NAMES, CREATE_NAMES,
                            "active",
                            Boolean.TRUE,
                            0, 100,//TODO: externalize
                            CREATE_ORDER_BY, CREATE_ORDER_HOW);
            for (Wallpaper wallpaper : wallpapers) {
                try {
                    Item item = new Item();
                    item.setDescription(wallpaper.getDescription());
                    item.setLink(gallery.web.Config.SITE_NAME + "index.htm?id_pages_nav=" + wallpaper.getId_pages() + "&id_photo_nav=" + wallpaper.getId());
                    item.setTitle(wallpaper.getTitle());
                    item.setPubDate(wallpaper.getDate_upload());//date_upload
                    long fileLen = Utils.findFileFromResolution(imgDir, wallpaper).length();//name
                    if (fileLen > 0L) {
                        Enclosure enclosure = new Enclosure();
                        enclosure.setUrl(gallery.web.Config.SITE_NAME + Config.ENCLOSURE_IMG_WEBDIR + wallpaper.getFolderAndName());
                        enclosure.setLength(fileLen);
                        enclosure.setType("image/jpeg");
                        item.setEnclosure(enclosure);
                    }
                    chan.addItem(item);
                } catch (Exception e) {
                    logger.error("Error happened while creating rss feed for wallpaper_id = {}",
                            wallpaper.getId());
                }
            }
            File tmpFile = new File(path_tmp, Config.RSS_FILE_NAME);
            if (tmpFile.exists() && !tmpFile.delete()) {
                logger.error("Failed to delete tmp rss file.");
                return;
            }
            rssFeedGenerator.generateToFile(rss, tmpFile, "UTF-8");
            moveToRealFile(tmpFile);
            time = System.currentTimeMillis() - time;
            logger.info("end generate xml. generated in: {}", time);
        } catch (IOException e) {
            logger.error("Failed to create rss.", e);
        } finally {
            generating = false;
        }
    }

    private void moveToRealFile(File tmpFile) throws IOException {
        File realFile = new File(path, Config.RSS_FILE_NAME);
        if (realFile.exists() && !realFile.delete()) {
            logger.error("Failed to delete existing rss File.");
            return;
        }
        org.apache.commons.io.FileUtils.moveFile(tmpFile, realFile);
    }

    public static final class Config {
        /**
         * subdirectory of store path where to get images
         */
        public static final String ENCLOSURE_IMG_SUBDIR = "medium";
        /**
         * subdirectory of store path where to get images
         */
        public static final String ENCLOSURE_IMG_WEBDIR = "images/wallpaper/medium/";
        /**
         * web path to logo
         */
        public static final String LOGO_WEBDIR = "img/top/logo.jpg";
        /**
         * web path to logo
         */
        public static final String RSS_FILE_NAME = "rss.xml";

        private Config() {
        }
    }

}
