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
package com.multimedia.web.support;

import common.beans.PagerBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author demchuck.dima@gmail.com
 */
public class PaginatedListUtils {

    /**
     * @param itemsPerPage   quantity of items per page
     * @param navigationSize quantity of pages left and right of current page (navigation)
     */
    public PaginatedListUtils(int itemsPerPage, int navigationSize) {
        this.itemsPerPage = itemsPerPage;
        this.navigationSize = navigationSize;
    }

    /**
     * http parameter name which stores information about page number in entity set
     */
    public static final String PAGE_NUMBER_NAME = "page_number";
    /**
     * default page number
     */
    public static final int DEFAULT_PAGE_NUMBER_VALUE = 0;
    /**
     * default page size
     */
    public static final int DEFAULT_PAGE_SIZE_VALUE = 1;
    private final int itemsPerPage;
    /**
     * quantity of pages to show for navigation
     */
    public static final int DEFAULT_PAGE_NUMBER_BOTH_SIDES = 2;
    private final int navigationSize;

    /**
     * get current page number, never throws an exception
     *
     * @return current page number
     */
    public static int getPageNumber(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter(PAGE_NUMBER_NAME));
        } catch (Exception nfe) {
            return DEFAULT_PAGE_NUMBER_VALUE;
        }
    }

    /**
     * get quantity of items that will be dispalyed on one page
     */
    public int getItemsPerPage() {
        return this.itemsPerPage;
    }

    /**
     * number of page that will be dispalyed if no page_number passed
     */
    public int getStartPageNumber() {
        return DEFAULT_PAGE_NUMBER_VALUE;
    }

    /**
     * get current page number, never throws an exception
     *
     * @return current page number
     */
    public static int getFirstItemNumber(HttpServletRequest request) {
        return getPageNumber(request) * DEFAULT_PAGE_SIZE_VALUE;
    }

    public PagerBean getPagerBean(Integer currentPage, int itemsCount, Integer itemsPerPage, String keepParameters) {
        return getPagerBean(
                getPageNumber(currentPage), itemsCount,
                getItemsPerPage(itemsPerPage), keepParameters);
    }

    int getPageNumber(Integer currentPage) {
        return currentPage == null ? DEFAULT_PAGE_NUMBER_VALUE : currentPage;
    }

    int getItemsPerPage(Integer itemsPerPage) {
        return itemsPerPage == null ? this.itemsPerPage : itemsPerPage;
    }

    /**
     * returns pager bean initialized by this class;
     * this method uses a rule: total pages eq 2*navigation_size+1,
     * but if less items left or right pages quantity will be reduced;
     * ex: if cur page is 3 and we need to draw 5 items in left we will draw only 2 items ...
     *
     * @param currentPage    current page from request
     * @param itemsCount     number of items for current request
     * @param keepParameters query string that will be added to all links
     * @return pager bean
     */
    public PagerBean getPagerBean(int currentPage, int itemsCount, int itemsPerPage, String keepParameters) {
        PagerBean count = getPagerBeanCommon(itemsCount, itemsPerPage, currentPage, keepParameters);

        count.setFirstPage(normalizeFirstPage(count.getCurrentPage()));
        count.setLastPage(normalizeLastPage(count.getCurrentPage(), count.getPageCount()));

        return count;
    }

    int normalizeFirstPage(int currentPage) {
        if (currentPage <= navigationSize) {
            return 0;
        } else {
            return currentPage - navigationSize;
        }
    }

    int normalizeLastPage(int currentPage, int pageCount) {
        if (pageCount <= navigationSize || currentPage + navigationSize >= pageCount) {
            return pageCount;
        } else {
            return currentPage + navigationSize;
        }
    }

    /**
     * returns pager bean initialized by this class;
     * this method uses a rule: total pages eq 2*navigation_size+1;
     * if we can't draw navigation we will draw an additional pages to another side ...
     *
     * @param currentPage    current page from request
     * @param itemsCount     number of items for current request
     * @param keepParameters query string that will be added to all links
     * @return pager bean
     */
    public PagerBean getPagerBean2(int currentPage, int itemsCount, String keepParameters) {
        return getPagerBean2(currentPage, itemsCount, itemsPerPage, keepParameters);
    }

    /**
     * returns pager bean initialized by this class;
     * this method uses a rule: total pages eq 2*navigation_size+1;
     * if we can't draw navigation we will draw an additional pages to another side ...
     *
     * @param currentPage    current page from request
     * @param itemsCount     number of items for current request
     * @param keepParameters query string that will be added to all links
     * @return pager bean
     */
    public PagerBean getPagerBean2(int currentPage, int itemsCount, int itemsPerPage, String keepParameters) {
        PagerBean count = getPagerBeanCommon(itemsCount, itemsPerPage, currentPage, keepParameters);

        if (count.getPageCount() <= 2 * navigationSize) {
            count.setFirstPage(0);
            count.setLastPage(count.getPageCount());
        } else if (count.getCurrentPage() <= navigationSize) {
            count.setFirstPage(0);
            count.setLastPage(2 * navigationSize);
        } else if (count.getCurrentPage() >= count.getPageCount() - navigationSize) {
            count.setFirstPage(count.getPageCount() - 2 * navigationSize);
            count.setLastPage(count.getPageCount());
        } else {
            count.setFirstPage(count.getCurrentPage() - navigationSize);
            count.setLastPage(count.getCurrentPage() + navigationSize);
        }

        return count;
    }

    PagerBean getPagerBeanCommon(int itemsCount, int itemsPerPage, int currentPage, String keepParameters) {
        PagerBean count = new PagerBean();
        count.setCurPageParam(PAGE_NUMBER_NAME);
        count.setQueryString(keepParameters);
        count.setNavButtonsCountOneSide(navigationSize);

        count.setItemsCount(itemsCount);
        count.setItemsPerPage(normalizeItemsPerPage(itemsCount, itemsPerPage));
        count.setPageCount(normalizePageCount(count.getItemsCount(), count.getItemsPerPage()));
        count.setCurrentPage(normalizeCurrentPage(currentPage, count.getPageCount()));
        return count;
    }

    static int normalizePageCount(int itemsCount, int itemsPerPage) {
        if (itemsCount == 0) {
            return 0;
        } else if (itemsCount % itemsPerPage == 0) {
            return (itemsCount / itemsPerPage) - 1;
        } else {
            return itemsCount / itemsPerPage;
        }
    }

    static int normalizeCurrentPage(int currentPage, int pageCount) {
        if (currentPage < 0) {
            return 0;
        } else if (currentPage > pageCount) {
            return pageCount;
        } else {
            return currentPage;
        }
    }

    static int normalizeItemsPerPage(int itemsCount, int itemsPerPage) {
        return itemsPerPage < 1 ? itemsCount : itemsPerPage;
    }
}
