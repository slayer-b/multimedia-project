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

package com.multimedia.jobs;

import com.multimedia.service.impl.RssService;
import com.multimedia.service.impl.SitemapService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * User: slayer
 * Date: 01.06.12
 */
public class XmlCreationJobs {
    private final RssService rssService;
    private final SitemapService sitemapService;

    public XmlCreationJobs(RssService rssService, SitemapService sitemapService) {
        this.rssService = rssService;
        this.sitemapService = sitemapService;
    }

    @Scheduled(fixedRate = 86400000, initialDelay = 10000)
    public void create() {
        rssService.create();
    }

    @Scheduled(fixedRate = 86400000, initialDelay = 10000)
    public void createSitemap() {
        sitemapService.createSitemap();
    }
}
