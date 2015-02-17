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

package core.rss.elem;

import core.rss.RssDateAdaper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that represent the {@code &lt;channel&gt;} element.
 */
@XmlRootElement
@XmlType(propOrder = {"title", "link", "description", "lastBuildDate", "generator", "docs", "image", "items",
        "categories", "cloud", "copyright", "language", "managingEditor", "pubDate", "rating", "ttl", "webMaster"})
public class Channel {

    // Private Constants
    private static final String GENERATOR = "jaxb";
    private static final String DOCS = "http://cyber.law.harvard.edu/rss/rss.html";
    
    // Class attributes
    private String title;
    private String link;
    private String description;
    
    private String language;
    private String copyright;
    private String managingEditor;
    private String webMaster;
    private Date pubDate;
    private Date lastBuildDate;
    private final List<Category> categories = new ArrayList<Category>();
    private final String generator = GENERATOR;
    private final String docs = DOCS;
    private Cloud cloud;
    private Integer ttl;
    private Image image;
    private String rating;

    private final List<Item> items = new ArrayList<Item>();

    // Getters and Setters
    /** Gets the Channel's items
     * @return Returns the items
     */
    @XmlElement(name = "item")
    public List<Item> getItems() {
        return this.items;
    }
    /** Adds an item to the channel 
     * @param item The item to add.
     */
    public void addItem(Item item) {
        this.items.add(item);
    }    
    /** Gets the Channel's categories.
     * @return Returns the categories.
     */
    @XmlElement(name = "category")
    public List<Category> getCategories() {
        return categories;
    }
    /** Adds a category that the channel belongs to. 
     * @param category The category to add.
     */
    public void addCategory(Category category) {
        this.categories.add(category);
    }
    /** Gets the cloud of the channel.
     * @return Returns the cloud.
     */
//    @XmlElement
    public Cloud getCloud() {
        return cloud;
    }
    /** Sets the cloud of the channel.
     * @param cloud The cloud to set.
     */
    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }
    /** Gets the copyright notice for content in the channel.
     * @return Returns the copyright.
     */
    @XmlElement
    public String getCopyright() {
        return copyright;
    }
    /** Sets the copyright notice for content in the channel.
     * @param copyright The copyright to set.
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    /** Gets the Channel's description.
     * @return Returns the description.
     */
    @XmlElement(required = true)
    public String getDescription() {
        return description;
    }
    /** Sets a phrase or sentence describing the channel (REQUIRED).
     * @exception InvalidRequiredParamException if some parameter passed is invalid
     * @param description The description to set.
     */
    public void setDescription(String description) {
        if(description == null || "".equals(description.trim())) {
            throw new InvalidRequiredParamException("description required: "+description);
        }
        this.description = description;
    }
    /** Gets the channel's image.
     * @return Returns the image.
     */
    @XmlElement
    public Image getImage() {
        return image;
    }
    /** Sets an image that can be displayed with the channel.
     * @param image The image to set.
     */
    public void setImage(Image image) {
        this.image = image;
    }
    /** Gets the language the channel is written in.
     * @return Returns the language.
     */
    @XmlElement
    public String getLanguage() {
        return language;
    }
    /** Sets the language the channel is written in.
     * <p>A list of allowable values for this element, provided by W3C, is
     * <a href="http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes" target="_blank">here</a>.
     * @param language The language to set.
     */
    public void setLanguage(String language) {
        this.language = language;
    }
    /** Gets the last time the content of the channel changed.
     * @return Returns the lastBuildDate.
     */
    @XmlElement
    @XmlJavaTypeAdapter(RssDateAdaper.class)
    public Date getLastBuildDate() {
        return lastBuildDate;
    }
    /** Sets the last time the content of the channel changed.
     * @param lastBuildDate The lastBuildDate to set.
     */
    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }
    /** Gets the corresponding website link of the Channel.
     * @return Returns the link.
     */
    @XmlElement(required = true)
    public String getLink() {
        return link;
    }
    /** Sets the URL to the HTML website corresponding to the channel (REQUIRED).
     * @exception InvalidRequiredParamException if some parameter passed is invalid
     * @param link The link to set.
     */
    public void setLink(String link) {
        if(link == null || "".equals(link.trim())) {
            throw new InvalidRequiredParamException("link required: "+link);
        }
        this.link = link;
    }
    /** Gets the <em>email</em> address for person responsible for editorial content.
     * @return Returns the managingEditor's email.
     */
    @XmlElement
    public String getManagingEditor() {
        return managingEditor;
    }
    /** Sets the <em>email</em> address for person responsible for editorial content.
     * @param email The managingEditor's email to set.
     */
    public void setManagingEditor(String email) {
        this.managingEditor = email;
    }
    /** Gets the publication date for the content in the channel.
     * @return Returns the pubDate.
     */
    @XmlElement
    @XmlJavaTypeAdapter(RssDateAdaper.class)
    public Date getPubDate() {
        return pubDate;
    }
    /** Sets the publication date for the content in the channel.
     * @param pubDate The pubDate to set.
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
    /** Gets the PICS rating for the channel.
     * @return Returns the rating.
     */
    @XmlElement
    public String getRating() {
        return rating;
    }
    /** Sets the PICS rating for the channel.
     * <p>Example:<br>{@code (PICS-1.1 &quot;http://www.classify.org/safesurf/&quot; l r (SS~~000 1))}</p>
     * <p>More info about the <em>Platform for Internet Content Selection</em> (PICS) can be found 
     * <a href="http://www.w3.org/PICS/" target="_blank">here</a>.</p>
     * @param rating The rating to set.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }
    /** Gets the Channel's title.
     * @return Returns the title.
     */
    @XmlElement(required = true)
    public String getTitle() {
        return title;
    }
    /** Sets the name of the channel (REQUIRED).
     * <p>It's how people refer to your service. If you have an HTML website 
     * that contains the same information as your RSS file, the title of your 
     * channel should be the same as the title of your website.</p>
     * @exception InvalidRequiredParamException if some parameter passed is invalid
     * @param title The title to set.
     */
    public void setTitle(String title) {
        if(title == null || "".equals(title.trim())) {
            throw new InvalidRequiredParamException("title required: "+title);
        }
        this.title = title;
    }
    /** Gets the channel's <em>time to live</em>.
     * @return Returns the ttl.
     */
    @XmlElement
    public Integer getTtl() {
        return ttl;
    }
    /** Sets the channel's <em>time to live</em>.
     * <p>It's the number of <em>minutes</em> that indicates how long a channel can be cached before refreshing from the source.</p>
     * @param ttl The ttl in <em>minutes</em> to set.
     */
    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }
    /** Gets the <em>email</em> address for person responsible for technical issues relating to channel.
     * @return Returns the webMaster's email.
     */
    @XmlElement
    public String getWebMaster() {
        return webMaster;
    }
    /** Sets the <em>email</em> address for person responsible for technical issues relating to channel.
     * @param email The webMaster's email to set.
     */
    public void setWebMaster(String email) {
        this.webMaster = email;
    }
    /** Gets the URL that points to the documentation for the format used in the generation of the RSS file.
     * @return Returns the docs' URL.
     */
    @XmlElement
    public String getDocs() {
        return docs;
    }
    /** Gets the string indicating the name of this generator
     * @return Returns the generator.
     */
    @XmlElement
    public String getGenerator() {
        return generator;
    }
}
