package com.multimedia.dao;

import common.dao.GenericDAOHibernate;
import gallery.model.beans.WallpaperComment;

public class WallpaperCommentDAO extends GenericDAOHibernate<WallpaperComment, Long> {

    public WallpaperCommentDAO() {
        super(WallpaperComment.class);
    }

}
