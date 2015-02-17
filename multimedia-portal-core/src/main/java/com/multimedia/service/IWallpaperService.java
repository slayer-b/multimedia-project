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

package com.multimedia.service;

import common.services.generic.IGenericService;
import gallery.model.beans.Wallpaper;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IWallpaperService extends IGenericService<Wallpaper, Long> {

    /** generate default folder name */
    String defaultFolderName();
    /** normalize folder name */
    void normalizeFolder(Wallpaper wallpaper);
	/**
	 * creates a file with its content (i.e. jpg image)
	 * and set's width and height of wallpaper
	 * @param p wallpaper to save
	 * @return true if ok
	 */
	boolean getImage(Wallpaper p);

    /**
     * if folder contains appropriate subfolders
     *	before resizing wallpaper checks for resized images in these subfolders
     * @return true if ok
     */
    boolean getImages(List<Wallpaper> wallpapers, List<File> folders);

	/**
	 * deletes all copies of this wallpaper (files with resized wallpaper)
	 * @param wallpaper wallpaper to delete (must have at least name property)
	 * @return false if error
	 */
	boolean deleteFiles(Wallpaper wallpaper);

	/**
	 * renames all copies of this wallpaper (files with resized wallpaper)
	 * and fixes a name property if it contains deprecated characters or is not uniq
	 * @param wallpaper wallpaper to delete (must have at least name property)
	 * @return false if error
	 */
	boolean renameFiles(Wallpaper wallpaper);

    boolean renameFiles(Wallpaper wallpaper, Wallpaper old);

	/**
	 * get all active wallpapers with given id_pages
	 * one wallpaper from each sublist
	 * @param id_pages id of pages
	 * @return list of wallpapers
	 */
	List<Wallpaper> getMainImages(List<Long> id_pages, int count);

	/**
	 * get all active wallpapers with given id_pages
	 * @param first_num first number to select
	 * @param quantity quantity of items to select
	 * @return list of wallpapers
	 */
	List<Wallpaper> getWallpapersPaginated(int first_num, int quantity, Long id_pages);

	/**
	 * get all active wallpapers with given id_pages
	 * @param first_num first number to select
	 * @param quantity quantity of items to select
	 * @return list of wallpapers
	 */
	List<Wallpaper> getWallpapersPaginated(int first_num, int quantity, Long[] id_pages);

	/**
	 * get all active wallpapers with given id_pages
	 * @param id an id of wallpaper to start selecting
	 * @param quantity quantity of items to select (if less then 0 selecting  left greater then 0 selecting right)
	 * @return list of wallpapers
	 */
	List<Wallpaper> getWallpapersPaginatedId(Long id, int quantity, Long id_pages);

	/**
	 * get all active wallpapers with given id_pages
	 * @return quantity of wallpapers
	 */
	Long getWallpapersRowCount(Long id_pages);

    /** throws exception if not found. */
    Wallpaper findWallpaperFileByFolderAndName(String name, String folder);

    List<Wallpaper> findByFolder(String folder, int quantity);

	/**
	 * saves the wallpaper with given id and resized to new_width to given os
	 * @param id_wallpaper id of wallpaper to save
	 * @param new_width width to resize wallpaper
	 * @param new_height height to resize wallpaper
	 * @param os where to save result
	 * @return true if wallpapers found
	 */
	boolean getResizedWallpaperStream(Long id_wallpaper, Integer new_width, Integer new_height, OutputStream os) throws IOException;

	/**
	 * First checks whether a photo with such name realy exists.
	 * If wallpaper exists, but is not in given resolution
	 * resize it to given resolution.
	 * @param name name of wallpaper
	 * @param newWidth width to resize wallpaper
	 * @param newHeight height to resize wallpaper
	 * @return file with wallpaper in given resolution or null if wallpaper not found
	 * @throws IOException if an exception occurs while resizing wallpaper.
	 */
	File getResizedWallpaper(Wallpaper wallpaper, int newWidth, int newHeight) throws IOException;

    /**
     * @param name name of wallpaper
     * @param newWidth width to wallpaper
     * @param newHeight height to wallpaper
     * @return last modified for a file of wallpaper with specified name or -1 if not found
     */
    long getWallpaperLastModified(Wallpaper wallpaper, int newWidth, int newHeight);

	/**
	 * execute a backup of wallpapers directory is setted in services
	 * @param wallpapers list of wallpapers t backup
	 * @param append if true append all files / else new files
	 * @param onlyFiles backup only files
	 * @return list of backuped wallpapers, wallpapers param contains only unbackuped wallpapers
	 */
	List<Wallpaper> backupWallpapers(List<Wallpaper> wallpapers, boolean append, boolean onlyFiles);

	/**
	 * execute a restore of wallpapers directory is setted in services
	 * @param wallpapers list of wallpapers to restore
	 * @param append if true append all files / else new files
	 * @param onlyFiles restore only files
	 * @return list of restore wallpapers, wallpapers param contains only unrestored wallpapers
	 */
	List<Wallpaper> restoreWallpapers(List<Wallpaper> wallpapers, boolean append, boolean onlyFiles);

	String getUploadPath();

	/**
	 * directory where wallpapers are stored(to show in web application)
	 * @return path to directory
	 */
	String getStorePath();

	/**
	 * get dimmensions for this wallpaper service
	 * @return map containing dir name(key) and width(value) of dimmension
	 */
	Map<String,Integer> getDimmensions();

	/**
	 * get number of wallpapers
	 * @param p an entity whitch number you seek
	 * @return number of entity in resultset returned by getWallpapersPaginatedId() method
	 */
	Long getWallpaperNumber(Wallpaper p);

	/**
	 * get quantity of random wallpapers from DB
	 * @param quantity quantity of wallpapers to get
	 * @return list of wallpapers
	 */
	List<Wallpaper> getRandomWallpapers(int quantity);

	/**
	 * get tags from all active wallpapers, tags area created by parsing 'tags' field of wallpapers
	 * @param maxTags max tag quantity to be returned
	 * @return tags from all active wallpapers
	 */
	Map<String, Double> getTags(int maxTags);

	void enableResolutionFilter(int width, int height);

	void disableResolutionFilter();

    List<Wallpaper> save(List<Wallpaper> wallpapers);
}
