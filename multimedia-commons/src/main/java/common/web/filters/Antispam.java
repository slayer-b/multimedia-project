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

import common.beans.AntispamBean;
import common.utils.CommonAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author demchuck.dima@gmail.com
 */
public class Antispam implements Filter {
    private final Logger logger = LoggerFactory.getLogger(Antispam.class);

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    /**
     * request parameter that holds antispam value.
     */
    private static final String ANTISPAM_PARAM_NAME = "anti_spam_code";
    /**
     * session attribute that holds bean with antispam properties.
     */
    private static final String CODE_ATTR_DEFAULT = "antiSpam";
    private static final String ATTR_VIEW_URL = "view_url";
    private String viewUrl;

    /**
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (response.isCommitted()) {
            logger.error("response is committed; url=" + req.getRequestURI() + "?" + req.getQueryString());
            return;
        }
        String errorCode = canAccess(req);
        if (errorCode == null) {
            chain.doFilter(request, response);
        } else {
            CommonAttributes.addErrorMessage(errorCode, request);
            request.getRequestDispatcher(viewUrl).forward(request, response);
        }
    }

    protected String canAccess(HttpServletRequest request) {
        return canAccess(request, request.getParameter(ANTISPAM_PARAM_NAME), false);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        if (filterConfig == null) {
            viewUrl = null;
        } else {
            viewUrl = filterConfig.getInitParameter(ATTR_VIEW_URL);
            if (viewUrl == null || viewUrl.equals("")) {
                viewUrl = null;
            }
            logger.debug("Antispam:Initializing filter");
        }
    }

    /**
     * Destroy method for this filter.
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter.
     *
     * @param value value to set.
     */
    @Override
    public void init(FilterConfig value) {
        setFilterConfig(value);
    }

    /**
     * this method actually sets result of antispamcode request
     * i.e. creates an Antispam bean and saves it to session
     *
     * @param request the current request
     * @param result  the expected result of entering antispam code
     */
    public static void setCode(HttpServletRequest request, String result) {
        AntispamBean antispam = new AntispamBean();
        antispam.setResult(result);
        request.getSession().setAttribute(CODE_ATTR_DEFAULT, antispam);
        //logger.info("antispam set");
    }

    /**
     * checks if user can access
     * i.e. if request contains same antispam code as session
     *
     * @param request current http request
     * @param code    the code that was entered
     * @return error code if any, or null if no errors
     */
    public static String canAccess(HttpServletRequest request, String code, boolean resetCode) {
        Object o = request.getSession().getAttribute(CODE_ATTR_DEFAULT);
        if (o != null) {
            AntispamBean antispam = (AntispamBean) o;
            String code2 = antispam.getResult();
            //if (code==null&&antispam.getTime_spam()>System.currentTimeMillis()){
            if (code == null && antispam.getCount() > 0) {
                //logger.info("antispam not needed");
                return null;
            }
            if (resetCode) {
                antispam.setResult(null);
            }
            if (code2 != null && !code2.equals("") && code2.equals(code)) {
                //logger.info("antispam good");
                antispam.resetAttempts();
                antispam.increaseCount();
                //antispam.setTime_spam(System.currentTimeMillis()+60000*antispam.getCount());
                return null;
            } else {
                //logger.info("antispam bad");
                antispam.increaseAttempts();
                antispam.resetCount();
            }
        }
        return "anti_spam_code.different";
    }
}
