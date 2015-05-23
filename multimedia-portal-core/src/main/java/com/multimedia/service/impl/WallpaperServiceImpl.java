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

import com.multimedia.dao.WallpaperRepository;
import com.multimedia.exceptions.WallpaperNotFound;
import com.multimedia.service.IResolutionService;
import com.multimedia.service.IWallpaperService;
import common.beans.MyPageable;
import common.services.generic.GenericServiceImpl;
import common.utils.FileUtils;
import common.utils.ImageUtils;
import gallery.model.beans.Resolution;
import gallery.model.beans.Wallpaper;
import gallery.web.support.wallpaper.Utils;
import org.hibernate.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * @author demchuck.dima@gmail.com
 */
public class WallpaperServiceImpl extends GenericServiceImpl<Wallpaper, Long> implements IWallpaperService {
    private final Logger logger = LoggerFactory.getLogger(WallpaperServiceImpl.class);
    /**
     * specifies the path to folder with wallpapers within server
     */
    private final String path;
    /**
     * path where resized photos will be stored (i.e. all resolutions from database)
     */
    private final String resized_path;
    /**
     * specifies the path to folder where to backup wallpapers
     */
    private final String backup_path;
    /**
     * specifies the path to folder where to get wallpapers for multi upload wallpapers
     */
    private final String upload_path;
    /**
     * are paths to folders with resized images i.e. small or medium
     */
    private final Map<String, Integer> dimensions;
    /**
     * forbidden words for tag cloud. tags with this words will not be added to the tag cloud
     */
    private final Set<String> blackWordList;

    private final WallpaperRepository wallpaperRepository;
    private final IResolutionService resolutionService;

    public WallpaperServiceImpl(
            WallpaperRepository wallpaperRepository,
            IResolutionService resolutionService,
            Resource imageFolder,
            Resource imageResizedFolder,
            Resource backupFolder,
            Resource uploadFolder,
            Set<String> blackWordList,
            Map<String, Integer> dimensions
    ) {
        super(wallpaperRepository);
        this.wallpaperRepository = wallpaperRepository;
        this.resolutionService = resolutionService;
        this.path = FileUtils.createDirectory(imageFolder, "Image folder");
        this.resized_path = FileUtils.createDirectory(imageResizedFolder, "Image resized folder");
        this.backup_path = FileUtils.createDirectory(backupFolder, "Backup folder");
        this.upload_path = FileUtils.createDirectory(uploadFolder, "Upload folder");
        this.blackWordList = blackWordList;
        this.dimensions = dimensions;
    }
//--------------------------------------------------------------------

    @PostConstruct
    public void init() {
        Set<Entry<String, Integer>> entrySet = dimensions.entrySet();
        for (Entry<String, Integer> e : entrySet) {
            File dstSubFolder = new File(path + File.separator + e.getKey());
            if (!dstSubFolder.exists() && !dstSubFolder.mkdir()) {
                logger.error("Failed to create dir for wallpapers [{}}]",
                        dstSubFolder.getAbsolutePath());
            }
        }
    }

    private static final String[] ORDER_BY = {"id"};
    private static final String[] ORDER_HOW = {"ASC"};
    private static final Sort SORT = new Sort(Sort.Direction.ASC, "id");
    private static final String[] ORDER_HOW_REVERSE = {"DESC"};

    static final String IMAGE_EXTENSION = ".jpg";

    private static final int MAX_DIRECTORY_SIZE = 1000;

    public String defaultFolderName() {
        File fullFolder = new File(path, Utils.FULL_DIMENSION_NAME);
        return findLastNonFullFolder(fullFolder);
    }

    private int lastFolderNumber = 0;
    private synchronized String findLastNonFullFolder(File rootFolder) {
        String[] content = (new File(rootFolder, Integer.toString(lastFolderNumber))).list();
        while (content == null || content.length > MAX_DIRECTORY_SIZE) {
            lastFolderNumber += 1;
            File nextFolder = new File(rootFolder, Integer.toString(lastFolderNumber));
            if (nextFolder.exists() || nextFolder.mkdir()) {
                content = nextFolder.list();
            }
        }
        return Integer.toString(lastFolderNumber);
    }

