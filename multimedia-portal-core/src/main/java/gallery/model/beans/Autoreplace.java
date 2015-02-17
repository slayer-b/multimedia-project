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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "autoreplace")
public class Autoreplace implements Serializable {
    private static final long serialVersionUID = -2417097742519313520L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "sort", nullable = false)
    private Long sort;
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,
            mappedBy = "localeParent", targetEntity = AutoreplaceL.class)
    private List<AutoreplaceL> localizations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Transient
    public String getActiveHtml() {
        if (Boolean.TRUE.equals(active)) {
            return "checked";
        } else {
            return "";
        }
    }

    public List<AutoreplaceL> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(List<AutoreplaceL> localizations) {
        this.localizations = localizations;
    }

    @Override
    public String toString() {
        return getClass().getName() + ". id:" + id + ", code:" + code + ", sort=" + sort + ", active=" + active;
    }

}