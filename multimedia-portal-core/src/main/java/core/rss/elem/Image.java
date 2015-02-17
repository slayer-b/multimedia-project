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


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class to define the {@code &lt;image&gt;} optional sub-element of <code>&lt;channel&gt;</code>.
 * It specifies a GIF, JPEG or PNG image that can be displayed with the channel.
 */
@XmlRootElement
@XmlType(propOrder = {"url", "title", "link", "description", "height", "width"})
public class Image {
    /**
     * Constant indicating the max width value of the image
     */
    public static final int MAX_WIDTH = 144;
    /**
     * Constant indicating the max height value of the image
     */
    public static final int MAX_HEIGHT = 400;

    private String url;
    private String title;
    private String link;
    private Integer width;
    private Integer height;
    private String description;

    /**
     * Gets the Image description
     *
     * @return Returns the description.
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Image description
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the Image height
     *
     * @return Returns the height.
     */
    @XmlElement
    public Integer getHeight() {
        return height;
    }

    /**
     * Sets the Image height
     *
     * @param height The height to set.
     * @throws InvalidRequiredParamException if some parameter passed is invalid
     */
    public void setHeight(Integer height) {
        if (height != null && (height < 0 || height > MAX_HEIGHT)) {
            throw new InvalidRequiredParamException("height must be between 0 and " + MAX_HEIGHT + ": " + height);
        }
        this.height = height;
    }

    /**
     * Gets the Image link
     *
     * @return Returns the link.
     */
    @XmlElement(required = true)
    public String getLink() {
        return link;
    }

    /**
     * Sets the Image link
     *
     * @param link The link to set.
     * @throws InvalidRequiredParamException if some parameter passed is invalid
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the Image title
     *
     * @return Returns the title.
     */
    @XmlElement(required = true)
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Image title
     *
     * @param title The title to set.
     * @throws InvalidRequiredParamException if some parameter passed is invalid
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the Image url
     *
     * @return Returns the url.
     */
    @XmlElement(required = true)
    public String getUrl() {
        return url;
    }

    /**
     * Sets the Image url
     *
     * @param url The url to set.
     * @throws InvalidRequiredParamException if some parameter passed is invalid
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the Image width
     *
     * @return Returns the width.
     */
    @XmlElement
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the Image width
     *
     * @param width The width to set.
     * @throws InvalidRequiredParamException if some parameter passed is invalid
     */
    public void setWidth(Integer width) {
        if (width != null && (width < 0 || width > MAX_WIDTH)) {
            throw new InvalidRequiredParamException("width must be between 0 and " + MAX_WIDTH + ": " + width);
        }
        this.width = width;
    }
}
