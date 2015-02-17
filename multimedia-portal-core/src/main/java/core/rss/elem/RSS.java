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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the <code>&lt;rss&gt;</code> root element.
 */
@XmlRootElement
public class RSS {
    
    /**
     * Constant to specify version 2.0 for the generated feed.
     */
    public static final String VERSION_2_0 = "2.0";
    
    private final String version = VERSION_2_0;
    private final List<Channel> channels = new ArrayList<Channel>();

    /** Gets this RSS Feed version
     * @return Returns the version.
     */
    @XmlAttribute
    public String getVersion() {
        return version;
    }

    /** Gets the channel elements of this RSS Feed
     * @return Returns the channels.
     */
    @XmlElement(name = "channel")
    public List<Channel> getChannels() {
        return channels;
    }
    /** Add a channel to this RSS Feed
     * @param channel The channel to add.
     */
    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }
}
