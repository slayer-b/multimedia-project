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

/**
 * Class to define the <code>&lt;guid&gt;</code> optional sub-element of {@code &lt;item&gt;}.
 * <p>GUID stands for Globally Unique IDentifier. It's a string that uniquely identifies the item.
 * When present, an aggregator may choose to use this string to determine if an item is new.</p>
 * <blockquote>{@code &lt;guid&gt;http://some.server.com/weblogItem3207&lt;/guid&gt;}</blockquote>
 * <p>There are no rules for the syntax of a guid. Aggregators must view them as a string.
 * It's up to the source of the feed to establish the uniqueness of the string.</p>
 * <p>If the {@code &lt;guid&gt;} element has an attribute named <code>isPermaLink</code> with a value of <code>true</code>, the reader may
 * assume that it is a permalink to the item, that is, a url that can be opened in a Web browser,
 * that points to the full item described by the {@code &lt;item&gt;} element. An example:</p>
 * <blockquote>{@code &lt;guid isPermaLink=&quot;true&quot;>http://some.server.com/weblogItem3207&lt;/guid&gt;}</blockquote>
 * <p><code>isPermaLink</code> is optional, its default value is <code>true</code>. If its value is {@code false},
 * the guid may not be assumed to be a url, or a url to anything in particular.</p>
 *
 */
@XmlRootElement
public class Guid {

    private String id;
    private boolean permaLink;

    @XmlElement(required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(defaultValue = "true")
    public boolean isPermaLink() {
        return permaLink;
    }

    public void setPermaLink(boolean permaLink) {
        this.permaLink = permaLink;
    }
}
