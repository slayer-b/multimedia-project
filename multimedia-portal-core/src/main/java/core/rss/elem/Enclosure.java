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


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class to define the <code>&lt;enclosure&gt;</code> optional sub-element of {@code &lt;item&gt;}.
 * <p>It can describe any type of file, enabling the aggregator can know in advance, without having
 * to do any communication, what it's going to get, and apply scheduling and filtering rules.<br>
 * An example is:</p>
 * <blockquote>{@code &lt;enclosure url=&quot;http://mp3.centurymedia.com/krisiun_Murderer_WorksofCarnage.mp3&quot; length=&quot;2629133&quot; type=&quot;audio/mpeg&quot;/>}</blockquote>
 */
@XmlRootElement
public class Enclosure {
    private String url;
    private long length;
    private String type;

    @XmlAttribute(required = true)
    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        if(length <= 0) {
            throw new InvalidRequiredParamException("length must be a positive non-zero value: "+length);
        }
        this.length = length;
    }

    @XmlAttribute(required = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(required = true)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlValue
    public String getValue() {
        return null;
    }
}