    /** supports only one level folder, i.e no nested folders */
    public void normalizeFolder(Wallpaper wallpaper) {
        if (wallpaper.getFolder() != null) {
            String folder = FileUtils.toTranslit(wallpaper.getFolder());
            wallpaper.setFolder(folder.isEmpty()? null: folder);
        }
    }

    private String normalizeName(String name) {
        //get extension and remove it if any
        int pos = name.lastIndexOf('.');
        String newName;
        if (pos > 0) {
            newName = name.substring(0, pos);
        } else {
            newName = name;
        }

        return FileUtils.checkFileNameSpelling(newName);
    }

    /**
     * creates an unique name for a wallpaper
     * i.e. name that is not currently in use,
     * contains lowercase latin letters and some spec symbols,
     * and ends with .jpg
     * @param otherNames additional names marked as used or invalid
     */
    private String getUniqueName(Long id, String oldName, Set<String> otherNames) {
        String newName = normalizeName(oldName);
        String candidate = newName + IMAGE_EXTENSION;

        //making name unique by appending numerical suffix
        int i = 0;
        while (otherNames.contains(candidate) ||
                wallpaperRepository.findCountByNameAndNotId(id, candidate) > 0L) {
            candidate = newName + '_' + i + IMAGE_EXTENSION;
            i++;
        }
        return candidate;
    }

    private boolean isContentInvalid(Wallpaper p) {
        return p.getContent() == null && p.getContent_file() == null;
    }

    private void initImage(Wallpaper p, String origFileName, Set<String> otherNames) {
        assert (origFileName != null);
        p.setName(getUniqueName(p.getId(), origFileName, otherNames));
        p.setDate_upload(new Timestamp(System.currentTimeMillis()));
    }

