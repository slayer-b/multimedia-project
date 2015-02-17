package com.multimedia.web.support;

import common.beans.PagerBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;

public class PaginatedListUtilsTest {
    private static final int NAVIGATION_SIZE = 15;
    
    private PaginatedListUtils paginatedListUtils;

    @Before
    public void setUp() {
        paginatedListUtils = new PaginatedListUtils(10, NAVIGATION_SIZE);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetPageNumber_ok() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter(PaginatedListUtils.PAGE_NUMBER_NAME, "25");
        assertEquals(25, PaginatedListUtils.getPageNumber(request));
    }

    @Test
    public void testGetPageNumber_err() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(PaginatedListUtils.DEFAULT_PAGE_NUMBER_VALUE,
                PaginatedListUtils.getPageNumber(request));
    }

    @Test
    public void testGetFirstItemNumber() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter(PaginatedListUtils.PAGE_NUMBER_NAME, "25");
        assertEquals(25 * PaginatedListUtils.DEFAULT_PAGE_SIZE_VALUE,
                PaginatedListUtils.getFirstItemNumber(request));
    }

    @Test
    public void testGetStartPageNumber() {
        assertEquals(PaginatedListUtils.DEFAULT_PAGE_NUMBER_VALUE, paginatedListUtils.getStartPageNumber());
    }
    
    @Test
    public void testGetItemsPerPage() {
        assertEquals(10, paginatedListUtils.getItemsPerPage());
    }
    
    @Test
    public void testGetPageNumber_int() {
        assertEquals(785, paginatedListUtils.getPageNumber(785));
    }
    
    @Test
    public void testGetPageNumber_null() {
        assertEquals(PaginatedListUtils.DEFAULT_PAGE_NUMBER_VALUE, paginatedListUtils.getPageNumber((Integer)null));
    }

    @Test
    public void testGetItemsPerPage_int() {
        assertEquals(785, paginatedListUtils.getItemsPerPage(785));
        
    }

    @Test
    public void testGetItemsPerPage_null() {
        assertEquals(paginatedListUtils.getItemsPerPage(), paginatedListUtils.getItemsPerPage(null));
    }

    @Test
    public void testGetPagerBeanCommon() {
        PagerBean count = paginatedListUtils.getPagerBeanCommon(150, 125, 1, "asd");
        assertEquals(150, count.getItemsCount());
        assertEquals(125, count.getItemsPerPage());
        assertEquals("asd", count.getQueryString());
        assertEquals(PaginatedListUtils.PAGE_NUMBER_NAME, count.getCurPageParam());
        assertEquals(NAVIGATION_SIZE, count.getNavButtonsCountOneSide());
        assertEquals(1, count.getCurrentPage());
        assertEquals(1, count.getPageCount());
    }

    @Test
    public void testNormalizeItemsPerPage_lowerThenOne() {
        assertEquals(35, PaginatedListUtils.normalizeItemsPerPage(35, 0));
    }

    @Test
    public void testNormalizeItemsPerPage_notLowerThenOne() {
        assertEquals(5, PaginatedListUtils.normalizeItemsPerPage(35, 5));
    }
    
    @Test
    public void testNormalizeCurrentPage_curGtTotal() {
        assertEquals(6, PaginatedListUtils.normalizeCurrentPage(7, 6));
    }
    
    @Test
    public void testNormalizeCurrentPage_curLeTotalAndLtZero() {
        assertEquals(0, PaginatedListUtils.normalizeCurrentPage(-1, 6));
    }
    
    @Test
    public void testNormalizeCurrentPage_curLeTotalAndGeZero() {
        assertEquals(2, PaginatedListUtils.normalizeCurrentPage(7, 2));
    }

    @Test
    public void testNormalizePageCount_divisible() {
        assertEquals(3, PaginatedListUtils.normalizePageCount(100, 25));
    }

    @Test
    public void testNormalizePageCount_notDivisible() {
        assertEquals(4, PaginatedListUtils.normalizePageCount(101, 25));
    }

    @Test
    public void testNormalizePageCount_zero_zero() {
        assertEquals(0, PaginatedListUtils.normalizePageCount(0, 25));
    }

    @Test
    public void testNormalizeFirstPage_curPageLeNavSize() {
        assertEquals(0, paginatedListUtils.normalizeFirstPage(7));
    }

    @Test
    public void testNormalizeFirstPage_curPageGtNavSize() {
        assertEquals(20 - NAVIGATION_SIZE, paginatedListUtils.normalizeFirstPage(20));
    }

    @Test
    public void testNormalizeLastPage_pageCountLeNavSize() {
        assertEquals(10, paginatedListUtils.normalizeLastPage(7, 10));
    }

    @Test
    public void testNormalizeLastPage_curPagePlusNavSizeGtPageCount() {
        assertEquals(20, paginatedListUtils.normalizeLastPage(7, 20));
    }

    @Test
    public void testNormalizeLastPage_other() {
        assertEquals(18, paginatedListUtils.normalizeLastPage(3, 20));
    }

    @Test
    public void testGetPagerBean() {
        PagerBean count = paginatedListUtils.getPagerBean(
                Integer.valueOf(1), 10, Integer.valueOf(3), "asd");

        assertEquals(0, count.getFirstPage());
        assertEquals(3, count.getLastPage());
    }

    @Test
    public void testGetPagerBean2_zero_zero() {
        PagerBean count = paginatedListUtils.getPagerBean2(
                0, 0, "asd");

        assertEquals(0, count.getFirstPage());
        assertEquals(0, count.getLastPage());
    }

    @Test
    public void testGetPagerBean2_pageCountLessTotalNavButtons() {
        PagerBean count = paginatedListUtils.getPagerBean2(
                0, 25, "asd");

        assertEquals(0, count.getFirstPage());
        assertEquals(2, count.getLastPage());
    }

    @Test
    public void testGetPagerBean2_addItemsRight() {
        PagerBean count = paginatedListUtils.getPagerBean2(
                3, 500, "asd");

        assertEquals(0, count.getFirstPage());
        assertEquals(2 * NAVIGATION_SIZE, count.getLastPage());
    }

    @Test
    public void testGetPagerBean2_addItemsLeft() {
        PagerBean count = paginatedListUtils.getPagerBean2(
                40, 500, "asd");

        assertEquals(19, count.getFirstPage());
        assertEquals(49, count.getLastPage());
    }

    @Test
    public void testGetPagerBean2_general() {
        PagerBean count = paginatedListUtils.getPagerBean2(
                20, 500, "asd");

        assertEquals(5, count.getFirstPage());
        assertEquals(35, count.getLastPage());
    }
}
