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
import com.multimedia.service.IRubricImageService;
import com.multimedia.service.IWallpaperService;
import common.utils.FileUtils;
import common.utils.ImageUtils;
import common.utils.image.BufferedImageHolder;
import core.model.beans.Pages;
import gallery.model.beans.Wallpaper;
import gallery.web.controller.pages.types.WallpaperGalleryType;
import gallery.web.support.wallpaper.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * @author demchuck.dima@gmail.com
 */
@Service("rubricImageService")
public class RubricImageCachingService implements IRubricImageService {
    private final Logger logger = LoggerFactory.getLogger(RubricImageCachingService.class);
    /**
     * path to folder where the cached images will be stored
     */
    private String path;
    /**
     * relative path in web application
     */
    @Value("${wallpaper.rubric.web.path}")
    private String webPath;
    /**
     * quantity of images that will be cached for each rubric
     */
    @Value("${wallpaper.rubric.image.quantity}")
    private int imageQuantity;
    /**
     * width of created images
     */
    @Value("${wallpaper.rubric.image.width}")
    private int imageWidth;
    /**
     * height of created images
     */
    @Value("${wallpaper.rubric.image.height}")
    private int imageHeight;

    @Autowired
    private IWallpaperService wallpaperService;
    @Autowired
    private IPagesService pagesService;

    private final Random randomGenerator = new Random();

    @Value("${wallpaper.rubric.dir}")
    public void setPath(Resource resource) throws IOException {
        this.path = FileUtils.createDirectory(resource);
    }

    public static void createFolders(File dst, List<? extends Pages> pages) {
        if (!dst.exists() || dst.isFile()) {
            dst.delete();
            dst.mkdir();
        }
        for (Pages p : pages) {
            File dstDir = new File(dst, p.getId().toString());
            if (!dstDir.exists()) {
                dstDir.mkdir();
            }
        }
    }

    /**
     * generates image_quantity images for current page the images are resized to image_width * image_height resolution
     *
     * @param p      page to take images for
     * @param dstDir dir where to save resulting images
     * @return false if any error occurs
     */
    protected boolean generateImages(Pages p, File dstDir) {
        boolean succeed = true;
        List<Long> ids = pagesService.getAllActiveChildrenId(p.getId());
        List<Wallpaper> wallpapers = wallpaperService.getMainImages(ids, imageQuantity);
        File srcDir = new File(wallpaperService.getStorePath(), Utils.FULL_DIMENSION_NAME);

        for (Wallpaper wallpaper : wallpapers) {
            File src = Utils.findFileFromResolution(srcDir, wallpaper);
            File dst = new File(dstDir, wallpaper.getName());
            if (src.exists()) {
                try {
                    BufferedImageHolder holder = ImageUtils.readImage(src);
                    BufferedImage rez = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
                    ImageUtils.getScaledImageDimension(holder.getImage(), rez);
                    ImageUtils.writeImage(rez, -1f, dst);
                } catch (IOException ex) {
                    logger.error("error while creating image for rubric; ", ex);
                    succeed = false;
                }
            } else {
                logger.error("error while creating image for rubric; " + p.getId() + "; " + p.getName() + "; " + src.getName());
                succeed = false;
            }
        }
        return succeed;
    }

