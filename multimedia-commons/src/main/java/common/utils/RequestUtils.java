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
package common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author demchuck.dima@gmail.com
 */
public final class RequestUtils {

    /**
     * get parameter with given name from the request and tries to content it to long.
     *
     * @param req  given request
     * @param name name of request parameter
     * @return long or null, if parameter not exists or cannot be converted
     */
    public static Long getLongParam(HttpServletRequest req, String name) {
        try {
            return Long.valueOf(req.getParameter(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * get parameter with given name from the request and tries to content it to boolean.
     *
     * @param req  given request
     * @param name name of request parameter
     * @return boolean or null, if parameter not exists or cannot be converted
     */
    public static Boolean getBoolParam(HttpServletRequest req, String name) {
        return Boolean.valueOf(req.getParameter(name));
    }

    /**
     * get array of parameters as Long[].
     *
     * @param req  request where to get value of param
     * @param name name of request parameter
     * @return long or null, if parameter not exists or cannot be converted
     */
    public static Long[] getLongParameters(HttpServletRequest req, String name) {
        try {
            String[] temp = req.getParameterValues(name);
            Long[] rez = new Long[temp.length];
            for (int i = 0; i < temp.length; i++) {
                rez[i] = Long.valueOf(temp[i]);
            }
            return rez;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * get long parameter with given name.
     *
     * @param req        given request
     * @param name       name of request parameter
     * @param defaultVal the value to be returned if any exception occurs
     * @return long or default, if parameter not exists or cannot be converted
     */
    public static long getLongParam(HttpServletRequest req, String name, long defaultVal) {
        try {
            return Long.valueOf(req.getParameter(name));
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * get an integer parameter with given name from the request.
     *
     * @param req  given request
     * @param name name of request parameter
     * @return long or null, if parameter not exists or cannot be converted
     */
    public static Integer getIntegerParam(HttpServletRequest req, String name) {
        try {
            return Integer.valueOf(req.getParameter(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * copy all request attributes to the map.
     *
     * @param req given request
     * @param m   where to take attributes
     */
    public static void copyRequestAttributesFromMap(HttpServletRequest req, Map<String, Object> m) {
        if (m != null && !m.isEmpty()) {
            for (Entry<String, Object> e : m.entrySet()) {
                req.setAttribute(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * path is actually http://serverName:serverPort contextPath.
     *
     * @param request http request
     * @return path to server via http
     */
    public static String getFullServerPathHttp(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getLocalPort() + request.getContextPath();
    }

    /**
     * its an utility class.
     */
    private RequestUtils() {
    }
}
