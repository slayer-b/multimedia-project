package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import gallery.model.beans.Wallpaper;
import org.hibernate.ScrollableResults;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

public class WallpaperRepositoryImpl extends GenericDAOHibernate<Wallpaper, Long> implements WallpaperRepositoryCustom {

    public WallpaperRepositoryImpl() {
        super(Wallpaper.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> getWallpaperTags() {
        ScrollableResults wallpaperTags = getCurrentSession().createQuery(
                "select tags from " + this.entityName + " where active = :active")
                .setParameter("active", Boolean.TRUE)
                .scroll();
        Map<String, Double> tags = new HashMap<String, Double>();
        if (wallpaperTags.first()) {
            do {
                String tag = wallpaperTags.getString(0);
                if (tag != null) {
                    String[] tagsParsed = tag.split(",");
                    for (int i = 1; i < tagsParsed.length; i++) {
                        String tagParsed = tagsParsed[i].trim();
                        Double score = tags.get(tagParsed);
                        if (score == null) {
                            tags.put(tagParsed, 1.0);
                        } else {
                            tags.put(tagParsed, score + 1.0);
                        }
                    }
                }
            } while (wallpaperTags.next());
        }
        wallpaperTags.close();
        return tags;
    }

}
