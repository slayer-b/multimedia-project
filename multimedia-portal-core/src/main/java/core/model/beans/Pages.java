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

package core.model.beans;

import java.io.Serializable;
import java.util.List;

public class Pages implements Serializable, Cloneable {
    private static final long serialVersionUID = -6720737101923377409L;

    private Long id;
     private Long id_pages;
     private Pages pages;

     private String name;
     private String info_top;
     private String info_bottom;
     private String title;
     private String description;
     private String keywords;

     private Long sort;
	 private Long layer;

     private String type;

     private Boolean active;
     private Boolean last;

     private List pageses;
     //for view only
     /** if this page is selected (comes with parameters) */
     private Boolean selected;

    public Long getId() {return this.id;}
    public void setId(Long id) {this.id = id;}

    public Pages getPages() {return this.pages;}
    public void setPages(Pages pages) {this.pages = pages;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getInfo_top() {return this.info_top;}
    public void setInfo_top(String info_top) {this.info_top = info_top;}

    public String getInfo_bottom() {return this.info_bottom;}
    public void setInfo_bottom(String info_bottom) {this.info_bottom = info_bottom;}

    public String getTitle() {return this.title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}

    public String getKeywords() {return this.keywords;}
    public void setKeywords(String keywords) {this.keywords = keywords;}

    public Long getSort() {return this.sort;}
    public void setSort(Long sort) {this.sort = sort;}

    public Boolean getActive() {return this.active;}
    public void setActive(Boolean active) {this.active = active;}

    public String getType() {return this.type;}
    public void setType(String type) {this.type = type;}

    public List getPageses() {return this.pageses;}
    public void setPageses(List pageses) {this.pageses = pageses;}

	public Long getId_pages(){return id_pages;}
	public void setId_pages(Long id_pages) {this.id_pages = id_pages;}

	/**
	 * its number in hierarchy
	 */
	public Long getLayer() {return layer;}

	/**
	 * its number in hierarchy
	 */
	public void setLayer(Long layer) {this.layer = layer;}

	/**
	 * true if has no subpages
	 */
	public Boolean getLast() {return last;}

	/**
	 * true if has no subpages
	 */
	public void setLast(Boolean value) {this.last = value;}

    public Boolean getSelected() {return selected;}
    public void setSelected(Boolean selected) {this.selected = selected;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(id).append("; ");
        sb.append("id_pages:").append(id_pages).append("; ");
        sb.append("name:").append(name).append("; ");
        sb.append("top:").append(info_top).append("; ");
        sb.append("bottom:").append(info_bottom).append("; ");
        sb.append("title:").append(title).append("; ");
        sb.append("descr:").append(description).append("; ");
        sb.append("keywords:").append(keywords).append("; ");
        sb.append("sort:").append(sort).append("; ");
        sb.append("l:").append(layer).append("; ");
        sb.append("t:").append(type).append("; ");
        sb.append("a:").append(active).append("; ");
        sb.append("l:").append(last).append("; ");
        sb.append("s:").append(selected).append("; ");
        return sb.toString();
    }

	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
	}

    public Pages clonePage() {
        try {
            return (Pages) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}