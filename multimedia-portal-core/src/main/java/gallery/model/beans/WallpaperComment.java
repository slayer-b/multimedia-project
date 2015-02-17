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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.multimedia.security.model.User;
import test.annotations.HtmlSpecialChars;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "photo_comment")
public class WallpaperComment implements Serializable {
    private static final long serialVersionUID = -3669530542047621107L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_photo", nullable = false)
    private Long id_photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_photo", updatable = false, insertable = false)
    @ForeignKey(name = "FK_photo_photoComments")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wallpaper photo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_users", nullable = true)
    @ForeignKey(name = "FK_user_photoComment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @HtmlSpecialChars
    @Column(name = "text", nullable = false, columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_photo() {
        return id_photo;
    }

    public void setId_photo(Long value) {
        this.id_photo = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Wallpaper getPhoto() {
        return photo;
    }
}
