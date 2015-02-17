package com.multimedia.exceptions;

public class WallpaperNotFound extends RuntimeException {
    private static final long serialVersionUID = -2601252641619966872L;
    
    private final String name;
    private final String folder;

    public WallpaperNotFound(String name) {
        this.name = name;
        this.folder = "";
    }

    public WallpaperNotFound(String name, String folder) {
        this.name = name;
        this.folder = folder;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String getMessage() {
        return "Wallpaper [" + name + "] not found in folder [" + folder + "].";
    }
}
