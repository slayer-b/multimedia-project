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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "photo_rating")
public class WallpaperRating implements Serializable {
    private static final long serialVersionUID = -5851385481995816488L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_photo", nullable = false)
    private Long id_photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_photo", updatable = false, insertable = false)
    @ForeignKey(name = "FK_photo_photoRating")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wallpaper photo;

    @Column(name = "ip", length = 20, nullable = false)
    private String ip;
    @Column(name = "rate", length = 5, nullable = false)
    private Long rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_photo() {
        return id_photo;
    }

    public void setId_photo(Long id_photo) {
        this.id_photo = id_photo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Wallpaper getPhoto() {
        return photo;
    }
}
