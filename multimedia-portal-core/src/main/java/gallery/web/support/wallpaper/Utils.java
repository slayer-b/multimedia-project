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

package gallery.web.support.wallpaper;

import common.utils.ImageUtils;
import common.utils.image.BufferedImageHolder;
import gallery.model.beans.Wallpaper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * implements differ actions with file( such as save, delete...)
 * @author dima
 */
public final class Utils {
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	public static final String FULL_DIMENSION_NAME = "full";

    private Utils() {}

    /**
     * resize wallpaperCollection file to new dimensions(declared in config) and saves it to disk
     * @param wallpaper Wallpaper object
	 * @param dimensions map that contains name(folder)/resolution of resized wallpaperCollection
	 * @param basePath path of folder where to create subdirectories
	 * @param resizedFolder may be null. contains resized copies of wallpaperCollection
	 * @param origFileName may be null. original file name for this wallpaperCollection, used when uploading a pre uploaded wallpaperCollection
	 * @return dimensions of saved wallpaperCollection
     */
    public static Dimension saveScaledWallpaperFileToDisk(Wallpaper wallpaper, Map<String,Integer> dimensions,
            String basePath, File resizedFolder, String origFileName)
	{
		Set<Entry<String,Integer>> entrySet = dimensions.entrySet();
		Iterator<Entry<String,Integer>> entries = entrySet.iterator();
		List<File> files = new ArrayList<File>(entrySet.size());
		try {
			long ms = System.currentTimeMillis();
			BufferedImageHolder holder = null;
			if (wallpaper.getContent()!=null) {
				holder = ImageUtils.readImage(wallpaper.getContent());
			} else if (wallpaper.getContent_file()!=null){
				holder = ImageUtils.readImage(wallpaper.getContent_file());
			}
			ms = System.currentTimeMillis() - ms;
			LOGGER.debug("reading wallpaperCollection: {}ms", ms);
			if (holder!=null) {
				while (entries.hasNext()) {
					Entry<String,Integer> e = entries.next();
					File rez = findFileFromBasePath(basePath, e.getKey(), wallpaper);
					File resultCandidate = null;
					if (resizedFolder!=null) {
                        //TODO: fix this, origFileName might not be the same as in resized folder as it can be taken from wallpaperCollection name
						resultCandidate = new File(resizedFolder, e.getKey() + File.separator+origFileName);
					}
					if (resultCandidate != null && resultCandidate.exists()) {
						FileUtils.copyFile(resultCandidate, rez);
					} else {
						//no resized file found resizing ...
						double scaleFactor = ImageUtils.getScaling(holder.getImage().getWidth(), e.getValue());
						//if scale factor is 1 then saving src file
						ms = System.currentTimeMillis();
						if ((scaleFactor<1d&&scaleFactor>0d)||(holder.needEncode())) {
							LOGGER.debug("encoded: {}", holder.getFormat_name());
							ImageUtils.saveScaledImageWidth(holder.getImage(), scaleFactor, rez);
						} else {
							LOGGER.debug("not encoded: {}", holder.getFormat_name());
                            FileUtils.copyInputStreamToFile(holder.getInputStream(), rez);
						}
					}
					ms = System.currentTimeMillis() - ms;
					LOGGER.debug("resizing-->{}: {}ms", e.getKey(), ms);
					files.add(rez);
				}
				int maxWidth = dimensions.get(FULL_DIMENSION_NAME);
				double scaleFactor = ImageUtils.getScaling(holder.getImage().getWidth(), maxWidth);
				if (scaleFactor<1d&&scaleFactor>0d) {
					return ImageUtils.getDimmension(scaleFactor, holder.getImage());
				} else {
					return new Dimension(holder.getImage().getWidth(), holder.getImage().getHeight());
				}
			} else {
				return null;
			}
		} catch (IOException ex) {
			LOGGER.error("saving scaled wallpaperCollection to disk", ex);
            deleteFilesWithLog(files);
			return null;
		}
    }

