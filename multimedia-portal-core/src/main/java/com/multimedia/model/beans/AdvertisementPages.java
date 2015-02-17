/*
 *  Copyright 2010 demchuck.dima@gmail.com.
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

import gallery.model.beans.Pages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "advertisement_pages")
public class AdvertisementPages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
    @Column(name = "id_pages", nullable = false)
	private Long id_pages;
    @Column(name = "id_advertisement", nullable = false)
	private Long id_advertisement;

	@Column(name = "useInParent", nullable = false)
	private Boolean useInParent;
    @Column(name = "useInChildren", nullable = false)
	private Boolean useInChildren;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pages", nullable = false, insertable = false, updatable = false)
    @ForeignKey(name = "FK_pages_advertisement")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Pages pages;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_advertisement", nullable = false, insertable = false, updatable = false)
    @ForeignKey(name = "FK_advertisement_pages")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Advertisement advertisement;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public Long getId_pages() {return id_pages;}
	public void setId_pages(Long id_pages) {this.id_pages = id_pages;}

	public Long getId_advertisement() {return id_advertisement;}
	public void setId_advertisement(Long id_advertisement) {this.id_advertisement = id_advertisement;}

	public Boolean getUseInParent() {return useInParent;}
	public void setUseInParent(Boolean useInParent) {this.useInParent = useInParent;}

	public Boolean getUseInChildren() {return useInChildren;}
	public void setUseInChildren(Boolean useInChildren) {this.useInChildren = useInChildren;}

	public Pages getPages() {return pages;}
	public void setPages(Pages pages) {this.pages = pages;}

	public Advertisement getAdvertisement() {return advertisement;}
	public void setAdvertisement(Advertisement advertisement) {this.advertisement = advertisement;}

}
