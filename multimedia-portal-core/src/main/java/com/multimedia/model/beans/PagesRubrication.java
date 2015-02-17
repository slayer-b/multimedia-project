package com.multimedia.model.beans;

/**
 * contains some fields of Pages entity.
 * Is used for displaying rubrication by Jackson to create JSON.
 * @author slayer
 *
 */
public class PagesRubrication {
    private Long id;
    private String name;
    private Boolean last;
    private String moduleName;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getLast() {
        return last;
    }
    public void setLast(Boolean last) {
        this.last = last;
    }
    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