    /**
     * resize wallpaperCollection file to all dimensions and saves it to disk
     * used for pre uploading
     * @param image_file file with image to resize
	 * @param dimensions map that contains name(folder)/resolution for resized images
	 * @param result_dir path of folder where to create subdirectories with resulting images
	 * @return true if succeed
     */
    public static boolean saveScaledImageFileToDisk(File image_file, Map<String, Integer> dimensions, File result_dir)
	{
		Set<Entry<String,Integer>> entrySet = dimensions.entrySet();
		Iterator<Entry<String,Integer>> entries = entrySet.iterator();
		List<File> files = new ArrayList<File>(entrySet.size());
		try {
			long ms = System.currentTimeMillis();
			BufferedImageHolder holder = ImageUtils.readImage(image_file);
			ms = System.currentTimeMillis() - ms;
			LOGGER.debug("reading image: {}ms", ms);
			if (holder!=null){
				while (entries.hasNext()){
					Entry<String,Integer> e = entries.next();
					File rez = new File(result_dir, e.getKey() + File.separator + image_file.getName());

					double scaleFactor = ImageUtils.getScaling(holder.getImage().getWidth(), e.getValue());
					//if scale factor is 1 then saving src file
					ms = System.currentTimeMillis();
					if ((scaleFactor<1d&&scaleFactor>0d)||(holder.needEncode())){
						LOGGER.debug("encoded: {}", holder.getFormat_name());
						ImageUtils.saveScaledImageWidth(holder.getImage(), scaleFactor, rez);
					} else {
						LOGGER.debug("not encoded: {}", holder.getFormat_name());
						InputStream is = holder.getInputStream();
                        FileUtils.copyInputStreamToFile(is, rez);
					}
					ms = System.currentTimeMillis() - ms;
					LOGGER.debug("resizing-->{}: {}ms", e.getKey(), ms);
					files.add(rez);
				}
				return true;
			} else {
				return false;
			}
		} catch (IOException ex) {
			LOGGER.error("saving scaled image to disk", ex);
			deleteFilesWithLog(files);
			//false indicates an error
			return false;
		}
    }

	/**
	 * deletes a wallpaperCollection and all its copies
     * @param wallpaper wallpaper to delete all copies of it
	 * @param dimensions map that contains name(folder)/resolution of resized wallpaperCollection
	 * @param basepath path of folder where to look for subdirectories
	 * @param resolution_directories directories for wallpapers resized to user resolutions
	 */
	public static boolean deleteWallpaper(Wallpaper wallpaper, Map<String,Integer> dimensions, String basepath, List<File> resolution_directories){
		Set<Entry<String,Integer>> entrySet = dimensions.entrySet();
        for (Entry<String, Integer> e : entrySet) {
            File rez = findFileFromBasePath(basepath, e.getKey(), wallpaper);
            if (rez.exists() && !rez.delete()) {
                rez.deleteOnExit();
                //one file exists but is not deleted error here :(
                // may be its better try to delete other copies ...
                return false;
            }
        }
		for (File f : resolution_directories) {
			File image = findFileFromResolution(f.getAbsolutePath(), wallpaper);
			if (image.exists() && !image.delete()){
				image.deleteOnExit();
			}
		}
		return true;
    }

