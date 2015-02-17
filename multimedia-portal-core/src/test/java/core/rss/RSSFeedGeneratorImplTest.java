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

import core.rss.elem.Channel;
import core.rss.elem.Enclosure;
import core.rss.elem.Image;
import core.rss.elem.Item;
import core.rss.elem.RSS;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

public class RSSFeedGeneratorImplTest {

    static File exampleFile;
    File xmlFile;
    RSSFeedGeneratorImpl rssFeedGenerator;

    @BeforeClass
    public static void setUpClass() throws Exception {
        exampleFile = File.createTempFile("example-", ".xml");
        FileUtils.write(exampleFile, resultXml(), "UTF-8");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        FileUtils.deleteQuietly(exampleFile);
    }

    @Before
    public void setUp() throws Exception {
        xmlFile = File.createTempFile("rss-", ".xml");
        rssFeedGenerator = new RSSFeedGeneratorImpl();
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteQuietly(xmlFile);
    }

    @Test
    public void testGenerateToFile() throws Exception {
        rssFeedGenerator.generateToFile(rss(), xmlFile, "UTF-8");
        assertTrue("Rss file not created", xmlFile.exists());
        assertTrue("Rss gen and example files have different content", FileUtils.contentEquals(exampleFile, xmlFile));
    }

    RSS rss() {
        RSS rss = new RSS();
        rss.addChannel(channel());
        return rss;
    }

    Channel channel() {
        Channel channel = new Channel();
        channel.setTitle("Скачать картинки бесплатно и без регистрации");
        channel.setLink("http://download-multimedia.com/");
        channel.setDescription("Здесь Вы можете скачать картинки абсолютно бесплатно и без регистрации. Мультимедийный портал Download-Multimedia.com дает возможность не только скачать картинки, но и добавить собственные. Если Вы еще не нашли сайт на котором можно скачать картинки бесплатно и без регистрации - Вы пришли по адресу. Теги: Скачать картинки, Мультимедийный портал");
        channel.setImage(image());
        channel.addItem(item());
        channel.setLastBuildDate(new Date(LAST_BUILD_DATE));
        return channel;
    }

    Image image() {
        Image image = new Image();
        image.setUrl("http://download-multimedia.com/img/top/logo.jpg");
        image.setTitle("Скачать картинки бесплатно и без регистрации");
        image.setLink("http://download-multimedia.com/");
        return image;
    }

    Item item() {
        Item item = new Item();
        item.setTitle("test");
        item.setLink("http://download-multimedia.com/index.htm?id_pages_nav=1186&id_photo_nav=13586");
        item.setDescription("test.jpg");
        Enclosure enclosure = new Enclosure();
        enclosure.setLength(48763L);
        enclosure.setType("image/jpeg");
        enclosure.setUrl("http://download-multimedia.com/images/wallpaper/medium/test.jpg");
        item.setEnclosure(enclosure);
        item.setPubDate(new Date(PUB_DATE));
        return item;
    }

    static final long LAST_BUILD_DATE = 1340301798000L;
    static final long PUB_DATE = 1339968935000L;

    static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    static String getDateFormatted(long millis) {
        return sdf.format(new Date(millis));
    }

    static String resultXml() {
        return
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<rss version=\"2.0\">\n" +
            "<channel>\n" +
            "<title>Скачать картинки бесплатно и без регистрации</title>\n" +
            "<link>http://download-multimedia.com/</link>\n" +
            "<description>Здесь Вы можете скачать картинки абсолютно бесплатно и без регистрации. Мультимедийный портал Download-Multimedia.com дает возможность не только скачать картинки, но и добавить собственные. Если Вы еще не нашли сайт на котором можно скачать картинки бесплатно и без регистрации - Вы пришли по адресу. Теги: Скачать картинки, Мультимедийный портал</description>\n" +
            "<lastBuildDate>" + getDateFormatted(LAST_BUILD_DATE) + "</lastBuildDate>\n" +
            "<generator>jaxb</generator>\n" +
            "<docs>http://cyber.law.harvard.edu/rss/rss.html</docs>\n" +
            "<image>\n" +
            "<url>http://download-multimedia.com/img/top/logo.jpg</url>\n" +
            "<title>Скачать картинки бесплатно и без регистрации</title>\n" +
            "<link>http://download-multimedia.com/</link>\n" +
            "</image>\n" +
            "<item>\n" +
            "<title>test</title>\n" +
            "<link>http://download-multimedia.com/index.htm?id_pages_nav=1186&amp;id_photo_nav=13586</link>\n" +
            "<description>test.jpg</description>\n" +
            "<enclosure url=\"http://download-multimedia.com/images/wallpaper/medium/test.jpg\" type=\"image/jpeg\" length=\"48763\"/>\n" +
            "<pubDate>" + getDateFormatted(PUB_DATE) + "</pubDate>\n" +
            "</item>\n" +
            "</channel>\n" +
            "</rss>\n";
    }
}
