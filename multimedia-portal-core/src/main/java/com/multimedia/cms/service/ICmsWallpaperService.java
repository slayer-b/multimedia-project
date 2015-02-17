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

package com.multimedia.cms.service;

import com.multimedia.security.model.User;
import common.cms.services2.ICmsService;
import gallery.model.beans.Pages;
import gallery.model.beans.Wallpaper;
import gallery.model.misc.StatusBean;
import gallery.model.misc.UploadBean;

import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface ICmsWallpaperService extends ICmsService<Wallpaper, Long>{

	/**
	 * inserts a list of items
	 *
     * @param list items to insert
     * @return quantity of items inserted
	 */
	int insert(List<Wallpaper> list);
	/**
	 * creates folders with hierarchical structure like in database
	 * @return list of folders
	 */
	List<UploadBean> createPageFolders();

	/**
	 * creates folders with hierarchical structure like in database
	 * @return list of folders
	 */
	List<Pages> getCategoriesLayered();

	/**
	 * uploads wallpapers
	 * @param uploader user that uploads these wallpapers
	 * @param id_pages all wallpapers from root dir are uploaded to this page(nowhere if null)
	 * @param usb for monitoring of upload process
	 * @return quantity of wallpapers added
	 */
	long uploadWallpapers(User uploader, Boolean active, Long id_pages, StatusBean usb);

	/**
	 * prepares wallpapers for father uploading by uploadWallpapers method
	 * @param usb for monitoring process
	 */
	void preUploadWallpapers(StatusBean usb);

	/**
	 * deletes wallpapers with given id
	 */
	int deleteById(Long[] id);

	/**
	 * get all wallpaper id with given id_pages
	 * if id == null get all wallpapers
	 * @param id id_pages
	 * @return list of wallpapers id
	 */
	List<Long> getWallpapersInCategory(Long id);

	/**
     * @param appendAll
     */
	boolean reResizeWallpapers(boolean appendAll);

	/**
	 * generates new title, tags ... for wallpapers
	 * @param id id of wallpapers to handle(optimize)
	 */
	void optimizeWallpaper(Long id);

	/**
	 * generates new title, tags ... for wallpapers
	 * @param id_pages id of catalogues(pages) to handle(optimize)
	 */
	void optimizeWallpaperCategories(Long[] id_pages);

	/**
	 * change optimization for wallpapers in pages with given ids
	 * @param id_pages id of catalogues(pages) to handle(optimize)
	 * @param optimized optimized flag to be set
	 */
	void setWallpaperOptimizationCategories(Long[] id_pages, Boolean optimized);

	/**
	 * read all wallpaper files, and change their resolution in database to file's
	 * @return count of handled wallpapers
	 */
	long renewResolution(StatusBean sb);

	/**
	 * get all wallpapers and check their resolutions for equality
	 * if they are equal then check their file size for equality
	 * if they are equal then add to results
	 *
	 * else add all wallpapers without files to result
	 * @param quantity quantity of max equal wallpapers before ending. all if less then 1
	 * @return array of similar wallpapers (each array contains similar wallpapers)
	 */
	List<List<Wallpaper>> findWallpaperDuplicates(int quantity);

	/**
	 * checks if id_pages_new is an id of wallpaper page
	 * if yes changes id_page of all wallpapers with given id to id_pages_new
	 * @param id ids of wallpapers to move
	 * @param id_pages_new id of new page
	 * @return updated wallpapers
	 */
	int moveWallpapersToPage(Long[] id, Long id_pages_new);

    /**
     * copy all wallpapers from root folder to subfolders with default names.
     * @return return messages with error. And info in first element.
     */
    List<String> moveWallpaperImagesFromRootToChild();
}