	/**
	 * renames a wallpaperCollection and all its copies
     * @param wallpaper wallpaper containing new values
     * @param old wallpaper containing old values
	 * @param dimensions map that contains name(folder)/resolution of resized wallpaperCollection
	 * @param basepath path of folder where to look for subdirectories
	 * @param resolution_directories directories for wallpapers resized to user resolutions
	 */
	public static boolean renameWallpaper(Wallpaper wallpaper, Wallpaper old, Map<String,Integer> dimensions, String basepath, List<File> resolution_directories){
		Iterator<Entry<String,Integer>> entries = dimensions.entrySet().iterator();
		boolean ok_1 = true;
        //TODO: refactor almost the same peace of code
		while (entries.hasNext() && ok_1) {
			Entry<String,Integer> e = entries.next();
			File oldFile = findFileFromBasePath(basepath, e.getKey(), old);
			File newFile = findFileFromBasePath(basepath, e.getKey(), wallpaper);
            //because new file is always unique, delete new_file if exists
            if (newFile.exists() && !newFile.delete()) {
                LOGGER.error("Delete new file for wallpaper id [{}], file [{}]",
                        wallpaper.getId(), newFile.getAbsolutePath());
			}
			if (oldFile.exists()) {
                File newParent = newFile.getParentFile();
                if (!newParent.exists() && !newParent.mkdirs()) {
                    LOGGER.error("Old file renaming failed, cannot create parent folders for wallpaper old [{}], new [{}]",
                            oldFile.getAbsolutePath(), newFile.getAbsolutePath());
                }
                if (!oldFile.renameTo(newFile)) {
                    LOGGER.error("Old file renaming failed for wallpaper old [{}], new [{}]",
                            oldFile.getAbsolutePath(), newFile.getAbsolutePath());
                    ok_1 = false;
                }
			} else {
                LOGGER.error("Old file doesn't exist for wallpaper id [{}], file [{}]",
                        wallpaper.getId(), oldFile.getAbsolutePath());
                ok_1 = false;
            }
		}
		boolean ok_2 = true;
		if (ok_1) {
			for (File f : resolution_directories) {
				File oldFile = findFileFromResolution(f.getAbsolutePath(), old);
				File newFile = findFileFromResolution(f.getAbsolutePath(), wallpaper);
                //because new file is always unique, delete new_file if exists
                if (ok_2 && newFile.exists() && !newFile.delete()) {
                    LOGGER.error("New file delete failed for wallpaper id [{}], file [{}]",
                            wallpaper.getId(), oldFile.getAbsolutePath());
                    ok_2 = false;
				}
                if (ok_2 && oldFile.exists()) {
                    File newParent = newFile.getParentFile();
                    if (!newParent.exists() && !newParent.mkdirs()) {
                        ok_2 = false;
                        LOGGER.error("Old file renaming failed, cannot create parent folders for wallpaper old [{}], new [{}]",
                                oldFile.getAbsolutePath(), newFile.getAbsolutePath());
                    }
                    if (!oldFile.renameTo(newFile)) {
                        ok_2 = false;
                        LOGGER.error("Old file renaming failed for wallpaper old [{}], new [{}]",
                                oldFile.getAbsolutePath(), newFile.getAbsolutePath());
                    }
                }
			}
		}
		//reverting changes in case of an error
		if (!ok_1 || !ok_2) {
			entries = dimensions.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String,Integer> e = entries.next();
				File oldFile = findFileFromBasePath(basepath, e.getKey(), old);
				File newFile = findFileFromBasePath(basepath, e.getKey(), wallpaper);
				if (newFile.exists() && !newFile.renameTo(oldFile)) {
                    LOGGER.error("Failed to revert rename file for wallpaper id [{}], file [{}]",
                            wallpaper.getId(), newFile.getAbsolutePath());
				}
			}
            LOGGER.error("Failed to rename files for wallpaper id [{}]", wallpaper.getId());
		}
		if (!ok_2) {
			for (File f : resolution_directories) {
				File oldFile = findFileFromResolution(f, old);
				File newFile = findFileFromResolution(f, wallpaper);
				if (newFile.exists() && !newFile.renameTo(oldFile)) {
                    LOGGER.error("Failed to revert rename file for wallpaper id [{}], file [{}]",
                            wallpaper.getId(), newFile.getAbsolutePath());
				}
			}
		}
		return ok_1 && ok_2;
    }

	/**
	 * 1) create dimension folders
     * 2) copy wallpaper from src_path to dst_path (including dimensions)
     * @param wallpaperCollection list of wallpapers to copy
	 * @param dimensions map that contains name(folder)/resolution of resized wallpaperCollection
	 * @param src_path source path to copy
	 * @param dst_path destination path for copy
	 * @param append if true append all files, else only newer
	 * @param remove_copied if true then delete wallpaperCollection from list after copying its file (no delete if no file)
	 * @return list of copied wallpapers
	 */
    public static List<Wallpaper> copyWallpaper(Collection<Wallpaper> wallpaperCollection, Map<String, Integer> dimensions,
                                                String src_path, String dst_path, boolean append, boolean remove_copied) {
        List<Wallpaper> rez = new ArrayList<Wallpaper>();
        Set<Entry<String, Integer>> entrySet = dimensions.entrySet();
        Iterator<Entry<String, Integer>> entries = entrySet.iterator();
        Iterator<Wallpaper> wallpapers = wallpaperCollection.iterator();
        //creating folder for backup if not exits
        File dstFolder = new File(dst_path);
        if (!dstFolder.exists()) {
            dstFolder.mkdirs();
        }
        while (entries.hasNext()) {
            Entry<String, Integer> entry = entries.next();
            File dstSubfolder = new File(dst_path + File.separator + entry.getKey());
            if (!dstSubfolder.exists()) {
                dstSubfolder.mkdir();
            }
        }
        while (wallpapers.hasNext()) {
            Wallpaper wallpaper = wallpapers.next();
            boolean copied = false;
            entries = entrySet.iterator();
            while (entries.hasNext()) {
                Entry<String, Integer> entry = entries.next();
                File src = findFileFromBasePath(src_path, entry.getKey(), wallpaper);
                File dst = findFileFromBasePath(dst_path, entry.getKey(), wallpaper);
                if (src.exists()) {
                    boolean copy = true;
                    if (!append && dst.exists() && (src.lastModified() <= dst.lastModified())) {
                        copy = false;
                        copied = true;
                    }
                    if (copy) {
                        try {
                            FileUtils.copyFile(src, dst, true);
                            copied = true;
                        } catch (IOException e) {
                            LOGGER.error("Failed to copy wallpaperCollection.", e);
                            copied = false;
                        }
                    }
                }
            }
            if (remove_copied && copied) {
                rez.add(wallpaper);
                wallpapers.remove();
            }
        }
        return rez;
    }

	/**
	 * the start wallpaperCollection is dimension is 'full' size
     * @param wallpapers list of wallpapers to copy
	 * @param dimensions map that contains name(folder)/resolution of resized wallpaperCollection
	 * @param basePath source path to get wallpapers
	 * @param append if true append all files, else only newer
	 * @return false if folder in path or folder with full pictures does not exists
	 */
	public static boolean resizeWallpaper(Collection<Wallpaper> wallpapers, Map<String,Integer> dimensions,
			String basePath, boolean append)
	{
		Set<Entry<String,Integer>> entrySet = dimensions.entrySet();
		Iterator<Entry<String,Integer>> entries = entrySet.iterator();
		List<Entry<String,Integer>> newEntries = new ArrayList<Entry<String,Integer>>(entrySet.size());
		while (entries.hasNext()) {
			Entry<String, Integer> e = entries.next();
			if (!e.getKey().equals(FULL_DIMENSION_NAME)) {
				newEntries.add(e);
			}
		}
		entries = newEntries.iterator();
		//if folder not exists return false
		File folder = new File(basePath);
		File fullFolder = new File(basePath + File.separator + FULL_DIMENSION_NAME);
		if ((!folder.exists()) || (!fullFolder.exists())) {
            return false;
        }
		while (entries.hasNext()) {
			Entry<String,Integer> e = entries.next();
			File subfolder = new File(basePath + File.separator + e.getKey());
			if (!subfolder.exists()) {
                subfolder.mkdir();
            }
		}
		long ms;
		for (Wallpaper wallpaper : wallpapers) {
			BufferedImageHolder holder = null;

			File src = findFileFromBasePath(basePath, FULL_DIMENSION_NAME, wallpaper);
			if (src.exists()) {
				for (Entry<String,Integer> e : newEntries) {
					File dst = findFileFromBasePath(basePath, e.getKey(), wallpaper);
					if (append || (!dst.exists())) {
						try {
							//reading image only once
							if (holder == null) {
							    holder = ImageUtils.readImage(src);
							}
							if (holder != null) {
								double scaleFactor = ImageUtils.getScaling(holder.getImage().getWidth(), e.getValue());
								//if scale factor is 1 then saving src file
								ms = System.currentTimeMillis();
								if ((scaleFactor<1d&&scaleFactor>0d)||(holder.needEncode())) {
									ImageUtils.saveScaledImageWidth(holder.getImage(), scaleFactor, dst);
								} else {
                                    FileUtils.copyInputStreamToFile(holder.getInputStream(), dst);
								}
								ms = System.currentTimeMillis() - ms;
								LOGGER.debug("resizing-->{}: {}ms", e.getKey(), ms);
							}
						}catch (IOException ex) {
							LOGGER.error("re resizing wallpaperCollection", ex);
						}
					}
				}
			}
		}
		return true;
    }

    private static void deleteFilesWithLog(List<File> files) {
        for (File f : files) {
            if (!f.delete()) {
                LOGGER.error("Failed to delete file [{}]", f.getAbsolutePath());
            }
        }
    }

    public static File findFileFromBasePath(String basePath, String dimension, Wallpaper wallpaper) {
        if (StringUtils.hasText(wallpaper.getFolder())) {
            return new File(basePath + File.separator + dimension
                    + File.separator + wallpaper.getFolder() + File.separator + wallpaper.getName());
        } else {
            return new File(basePath + File.separator + dimension
                    + File.separator + wallpaper.getName());
        }
    }

    public static File findFileFromResolution(String resolutionPath, Wallpaper wallpaper) {
        if (StringUtils.hasText(wallpaper.getFolder())) {
            return new File(resolutionPath + File.separator + wallpaper.getFolder()
                    + File.separator + wallpaper.getName());
        } else {
            return new File(resolutionPath + File.separator + wallpaper.getName());
        }
    }

    public static File findFileFromResolution(File resolutionFile, Wallpaper wallpaper) {
        if (StringUtils.hasText(wallpaper.getFolder())) {
            return new File(resolutionFile, wallpaper.getFolder()
                    + File.separator + wallpaper.getName());
        } else {
            return new File(resolutionFile, wallpaper.getName());
        }
    }
}
