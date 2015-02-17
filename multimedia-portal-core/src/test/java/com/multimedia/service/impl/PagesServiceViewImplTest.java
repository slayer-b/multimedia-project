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

package com.multimedia.service.impl;

import com.multimedia.model.beans.Advertisement;
import com.multimedia.model.beans.AdvertisementPages;
import com.multimedia.model.beans.Counter;
import com.multimedia.service.IAdvertisementPagesService;
import com.multimedia.service.IAdvertisementService;
import com.multimedia.service.IPagesService;
import common.services.generic.IGenericService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PagesServiceViewImplTest {
    private PagesServiceViewImpl pagesServiceViewImpl;

    private IGenericService<Counter, Long> counterService;
    private IAdvertisementService advertisementService;
    private IAdvertisementPagesService advertisementPagesService;
    private IPagesService pagesService;

    private Ehcache cacheAdvertisement;
    private Ehcache cacheAdvertisementPages;

    private static final Long pageId = 1L;
    private static final Long[] parentIds = {2L, 3L, 4L};

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        counterService = Mockito.mock(IGenericService.class);
        advertisementService = Mockito.mock(IAdvertisementService.class);
        advertisementPagesService = Mockito.mock(IAdvertisementPagesService.class);
        pagesService = Mockito.mock(IPagesService.class);
        CacheManager cacheManager = Mockito.mock(CacheManager.class);
        cacheAdvertisement = Mockito.mock(Ehcache.class);
        cacheAdvertisementPages = Mockito.mock(Ehcache.class); 

        when(cacheManager.getEhcache(PagesServiceViewImpl.ADVERTISEMENT_PAGES_REGION))
            .thenReturn(cacheAdvertisementPages);
        when(cacheManager.getEhcache(PagesServiceViewImpl.ADVERTISEMENT_REGION))
            .thenReturn(cacheAdvertisement);

        fillAdvertisements();

        pagesServiceViewImpl = new PagesServiceViewImpl(counterService, advertisementService,
                advertisementPagesService, pagesService, cacheManager);
    }

    @Test
    public void testGetAdvertisementsForChildrenNoParent() {
        proxyParent(pageId, null);
        proxyCommonAds(1L, 2L);
        proxyAdvForPage(pageId, 
                advPage(3L, Boolean.TRUE, null),
                advPage(4L, Boolean.TRUE, null));

        Set<Long> res = pagesServiceViewImpl.getAdvertisementsForChildren(pageId);
        assertEquals(2, res.size());
        Iterator<Long> i = res.iterator();
        assertEquals(1L, i.next().longValue());
        assertEquals(2L, i.next().longValue());
    }

    @Test
    /** case when parent and page do not have common ads. */
    public void testGetAdvertisementsForChildrenWithParentSimple() {
        proxyParent(pageId, parentIds[0]);
        proxyAdvForPage(pageId,
                advPage(1L, Boolean.TRUE, null),
                advPage(2L, Boolean.TRUE, null));
        proxyAdvForPage(parentIds[0],
                advPage(3L, Boolean.TRUE, null),
                advPage(4L, Boolean.TRUE, null),
                advPage(5L, Boolean.FALSE, null));

        LinkedHashSet<Long> res = pagesServiceViewImpl.getAdvertisementsForChildren(pageId);
        assertEquals(2, res.size());
        Iterator<Long> i = res.iterator();
        assertEquals(3L, i.next().longValue());
        assertEquals(4L, i.next().longValue());
    }

    @Test
    /** case when parent and page have common ads. */
    public void testGetAdvertisementsForChildrenWithParent() {
        proxyParent(pageId, parentIds[0]);
        proxyAdvForPage(pageId,
                advPage(1L, Boolean.TRUE, null),
                advPage(2L, Boolean.FALSE, null));
        proxyAdvForPage(parentIds[0],
                advPage(2L, Boolean.TRUE, null),
                advPage(1L, Boolean.FALSE, null));

        Set<Long> res = pagesServiceViewImpl.getAdvertisementsForChildren(pageId);
        assertEquals(1, res.size());
        Iterator<Long> i = res.iterator();
        assertEquals(2L, i.next().longValue());
    }

    @Test
    /** case when does not have adv that is not useInChildren */
    public void testGetAdvertisementForPageNoParent() {
        proxyParent(pageId, null);
        proxyCommonAds(1L, 2L);
        proxyAdvForPage(pageId,
                advPage(3L, Boolean.TRUE, Boolean.TRUE),
                advPage(4L, Boolean.TRUE, Boolean.FALSE));

        Map<String, String> res = pagesServiceViewImpl.getAdvertisementForPage(pageId);
        assertEquals(3, res.size());
        assertEquals("1", res.get("top"));
        assertEquals("2", res.get("bot"));
        assertEquals("3", res.get("left"));
    }

    @Test
    /** case when has adv that is not useInChildren */
    public void testGetAdvertisementForPageNoParent2() {
        proxyParent(pageId, null);
        proxyAdvForPage(pageId, 
                advPage(1L, Boolean.TRUE, Boolean.TRUE),
                advPage(2L, Boolean.TRUE, Boolean.FALSE),
                advPage(3L, Boolean.FALSE, Boolean.TRUE),
                advPage(4L, Boolean.FALSE, Boolean.FALSE));

        Map<String, String> res = pagesServiceViewImpl.getAdvertisementForPage(pageId);
        assertEquals(2, res.size());
        assertEquals("1", res.get("top"));
        assertEquals("3", res.get("left"));
    }

    @Test
    /** most simple case */
    public void testGetAdvertisementForPageWithParent() {
        proxyParent(pageId, parentIds[0]);
        proxyAdvForPage(pageId, 
                advPage(1L, null, Boolean.TRUE),
                advPage(2L, null, Boolean.FALSE));
        proxyAdvForPage(parentIds[0], 
                advPage(1L, Boolean.TRUE, null),
                advPage(2L, Boolean.TRUE, null));

        Map<String, String> res = pagesServiceViewImpl.getAdvertisementForPage(pageId);
        assertEquals(1, res.size());
        assertEquals("1", res.get("top"));
    }

    @Test
    /** show one advertisement in parent and other in child */
    public void testGetAdvertisementForPageWithParent2() {
        proxyParent(pageId, parentIds[0]);
        proxyAdvForPage(pageId, 
                advPage(5L, null, Boolean.TRUE));
        proxyAdvForPage(parentIds[0], 
                advPage(6L, Boolean.FALSE, Boolean.TRUE));

        Map<String, String> childAds = pagesServiceViewImpl.getAdvertisementForPage(pageId);
        assertEquals(1, childAds.size());
        assertEquals("5", childAds.get("top"));
        Map<String, String> parentAds = pagesServiceViewImpl.getAdvertisementForPage(parentIds[0]);
        assertEquals(1, parentAds.size());
        assertEquals("6", parentAds.get("top"));
    }

    @Test
    /** show advertisement only in children */
    public void testGetAdvertisementForPageWithParent3() {
        proxyParent(pageId, parentIds[0]);
        proxyAdvForPage(pageId);
        proxyAdvForPage(parentIds[0], 
                advPage(6L, Boolean.TRUE, Boolean.FALSE));

        Map<String, String> childAds = pagesServiceViewImpl.getAdvertisementForPage(pageId);
        assertEquals(1, childAds.size());
        assertEquals("6", childAds.get("top"));
        Map<String, String> parentAds = pagesServiceViewImpl.getAdvertisementForPage(parentIds[0]);
        assertEquals(0, parentAds.size());
    }

    private void proxyAdvForPage(Long id, AdvertisementPages... ads) {
        when(advertisementPagesService.getByPropertyValueOrdered(null,
                    "id_pages", id,
                    null, null))
            .thenReturn(Arrays.asList(ads));
    }

    private void proxyParent(Long myId, Long parentId) {
        when(pagesService.getSinglePropertyU("id_pages", "id", myId)).thenReturn(parentId);
    }

    private void proxyCommonAds(Long... adsIds) {
        when(advertisementService.getSingleProperty("id",
                new String[]{"active", "size(advertisementPages)"},
                new Object[]{Boolean.TRUE, 0},
                0, 0, null, null))
            .thenReturn(Arrays.asList(adsIds));
    }

    private static AdvertisementPages advPage(Long id_advertisement, Boolean useInChildren, Boolean useInParent) {
        AdvertisementPages ad = new AdvertisementPages();
        ad.setId_advertisement(id_advertisement);
        ad.setUseInChildren(useInChildren);
        ad.setUseInParent(useInParent);
        return ad;
    }

    private void fillAdvertisements() {
        when(advertisementService.getById(1L)).thenReturn(adv(1L, "top", "1"));
        when(advertisementService.getById(2L)).thenReturn(adv(2L, "bot", "2"));
        when(advertisementService.getById(3L)).thenReturn(adv(3L, "left", "3"));
        when(advertisementService.getById(4L)).thenReturn(adv(4L, "right", "4"));
        when(advertisementService.getById(5L)).thenReturn(adv(5L, "top", "5"));
        when(advertisementService.getById(6L)).thenReturn(adv(6L, "top", "6"));
    }

    private Advertisement adv(Long id, String position, String text) {
        Advertisement adv = new Advertisement();
        adv.setActive(Boolean.TRUE);
        adv.setId(id);
        adv.setPosition(position);
        adv.setText(text);
        return adv;
    }
}
