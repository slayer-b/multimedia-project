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
import com.multimedia.service.IPagesServiceView;
import common.services.generic.IGenericService;
import gallery.model.beans.Pages;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service
public class PagesServiceViewImpl implements IPagesServiceView {
	private final Logger logger = LoggerFactory.getLogger(PagesServiceViewImpl.class);

	private final IGenericService<Counter, Long> counterService;
    private final IAdvertisementService advertisementService;
    private final IAdvertisementPagesService advertisementPagesService;
    private final IPagesService pagesService;

    private Ehcache cacheAdvertisement;
    private Ehcache cacheAdvertisementPages;

	/** stores result for view render i.e. pairs region -- text for page */
    static final String ADVERTISEMENT_REGION = "advertisement";
	/** stores result for children i.e. set of available advertisement ids for page */
    static final String ADVERTISEMENT_PAGES_REGION = "advertisement_pages";

	private static final String[] COUNTERS_ORDER_BY = {"sort"};
	private static final String[] COUNTERS_ORDER_HOW = {"ASC"};

	private static final String[] MAIN_PSEUDONYMES = {"id_pages","active"};
	private static final Object[] MAIN_VALUES = {null,Boolean.TRUE};

    @Autowired
	public PagesServiceViewImpl(IGenericService<Counter, Long> counterService,
            IAdvertisementService advertisementService,
            IAdvertisementPagesService advertisementPagesService,
            IPagesService pagesService,
            CacheManager cacheManager)
	{
        this.counterService = counterService;
        this.advertisementService = advertisementService;
        this.advertisementPagesService = advertisementPagesService;
        this.pagesService = pagesService;

        cacheAdvertisement = cacheManager.getEhcache(ADVERTISEMENT_REGION);
        cacheAdvertisementPages = cacheManager.getEhcache(ADVERTISEMENT_PAGES_REGION);
        if (cacheAdvertisement==null) {
            logger.warn("specified cacheManager has no region '{}', using default", ADVERTISEMENT_REGION);
            cacheManager.addCache(ADVERTISEMENT_REGION);
            cacheAdvertisement = cacheManager.getEhcache(ADVERTISEMENT_REGION);
        }
        if (cacheAdvertisementPages==null) {
            logger.warn("specified cacheManager has no region '{}', using default", ADVERTISEMENT_PAGES_REGION);
            cacheManager.addCache(ADVERTISEMENT_PAGES_REGION);
            cacheAdvertisementPages = cacheManager.getEhcache(ADVERTISEMENT_PAGES_REGION);
        }
    }

	@Override
	public List<Counter> getCounters() {
		return counterService.getOrdered(null, COUNTERS_ORDER_BY, COUNTERS_ORDER_HOW);
	}

	@Override
	public Map<String, String> getAdvertisementForPage(Long id) {
		Element cachedAdv = cacheAdvertisement.get(id);
		if (cachedAdv==null||cachedAdv.getValue()==null) {
			Set<Long> advertisementPages = new LinkedHashSet<Long>(getAdvertisementsForChildren(id));

			List<AdvertisementPages> allowDeny = advertisementPagesService.getByPropertyValueOrdered(null,
                    "id_pages", id,
                    null, null);//TODO: add sort

            //we need to make this because of ordering
			for (AdvertisementPages ap : allowDeny) {
                advertisementPages.remove(ap.getId_advertisement());
				if (ap.getUseInParent()) {
					advertisementPages.add(ap.getId_advertisement());
				}
			}

			//forming advertisements to show on page [position - advertisement.text]
			Map<String, String> m = new HashMap<String, String>();
			for (Long id_adv : advertisementPages) {
			    Advertisement adv = advertisementService.getById(id_adv);
				if (adv.getActive()) {
					m.put(adv.getPosition(), adv.getText());
				}
			}

			cacheAdvertisement.put(new Element(id, m));
			return m;
		} else {
			return (Map<String, String>)cachedAdv.getValue();
		}
	}

	/**
	 * get a set of ids for advertisements on given page
	 * !!!WARNING: this method is optimized for caching, so it does not contain advertisements that are not 'useInChildren'
	 * @param id id of pages
	 * @return set of id for advertisements that may be rendered on given page
	 */
	LinkedHashSet<Long> getAdvertisementsForChildren(Long id) {
        Long parentId = (Long) pagesService.getSinglePropertyU("id_pages", "id", id);
		Element cachedAdvPages = cacheAdvertisementPages.get(parentId);
		if (cachedAdvPages==null||cachedAdvPages.getValue()==null) {
			LinkedHashSet<Long> advertisementPages = new LinkedHashSet<Long>();
			if (parentId == null) {
                advertisementPages.addAll(commonAdvertisements());
			} else {
                advertisementPages.addAll(getAdvertisementsForChildren(parentId));

                List<AdvertisementPages> allowDeny = advertisementPagesService.getByPropertyValueOrdered(null,
                        "id_pages", parentId,
                        null, null);//TODO: add sort

                //we need to make this because of ordering
                for (AdvertisementPages ap : allowDeny) {
                    advertisementPages.remove(ap.getId_advertisement());
                    if (ap.getUseInChildren()) {
                        advertisementPages.add(ap.getId_advertisement());
                    }
                }
			}

			cacheAdvertisementPages.put(new Element(parentId, advertisementPages));
			return advertisementPages;
		} else {
			return (LinkedHashSet<Long>)cachedAdvPages.getValue();
		}
	}

	@SuppressWarnings("unchecked")
	/** get all advertisement that may be used in all pages. */
    private List<Long> commonAdvertisements() {
	    return advertisementService.getSingleProperty("id",
                new String[]{"active", "size(advertisementPages)"},
                new Object[]{Boolean.TRUE, 0},
                0, 0, null, null);//TODO: add sort
	}

    @Override
    public Pages getMainPage(){
        List<Pages> temp = pagesService.getByPropertiesValueOrdered(null, MAIN_PSEUDONYMES, MAIN_VALUES, null, null);
        if (temp.isEmpty()) {
            return null;
        } else {
            return temp.get(0);
        }
    }
}
