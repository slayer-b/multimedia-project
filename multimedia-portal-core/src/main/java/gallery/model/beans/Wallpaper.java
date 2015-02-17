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

package gallery.model.beans;

import com.multimedia.security.model.User;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.ParamDef;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import test.annotations.HtmlSpecialChars;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "photo")
@FilterDef(name = "resolution_id", parameters = {
        @ParamDef(name = "width", type = "integer"),
        @ParamDef(name = "height", type = "integer")
})

@Filters(
        @Filter(name = "resolution_id", condition = "width >= :width and height >= :height")
)
public class Wallpaper implements Serializable {
    private static final long serialVersionUID = -5679759939769651418L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_pages", nullable = true)
    private Long id_pages;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pages", updatable = false, insertable = false)
    @ForeignKey(name = "FK_pages_photo")
    private Pages pages;
    @ManyToOne
    @JoinColumn(name = "id_users", nullable = true)
    @ForeignKey(name = "FK_user_photo")
    private User user;
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    @HtmlSpecialChars
    @Column(name = "description_ru", length = 65535)
    private String description;
    @HtmlSpecialChars
    @Column(name = "title_ru", length = 65535)
    private String title;
    @HtmlSpecialChars
    @Column(name = "tags", length = 65535)
    private String tags;
    @Column(name = "width")
    protected Integer width;
    @Column(name = "height")
    protected Integer height;
    @Column(name = "views", nullable = false)
    protected Long views;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "optimized", nullable = false)
    private Boolean optimized;
    @Column(name = "optimized_manual", nullable = false)
    private Boolean optimized_manual;
    @Formula("(SELECT AVG(pr.rate) FROM photo_rating pr WHERE pr.id_photo=id)")
    private Double rating;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_upload", nullable = false)
    private Date date_upload;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "photo")
    private List<WallpaperRating> wallpaperRating;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "photo")
    @OrderBy("creationTime DESC ")
    private List<WallpaperComment> comments;
    @Column(name = "folder", nullable = false)
    private String folder;

    @Transient
    private MultipartFile content;
    @Transient
    private File content_file;
    @Transient
    private List<Resolution> resolutions;
    @Transient
    private String[] tagsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_pages() {
        return id_pages;
    }

    public void setId_pages(Long value) {
        this.id_pages = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String value) {
        this.tags = value;
    }

    public String[] getTagsList() {
        return tagsList == null ? (tags == null ? null : tags.split(", ")) : tagsList;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean value) {
        this.active = value;
    }

    public Boolean getOptimized() {
        return optimized;
    }

    public void setOptimized(Boolean optimized) {
        this.optimized = optimized;
    }

    public Boolean getOptimized_manual() {
        return optimized_manual;
    }

    public void setOptimized_manual(Boolean optimized_manual) {
        this.optimized_manual = optimized_manual;
    }

    public Date getDate_upload() {
        return date_upload;
    }

    public void setDate_upload(Date value) {
        this.date_upload = value;
    }
    public Double getRating() {
        if (rating == null) {
            return 0.0;
        } else {
            return rating;
        }
    }

    public void setRating(Double value) {
        this.rating = value;
    }

    public MultipartFile getContent() {
        return content;
    }

    public void setContent(MultipartFile value) {
        this.content = value;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages value) {
        this.pages = value;
    }

    public List<WallpaperRating> getWallpaperRating() {
        return wallpaperRating;
    }

    public void setWallpaperRating(List<WallpaperRating> value) {
        this.wallpaperRating = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User value) {
        this.user = value;
    }

    public List<WallpaperComment> getComments() {
        return this.comments;
    }

    public void setComments(List<WallpaperComment> value) {
        this.comments = value;
    }

    public File getContent_file() {
        return content_file;
    }

    public void setContent_file(File value) {
        this.content_file = value;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long value) {
        this.views = value;
    }

    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @Transient
    public String getFolderAndName() {
        if (StringUtils.hasText(getFolder())) {
            return getFolder() + "/" + getName();
        } else {
            return getName();
        }
    }

    /**
     * this method copies only id, id_pages, id_users, name, date_upload
     */
    public static Map<String, Object> toMap(Wallpaper p) {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("id", p.getId());
        hm.put("id_pages", p.getId_pages());
        hm.put("name", p.getName());
        hm.put("folder", p.getFolder());
        hm.put("date_upload", p.getDate_upload());
        return hm;
    }

}
