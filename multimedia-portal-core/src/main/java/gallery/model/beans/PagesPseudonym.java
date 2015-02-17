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

import org.hibernate.validator.constraints.NotEmpty;
import test.annotations.HtmlSpecialChars;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "pages_pseudonym")
public class PagesPseudonym implements Serializable {
    private static final long serialVersionUID = -2087470510666095072L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "id_pages", nullable = false)
    private Long id_pages;
    @NotNull
    @Column(name = "sort", nullable = false)
    private Long sort;
    @HtmlSpecialChars
    @Column(name = "text", length = 1000)
    private String text;

    @Column(name = "use_in_pages")
    private Boolean useInPages;
    @Column(name = "use_in_items")
    private Boolean useInItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_pages() {
        return id_pages;
    }

    public void setId_pages(Long id_pages) {
        this.id_pages = id_pages;
    }

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

    public Boolean getUseInPages() {
        return useInPages;
    }

    public void setUseInPages(Boolean val) {
        this.useInPages = val;
    }

    public Boolean getUseInItems() {
        return useInItems;
    }

    public void setUseInItems(Boolean val) {
        this.useInItems = val;
    }

}
