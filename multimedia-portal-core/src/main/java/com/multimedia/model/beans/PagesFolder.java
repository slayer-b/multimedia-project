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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import gallery.model.beans.Pages;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "pages_folder")
public class PagesFolder {
    @Id
    @Column(name = "pages_id")
	private Long id;

    @Column(name = "name", nullable = true)
	private String name;

    @MapsId
    @ManyToOne(optional = false, targetEntity = Pages.class)
    @JoinColumn(name = "pages_id")
    @ForeignKey(name = "FK_pages_pages_folder")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Pages pages;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public Pages getPages() {return pages;}
	public void setPages(Pages pages) {this.pages = pages;}

	public String getName() {return name;}
	public void setName(String folder_name) {this.name = folder_name;}

}
