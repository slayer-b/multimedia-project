package com.adv.core.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import test.annotations.HtmlSpecialChars;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class ItemLink extends ItemAdv {
    private static final long serialVersionUID = 1944027035748065994L;

    private Long id_location;

    private ContentLink contentLink;

    private Long sort;

    @HtmlSpecialChars
    @NotEmpty
    private String title;
    @HtmlSpecialChars
    @NotEmpty
    private String text;
    @URL
    @NotEmpty
    private String url;
    @Email
    @NotEmpty
    private String email;

    private ItemLinkStats stats;

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_location() {
        return id_location;
    }

    public void setId_location(Long id_location) {
        this.id_location = id_location;
    }

    public ContentLink getContentLink() {
        return contentLink;
    }

    public void setContentLink(ContentLink contentLink) {
        this.contentLink = contentLink;
    }

    /**
     * @return the stats
     */
    public ItemLinkStats getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(ItemLinkStats stats) {
        this.stats = stats;
    }
}
