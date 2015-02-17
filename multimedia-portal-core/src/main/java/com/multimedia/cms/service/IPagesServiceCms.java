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

import gallery.model.beans.Pages;

import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IPagesServiceCms {
    void reactivate();
    /**
     * pages contain quantities of items (PagesPseudonimes, wallpapers...)
     * @return all pages, that are modules (i.e. wallpaper_gallery_type)
     */
	List<Pages> getCategoriesFull();

    /**
     * creates an optimization phrases for all child pageses's elements
     */
    void optimizeCategory(Long id);
    void resetOptimizationCategory(Long id, Boolean optimized);
}
