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

package com.multimedia.dao;

import gallery.model.beans.Wallpaper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: slayer
 * Date: 09.05.12
 */
public interface WallpaperRepository extends JpaRepository<Wallpaper, Long>, WallpaperRepositoryCustom {

    @Query("from Wallpaper w where w.id_pages = ?1 and w.active = ?2")
    List<Wallpaper> findById_pagesAndActive(Long id_pages, Boolean active, Pageable pageable);

    @Query("select count(*) from Wallpaper w where w.id != ?1 and w.name = ?2")
    Long findCountByNameAndNotId(Long id, String name);

    @Override
    <S extends Wallpaper> List<S> save(Iterable<S> entities);
}
