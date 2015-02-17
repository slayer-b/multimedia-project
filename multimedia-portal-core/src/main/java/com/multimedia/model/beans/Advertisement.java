/*
 *  Copyright 2010 demchuck.dima@gmail.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.multimedia.model.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import test.annotations.HtmlSpecialChars;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "advertisement")
@Cache(region = "advertisement", include = "non-lazy", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

    @Column(name = "text", length = 65535)
	private String text;
	@HtmlSpecialChars
    @Column(name = "name", length = 100, nullable = false)
	private String name;
	@HtmlSpecialChars
    @Column(name = "position", length = 50, nullable = false)
	private String position;

	@Column(name = "sort", nullable = false)
	private Long sort;
	@Column(name = "active", nullable = false)
	private Boolean active;
	@OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
	private List<AdvertisementPages> advertisementPages;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public String getText() {return text;}
	public void setText(String text) {this.text = text;}

	public String getPosition() {return position;}
	public void setPosition(String position) {this.position = position;}

	public Long getSort() {return sort;}
	public void setSort(Long sort) {this.sort = sort;}

	public Boolean getActive() {return active;}
	public void setActive(Boolean active) {this.active = active;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

    public void setAdvertisementPages(List<AdvertisementPages> advertisementPages) {
        this.advertisementPages = advertisementPages;
    }
    public List<AdvertisementPages> getAdvertisementPages() {
        return advertisementPages;
    }
}