    /**
     * generates image_quantity images for current page
     * image is made by drawing 4 images on one same images
     * the resulting images are in image_width * image_height resolution
     *
     * @param p      page to take images for
     * @param dstDir dir where to save resulting images
     * @return false if any error occurs
     */
    protected boolean generateImages4(Pages p, File dstDir) {
        boolean succeed = true;
        List<Long> ids = pagesService.getAllActiveChildrenId(p.getId());
        List<Wallpaper> wallpapers = wallpaperService.getMainImages(ids, 4);

        File srcDir = new File(wallpaperService.getStorePath(), Utils.FULL_DIMENSION_NAME);
        BufferedImage[] srcImg = new BufferedImage[wallpapers.size()];
        //creating src images
        for (int i = 0; i < wallpapers.size(); i++) {
            Wallpaper wallpaper = wallpapers.get(i);
            File src = new File(srcDir, wallpaper.getName());
            if (src.exists()) {
                try {
                    BufferedImageHolder holder = ImageUtils.readImage(src);
                    BufferedImage rez = new BufferedImage((int) (imageWidth / 1.65), (int) (imageHeight / 1.65), BufferedImage.TYPE_3BYTE_BGR);
                    ImageUtils.getScaledImageDimension(holder.getImage(), rez);
                    srcImg[i] = rez;
                } catch (IOException ex) {
                    logger.error("error while creating image for rubric; ", ex);
                    succeed = false;
                }
            } else {
                logger.error("error while creating image for rubric; " + p.getId() + "; " + p.getName() + "; " + src.getName());
                succeed = false;
            }
        }
        //drawing
        try {
            File dst = new File(dstDir, Long.toString(System.nanoTime()));
            BufferedImage rez = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
            ImageUtils.draw4on1(srcImg, rez);
            ImageUtils.writeImage(rez, 1f, dst);
        } catch (IOException ex) {
            logger.error("error while creating image for rubric; ", ex);
            succeed = false;
        }
        return succeed;
    }

    /**
     * generates an url for this page
     * if directory for this page exists -- try to get an url
     * if no directory -- generating new images
     *
     * @param p page
     */
    @Override
    public String getImageUrl(Pages p) {
        //TODO remake
        File dstDir = new File(path, p.getId().toString());
        if (!dstDir.exists() || dstDir.isFile()) {
            dstDir.delete();
            dstDir.mkdirs();
            generateImages(p, dstDir);
        }
        File[] files = dstDir.listFiles();
        if (files.length > 0) {
            int i = randomGenerator.nextInt(files.length);
            return webPath + p.getId() + '/' + files[i].getName();
        } else {
            logger.error("can't generate image url for rubric; " + p.getId() + p.getName());
            return null;
        }
    }

    /**
     * generates urls for pages in list
     *
     * @param list pages
     * @see RubricImageCachingService#getImageUrl
     */
    @Override
    public List<String> getImageUrls(List<? extends Pages> list) {
        List<String> rez = new LinkedList<String>();
        for (Pages p : list) {
            rez.add(getImageUrl(p));
        }
        return rez;
    }

    @Override
    public boolean clearImages(Long id) {
        File dir = new File(path);
        if (!dir.exists()) {
            return false;
        }
        boolean rez = true;
        if (id == null) {
            File[] files = dir.listFiles();
            for (File f : files) {
                rez = FileUtils.deleteFiles(f, true) & rez;
            }
        } else {
            File dst = new File(dir, id.toString());
            rez = FileUtils.deleteFiles(dst, true);
        }
        return rez;
    }

    private static final String[] PAGES_NAMES = {"id", "name", "type"};

    /**
     * 1-st creates folders for all pages that will be affected(if they do not have any folders already)
     * 2-nd for each page clear folder and generate images
     * so if folder has some images they will be deleted only before creating new images for it
     * if you want to clear all images first call 'clearImages' method for this class
     */
    @Override
    public boolean refreshImages(Long id) {
        boolean rez = true;
        File dstDir = new File(path);
        List<gallery.model.beans.Pages> pages;
        if (id == null) {
            pages = pagesService.getByPropertiesValueOrdered(PAGES_NAMES,
                    new String[]{"type", "active"}, new Object[]{WallpaperGalleryType.TYPE, Boolean.TRUE}, null, null);
        } else {
            pages = pagesService.getByPropertiesValueOrdered(PAGES_NAMES,
                    new String[]{"id", "active"}, new Object[]{id, Boolean.TRUE}, null, null);
        }
        createFolders(dstDir, pages);
        for (Pages p : pages) {
            dstDir = new File(path, p.getId().toString());
            FileUtils.deleteFiles(dstDir, false);
            rez = generateImages(p, dstDir) & rez;
        }
        return rez;
    }

}