    private boolean initDimension(Wallpaper p, Dimension d) {
        if (d != null) {
            //set resolution
            p.setWidth(d.width);
            p.setHeight(d.height);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Always non-null.
     */
    private String findWallpaperName(Wallpaper wallpaper) {
        if (wallpaper.getName() != null && !wallpaper.getName().isEmpty()) {
            return wallpaper.getName();
        } else if (wallpaper.getContent() != null) {
            return wallpaper.getContent().getOriginalFilename();
        } else if (wallpaper.getContent_file() != null) {
            return wallpaper.getContent_file().getAbsolutePath();
        } else {
            throw new IllegalArgumentException("Cannot find name for wallpaper");
        }
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public boolean getImage(Wallpaper wallpaper) {
        if (isContentInvalid(wallpaper)) {
            return false;
        }
        //1-st generating an unique name
        String origFileName = findWallpaperName(wallpaper);
        normalizeFolder(wallpaper);
        initImage(wallpaper, origFileName, Collections.<String>emptySet());
        //2-nd creating file
        Dimension d = gallery.web.support.wallpaper.
                Utils.saveScaledWallpaperFileToDisk(wallpaper, dimensions, path, null, origFileName);
        return initDimension(wallpaper, d);
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public boolean getImages(List<Wallpaper> wallpapers, List<File> folders) {
        Set<String> otherNames = new HashSet<String>(wallpapers.size());
        for (int i = 0; i < wallpapers.size(); i++) {
            Wallpaper wallpaper = wallpapers.get(i);
            if (isContentInvalid(wallpaper)) {
                return false;
            }
            File folder = folders==null? null: folders.get(i);
            //1-st generating an unique name
            String origFileName = findWallpaperName(wallpaper);
            normalizeFolder(wallpaper);
            initImage(wallpaper, findWallpaperName(wallpaper), otherNames);
            otherNames.add(wallpaper.getName());
            //2-nd creating file
            Dimension d = gallery.web.support.wallpaper.
                    Utils.saveScaledWallpaperFileToDisk(wallpaper, dimensions, path, folder, origFileName);
            if (!initDimension(wallpaper, d)) {
                //TODO: delete all resized images
                return false;
            }
        }
        return true;
    }

    static final String[] WALLPAPERS_WHERE = {"id_pages", "active"};

    /**
     * get random wallpapers from given list of pages
     *
     * @param id_pages list of pages where to take wallpapers from
     * @param count    quantity of returned wallpapers
     * @return list of random wallpapers
     */
    @Override
    public List<Wallpaper> getMainImages(List<Long> id_pages, int count) {
        Object[][] values = {null, new Object[]{Boolean.TRUE}};

        Long[] id_pages_a = new Long[id_pages.size()];
        values[0] = id_pages.toArray(id_pages_a);
        int size = dao.getRowCount(WALLPAPERS_WHERE, values).intValue();

        Random r = new Random();
        Set<Integer> generated = new HashSet<Integer>(count + 1);
        //generating list of unique values from 0 to count
        if (size > count) {
            List<Wallpaper> rez = new LinkedList<Wallpaper>();
            for (int i = 0; i < count; i++) {
                Integer num = r.nextInt(size);
                while (generated.contains(num)) {
                    num = r.nextInt(size);
                }
                generated.add(num);
                List<Wallpaper> tempWallpaper = dao.getByPropertiesValuesPortionOrdered(null, null, WALLPAPERS_WHERE, values, num, 1, null, null);
                rez.add(tempWallpaper.get(0));
            }
            return rez;
        } else {
            return dao.getByPropertiesValuesPortionOrdered(null, null, WALLPAPERS_WHERE, values, 0, -1, null, null);
        }
    }

    static final String[] WALLPAPERS_PAGINATED_WHERE = {"id_pages", "active"};

    @Override
    public List<Wallpaper> getWallpapersPaginated(int first_num, int quantity, Long id_pages) {
        return wallpaperRepository.findById_pagesAndActive(id_pages, Boolean.TRUE,
                new MyPageable(first_num, quantity, SORT));
    }

    @Override
    public List<Wallpaper> getWallpapersPaginated(int first_num, int quantity, Long[] id_pages) {
        return dao.getByPropertiesValuePortionOrdered(null, null, WALLPAPERS_PAGINATED_WHERE, new Object[][]{id_pages, new Object[]{Boolean.TRUE}}, first_num, quantity, ORDER_BY, ORDER_HOW);
    }

    static final String[] RELATIONS_ASC = {"=", "=", ">"};
    static final String[] RELATIONS_DESC = {"=", "=", "<"};
    static final String[] WALLPAPERS_PAGINATED_RELATIONS_WHERE = {"id_pages", "active", "id"};

    @Override
    public List<Wallpaper> getWallpapersPaginatedId(Long id, int quantity, Long id_pages) {
        if (quantity > 0) {
            return dao.getByPropertiesValuePortionOrdered(null, null, WALLPAPERS_PAGINATED_RELATIONS_WHERE, new Object[]{id_pages, Boolean.TRUE, id}, RELATIONS_ASC, 0, quantity, ORDER_BY, ORDER_HOW);
        } else {
            return dao.getByPropertiesValuePortionOrdered(null, null, WALLPAPERS_PAGINATED_RELATIONS_WHERE, new Object[]{id_pages, Boolean.TRUE, id}, RELATIONS_DESC, 0, -quantity, ORDER_BY, ORDER_HOW_REVERSE);
        }
    }

    @Override
    public Long getWallpapersRowCount(Long id_pages) {
        return dao.getRowCount(WALLPAPERS_PAGINATED_WHERE, new Object[]{id_pages, Boolean.TRUE});
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public boolean getResizedWallpaperStream(Long id_wallpaper, Integer new_width, Integer new_height, OutputStream os)
            throws IOException {
        List<Wallpaper> wallpapers = dao.getByPropertyValues(ID_NAME_FOLDER, "id", new Long[]{id_wallpaper});
        if (!wallpapers.isEmpty()) {
            Wallpaper wallpaper = wallpapers.get(0);
            File src = Utils.findFileFromBasePath(path, Utils.FULL_DIMENSION_NAME, wallpaper);
            if (src.exists()) {
                ImageUtils.getScaledImageDimension(src, new_width, new_height, os);
                return true;
            }
        }
        return false;
    }

    @Override
    public Wallpaper findWallpaperFileByFolderAndName(String name, String folder) {
        List<Wallpaper> wallpapers = dao.getByPropertiesValuePortionOrdered(
                ID_NAME_FOLDER, ID_NAME_FOLDER, NAME_FOLDER, new String[]{name, folder}, 0, 1, null, null);
        if (wallpapers.isEmpty()) {
            throw new WallpaperNotFound(name, folder);
        }
        return wallpapers.get(0);
    }

    @Override
    public List<Wallpaper> findByFolder(String folder, int quantity) {
        return dao.getByPropertyValuePortionOrdered(
                ID_NAME_FOLDER, ID_NAME_FOLDER, "folder", folder, 0, quantity, null, null);
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public File getResizedWallpaper(Wallpaper wallpaper, int newWidth, int newHeight)
            throws IOException {
        File resolutionDir = getResolutionDir(newWidth, newHeight);
        File cachedFile = Utils.findFileFromResolution(resolutionDir, wallpaper);
        if (cachedFile.exists()) {
            logger.debug("Wallpaper [{}] found.", cachedFile.getAbsolutePath());
            return cachedFile;
        } else {
            File src = Utils.findFileFromBasePath(path, Utils.FULL_DIMENSION_NAME, wallpaper);
            File parent = cachedFile.getParentFile();
            if (src.exists() &&
                    (parent.exists() || parent.mkdirs()) &&
                    cachedFile.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(cachedFile);
                ImageUtils.getScaledImageDimension(src, newWidth, newHeight, fos);
                fos.flush();
                fos.close();
                logger.debug("Wallpaper [{}] resized.", cachedFile.getAbsolutePath());
                return cachedFile;
            }
        }
        throw new IOException("Failed to create files for wallpaper.");
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public long getWallpaperLastModified(Wallpaper wallpaper, int newWidth, int newHeight) {
        File file = Utils.findFileFromResolution(getResolutionDir(newWidth, newHeight), wallpaper);
        if (file.exists()) {
            logger.debug("Wallpaper [{}] found.", file.getAbsolutePath());
            return file.lastModified();
        } else {
            logger.debug("Wallpaper [{}] not found.", file.getAbsolutePath());
            return -1L;
        }
    }

    File getResolutionDir(int width, int height) {
        return new File(resized_path, width + "x" + height);
    }

    static final String[] ID_NAME_FOLDER = {"id", "name", "folder"};
    static final String[] NAME_FOLDER = {"name", "folder"};
    @Override
    public int deleteById(Long[] id) {
        if (id != null) {
            int count = 0;
            List<Wallpaper> wallpapers = dao.getByPropertyValues(ID_NAME_FOLDER, "id", id);
            for (int i = 0; i < wallpapers.size(); i++) {
                deleteFiles(wallpapers.get(i));
                dao.deleteById(id[i]);
                count++;
            }
            return count;
        } else {
            return -1;
        }
    }

    @Override
    public int deleteById(Long id) {
        Wallpaper p = dao.getById(id);
        if (p != null && deleteFiles(p)) {
            return dao.deleteById(id);
        } else {
            return -1;
        }
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public boolean deleteFiles(Wallpaper wallpaper) {
        List<Resolution> resolutions = resolutionService.getOrdered(null, null, null);
        //TODO: cache this array somewhere
        List<File> tmp = new ArrayList<File>(resolutions.size());
        for (Resolution res : resolutions) {
            tmp.add(new File(resized_path, res.getWidth() + "x" + res.getHeight()));
        }
        return gallery.web.support.wallpaper.Utils
                .deleteWallpaper(getById(wallpaper.getId()), dimensions, path, tmp);
    }

    //TODO: mb put this method inside of save / update operations
    @Override
    public boolean renameFiles(Wallpaper wallpaper) {
        if (wallpaper.getName() == null && wallpaper.getFolder() == null) {
            return true;//nothing to be renamed
        }
        Wallpaper old = getById(wallpaper.getId());
        return renameFiles(wallpaper, old);
    }

    @Override
    public boolean renameFiles(Wallpaper wallpaper, Wallpaper old) {
        if (wallpaper.getName() == null && wallpaper.getFolder() == null) {
            return true;//nothing to be renamed
        }
        if (wallpaper.getName() == null) {
            wallpaper.setName(old.getName());
        } else {
            wallpaper.setName(normalizeName(wallpaper.getName()) + IMAGE_EXTENSION);
        }
        if (wallpaper.getFolder() == null) {
            wallpaper.setFolder(old.getFolder());
        } else {
            normalizeFolder(wallpaper);
        }
        if (old.getName().equals(wallpaper.getName()) && old.getFolder().equals(wallpaper.getFolder())) {
            return true;//nothing to be renamed
        }
        wallpaper.setName(getUniqueName(wallpaper.getId(), wallpaper.getName(), Collections.<String>emptySet()));
        List<Resolution> resolutions = resolutionService.getOrdered(null, null, null);
        //TODO: cache this array somewhere
        List<File> tmp = new ArrayList<File>(resolutions.size());
        for (Resolution res : resolutions) {
            tmp.add(getResolutionDir(res.getWidth(), res.getHeight()));
        }
        return gallery.web.support.wallpaper.Utils.renameWallpaper(wallpaper, old, dimensions, path, tmp);
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public List<Wallpaper> backupWallpapers(List<Wallpaper> wallpapers, boolean append, boolean onlyFiles) {
        if (!onlyFiles) {
            throw new UnsupportedOperationException("not supported yet");
        }
        return Utils.copyWallpaper(wallpapers, dimensions, path, backup_path, append, true);
    }

    //TODO: refactor, include wallpaper folder
    @Override
    public List<Wallpaper> restoreWallpapers(List<Wallpaper> wallpapers, boolean append, boolean onlyFiles) {
        if (!onlyFiles) {
            throw new UnsupportedOperationException("not supported yet");
        }
        return Utils.copyWallpaper(wallpapers, dimensions, backup_path, path, append, true);
    }

    @Override
    public String getUploadPath() {
        return upload_path;
    }

    @Override
    public String getStorePath() {
        return path;
    }

    @Override
    public Map<String, Integer> getDimmensions() {
        return dimensions;
    }

    @Override
    public Long getWallpaperNumber(Wallpaper p) {
        //1-st get values for sort
        Object[] obj = new Object[ORDER_BY.length];
        Map<String, Object> m = Wallpaper.toMap(p);
        for (int i = 0; i < ORDER_BY.length; i++) {
            obj[i] = m.get(ORDER_BY[i]);
        }
        return (Long) dao.getRowNumber(obj, ORDER_BY, ORDER_HOW, WALLPAPERS_PAGINATED_WHERE, new Object[]{p.getId_pages(), Boolean.TRUE});
    }

    static final String[] RANDOM_WALLPAPERS_PROPERTIES = {"id", "id_pages", "name", "folder", "title"};

    @Override
    public List<Wallpaper> getRandomWallpapers(int quantity) {
        List ids = dao.getSinglePropertyOrderRand("id", "active", Boolean.TRUE, 0, quantity);
        return dao.getByPropertyValues(RANDOM_WALLPAPERS_PROPERTIES, "id", ids);
    }

    @Override
    public Map<String, Double> getTags(int maxTags) {
        logger.debug("Generating tag cloud");
        Map<String, Double> tags = wallpaperRepository.getWallpaperTags();
        //removing black words
        for (String blackWord : blackWordList) {
            tags.remove(blackWord);
        }
        if (logger.isTraceEnabled()) {
            for (String blackWord : blackWordList) {
                logger.trace("removing word [{}]", blackWord);
            }
            logger.trace("tags int cloud: {}", tags.keySet());
        }
        //keeping only maxTags quantity
        Set<Entry<String, Double>> i = tags.entrySet();
        List<Entry<String, Double>> l = new LinkedList<Entry<String, Double>>(i);
        java.util.Collections.sort(l, new EntryComparatorDesc());

        if (maxTags > 0) {
            for (int j = maxTags; j < l.size(); j++) {
                tags.remove(l.get(j).getKey());
            }
        }
        return tags;
    }

    /**
     * Compares two tags by score in descending order
     */
    public static class EntryComparatorDesc implements Comparator<Entry<String, Double>>, Serializable {

        private static final long serialVersionUID = 4303508142930260817L;

        @Override
        public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
            int scoreComparison = Double.compare(o2.getValue(), o1.getValue());

            // if the score is the same sort by name
            if (scoreComparison == 0) {
                return String.CASE_INSENSITIVE_ORDER.compare(o2.getKey(), o1.getKey());
            } else {
                return scoreComparison;
            }
        }

    }

    @Override
    public void enableResolutionFilter(int width, int height) {
        Filter filter = dao.enableFilter("resolution_id");
        filter.setParameter("width", width);
        filter.setParameter("height", height);
    }

    @Override
    public void disableResolutionFilter() {
        dao.disableFilter("resolution_id");
    }

    @Override
    public List<Wallpaper> save(List<Wallpaper> wallpapers) {
        return wallpaperRepository.save(wallpapers);
    }
}