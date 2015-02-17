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
 * Class to define the {@code &lt;cloud&gt;} optional sub-element of <code>&lt;channel&gt;</code>.
 * <p>It specifies a web service that supports the rssCloud interface which can be implemented in HTTP-POST, XML-RPC or SOAP 1.1.</p>
 * <p>Its purpose is to allow processes to register with a cloud to be notified of updates to the channel, implementing a lightweight publish-subscribe protocol for RSS feeds.</p>
 * <blockquote>{@code &lt;cloud domain=&quot;rpc.sys.com&quot; port=&quot;80&quot; path=&quot;/RPC2&quot; registerProcedure=&quot;myCloud.rssPleaseNotify&quot; protocol=&quot;xml-rpc&quot; /&gt;}</blockquote>
 * <p>In this example, to request notification on the channel it appears in, you would send an XML-RPC message to rpc.sys.com on port 80, with a path of /RPC2. The procedure to call is myCloud.rssPleaseNotify.</p>
 * A full explanation of this element and the rssCloud interface is <a href="http://blogs.law.harvard.edu/tech/soapMeetsRss#rsscloudInterface" target="_blank">here</a>.
 */
@XmlRootElement
public class Cloud {
    private String domain;
    private int port;
    private String path;
    private String registerProcedure;
    private String protocol;

    public static final String XML_RPC_PROTOCOL = "xml-rpc";

    public static final String SOAP_PROTOCOL = "soap";

    @XmlElement(required = true)
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @XmlElement(required = true)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlElement
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if(port <= 0 || port > 65000) {
            throw new InvalidRequiredParamException("port must be between 1 and 65000: "+port);
        }
        this.port = port;
    }

    @XmlElement
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        if(!XML_RPC_PROTOCOL.equals(protocol) || !SOAP_PROTOCOL.equals(protocol) ) {
            throw new InvalidRequiredParamException("protocol must be '"+XML_RPC_PROTOCOL+"' or '"+SOAP_PROTOCOL+"': "+protocol);
        }
        this.protocol = protocol;
    }

    @XmlElement
    public String getRegisterProcedure() {
        return registerProcedure;
    }

    public void setRegisterProcedure(String registerProcedure) {
        if(registerProcedure == null || "".equals(registerProcedure.trim())) {
            throw new InvalidRequiredParamException("registerProcedure required: "+registerProcedure);
        }
        this.registerProcedure = registerProcedure;
    }
}
