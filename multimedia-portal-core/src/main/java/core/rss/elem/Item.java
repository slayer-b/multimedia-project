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
 * Class that represent the {@code &lt;item&gt;} element.
 */
@XmlRootElement
@XmlType(propOrder = {"title", "link", "description", "enclosure", "pubDate",
        "author", "categories", "comments", "guid", "source"})
public class Item {

    private String title;
    private String link;
    private String description;

    private String author;
    private final List<Category> categories = new ArrayList<Category>();
    private String comments;
    private Enclosure enclosure;
    private Guid guid;
    private Date pubDate;
    private Source source;
    
    /** Gets the email address of the author of the item
     * @return Returns the author.
     */
    @XmlElement
    public String getAuthor() {
        return author;
    }
    /** Sets the email address of the author of the item
     * @param author The author to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /** Gets the URL of a page for comments relating to the item
     * @return Returns the comments.
     */
    @XmlElement
    public String getComments() {
        return comments;
    }
    /** Sets the URL of a page for comments relating to the item
     * @param comments The comments to set.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /** Gets the item synopsis
     * @return Returns the description.
     */
    @XmlElement(required = true)
    public String getDescription() {
        return description;
    }
    /** Sets the item synopsis
     * @exception InvalidRequiredParamException if some parameter passed is invalid
     * @param description The description to set.
     */
    public void setDescription(String description) {
        if((description == null || "".equals(description.trim())) && 
                (title == null || "".equals(title.trim()))) {
            throw new InvalidRequiredParamException("title or description required: "+title+' '+description);
        }
        this.description = description;
    }
    /** Gets an enclosure describing a media object that is attached to the item
     * @return Returns the enclosure.
     */
    @XmlElement
    public Enclosure getEnclosure() {
        return enclosure;
    }
    /** Sets an enclosure describing a media object that is attached to the item
     * @param enclosure The enclosure to set.
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }
    /** Gets the guid that uniquely identifies the item
     * @return Returns the guid.
     */
    @XmlElement
    public Guid getGuid() {
        return guid;
    }
    /** Sets a guid that uniquely identifies the item
     * @param guid The guid to set.
     */
    public void setGuid(Guid guid) {
        this.guid = guid;
    }
    /** Gets the URL of the item
     * @return Returns the link.
     */
    @XmlElement
    public String getLink() {
        return link;
    }
    /** Sets the URL of the item
     * @param link The link to set.
     */
    public void setLink(String link) {
        this.link = link;
    }
    /** Gets the date that indicates when the item was published
     * @return Returns the pubDate.
     */
    @XmlElement
    @XmlJavaTypeAdapter(RssDateAdaper.class)
    public Date getPubDate() {
        return pubDate;
    }
    /** Sets a date that indicates when the item was published
     * @param pubDate The pubDate to set.
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
    /** Gets the source info of the channel that the item came from 
     * @return Returns the source.
     */
    @XmlElement
    public Source getSource() {
        return source;
    }
    /** Sets the source info of the channel that the item came from
     * @param source The source to set.
     */
    public void setSource(Source source) {
        this.source = source;
    }
    /** Gets the title of the item
     * @return Returns the title.
     */
    @XmlElement(required = true)
    public String getTitle() {
        return title;
    }
    /** Sets the title of the item
     * @exception InvalidRequiredParamException if some parameter passed is invalid
     * @param title The title to set.
     */
    public void setTitle(String title) {
        if((description == null || "".equals(description.trim())) && 
                (title == null || "".equals(title.trim()))) {
            throw new InvalidRequiredParamException("title or description required: " + title + ' ' + description);
        }
        this.title = title;
    }
    /** Gets the item's categories
     * @return Returns the categories.
     */
    @XmlElement
    public List<Category> getCategories() {
        return categories;
    }
    /** Adds a category that the item belongs to.
     */
    public void addCategory(Category category) {
        this.categories.add(category);
    }
    
}