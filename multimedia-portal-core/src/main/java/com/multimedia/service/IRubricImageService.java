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

import core.model.beans.Pages;

import java.util.List;


/**
 * provides images to serve as an icon for a rubric
 * @author demchuck.dima@gmail.com
 */
public interface IRubricImageService{

    /**
     * generates an url and an image(if needed) for a given page
     */
    String getImageUrl(Pages p);

    /**
     * generates an url and an image(if needed) for the given pages list
     * @param p pages for which to generate urls
     */
    List<String> getImageUrls(List<? extends Pages> p);

    /**
     * clears images cached for a page with given id
     * all images if null
     * @return true if succeed
     */
    boolean clearImages(Long id);

    /**
     * refresh images cached for a page with given id
     * all images if null
     * @return true if succeed
     */
    boolean refreshImages(Long id);
}