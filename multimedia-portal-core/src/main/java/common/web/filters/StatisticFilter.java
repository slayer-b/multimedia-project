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

package common.web.filters;

import common.beans.StatisticsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author demchuck.dima@gmail.com
 */
public class StatisticFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(StatisticFilter.class);

    public static final String STAT_NAME = "STAT_NAME_FILTER";

    private static final boolean logTime = true;

    private StatisticsBean statsBean;

    private void doBeforeProcessing(ServletRequest request) {
        if (logTime) {
            request.setAttribute("timeStart", getTime());
        }
    }

    private void doAfterProcessing(ServletRequest request) {
        if (logTime) {
            Long timeStart = (Long) request.getAttribute("timeStart");
            if (timeStart != null) {
                long time = getTime() - timeStart;
                statsBean.increaseStat(time);
                logger.debug("processed time={}", time);
            }
        }

    }

    /**
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        doBeforeProcessing(request);

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Exception t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
        }

        doAfterProcessing(request);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            if (problem instanceof RuntimeException) {
                throw (RuntimeException) problem;
            }
            logger.error("even xz what other error can occur here");
        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
        statsBean = null;
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("statistic filter initialized");
        statsBean = new StatisticsBean(getTime());
        if (filterConfig != null) {
            filterConfig.getServletContext().setAttribute(STAT_NAME, statsBean);
        }
    }

    public static StatisticsBean getStatistics(ServletContext sc) {
        return (StatisticsBean) sc.getAttribute(STAT_NAME);
    }

    public static long getTime() {
        return System.nanoTime();
    }
}
