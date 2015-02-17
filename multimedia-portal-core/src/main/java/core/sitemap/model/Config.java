/*
 *  Copyright 2010 demchuck.dima@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package core.sitemap.model;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public final class Config {
    static final char[] META = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".toCharArray();
    static final char[] INDEX_START_TAG = "<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n".toCharArray();
    static final char[] INDEX_END_TAG = "</sitemapindex>".toCharArray();

    static final char[] URL_START_TAG = "<url>".toCharArray();
    static final char[] URL_END_TAG = "</url>\n".toCharArray();

    static final char[] URLSET_START_TAG = "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n".toCharArray();
    static final char[] URLSET_END_TAG = "</urlset>".toCharArray();

    static final char[] SITEMAP_START_TAG = "<sitemap>".toCharArray();
    static final char[] SITEMAP_END_TAG = "</sitemap>\n".toCharArray();

    static final char[] LOC_START_TAG = "<loc>".toCharArray();
    static final char[] LOC_END_TAG = "</loc>".toCharArray();

    static final char[] LASTMOD_START_TAG = "<lastmod>".toCharArray();
    static final char[] LASTMOD_END_TAG = "</lastmod>".toCharArray();

    static final char[] CHANGEFREQ_START_TAG = "<changefreq>".toCharArray();
    static final char[] CHANGEFREQ_END_TAG = "</changefreq>".toCharArray();

    static final char[] PRIORITY_START_TAG = "<priority>".toCharArray();
    static final char[] PRIORITY_END_TAG = "</priority>".toCharArray();

	public static final String SITEMAP_PREFIX = "sitemap_";
	public static final String XML_SUFFIX = ".xml";
	public static final String INDEX_PREFIX = "sitemaps_";

	private Config(){}
}
