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

package core.rss;

import core.rss.elem.RSS;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class RSSFeedGeneratorImpl implements RSSFeedGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSSFeedGeneratorImpl.class);

    //Little hack to remove indent in result xml
    private static final String INDENT_ATTR = "com.sun.xml.internal.bind.indentString";

    @Override
    public void generateToFile(RSS rss, File xmlFile, String encoding) {
        try {
            JAXBContext context = JAXBContext.newInstance(RSS.class);
            Marshaller m = context.createMarshaller();
//            m.setEventHandler(new DefaultValidationEventHandler());
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_ENCODING, ("UTF-8"));
            m.setProperty(INDENT_ATTR, "");
            m.setAdapter(new RssDateAdaper());
            if (xmlFile.exists() || xmlFile.createNewFile()) {
                //this is required to generate empty tags.(without end tag)
                Writer writer = new FileWriterWithEncoding(xmlFile, "UTF-8");
                m.marshal(rss, writer);
                writer.close();
            }
        } catch (JAXBException e) {
            LOGGER.error("Error while creating JAXBContext", e);
        } catch (IOException e) {
            LOGGER.error("Error while creating result file", e);
        }
    }
}
